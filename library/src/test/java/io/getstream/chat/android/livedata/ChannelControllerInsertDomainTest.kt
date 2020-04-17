package io.getstream.chat.android.livedata

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.client.utils.SyncStatus
import io.getstream.chat.android.livedata.entity.QueryChannelsEntity
import io.getstream.chat.android.livedata.entity.ReactionEntity
import io.getstream.chat.android.livedata.request.AnyChannelPaginationRequest
import io.getstream.chat.android.livedata.utils.getOrAwaitValue
import kotlinx.coroutines.*
import org.junit.*
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class ChannelControllerInsertDomainTest: BaseDomainTest() {

    @Before
    fun setup() {
        client = createClient()
        setupChatDomain(client, true)
        val handler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }
    }

    @After
    fun tearDown() {
        //ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
        client.disconnect()
        db.close()


    }

    @Test
    fun sendMessageUseCase() = runBlocking(Dispatchers.IO) {
        val call = chatDomain.useCases.sendMessage(data.message1)
        val result = call.execute()
    }



    @Test
    fun reactionStorage() = runBlocking(Dispatchers.IO) {
        val reactionEntity = ReactionEntity(data.message1.id, data.user1.id, data.reaction1.type)
        reactionEntity.syncStatus = SyncStatus.SYNC_NEEDED
        chatDomain.repos.reactions.insert(reactionEntity)
        val results = chatDomain.repos.reactions.retryReactions()
        Truth.assertThat(results.size).isEqualTo(1)
    }

    // TODO: converter/repo test suite

    @Test
    fun sendReaction() = runBlocking(Dispatchers.IO) {

        // TODO: Mock socket and mock client
        // ensure the message exists
        chatDomain.createChannel(data.channel1)
        channelController.sendMessage(data.message1)
        chatDomain.setOffline()
        chatDomain.repos.channels.insert(data.channel1)
        channelController.upsertMessage(data.message1)
        // send the reaction while offline
        channelController.sendReaction(data.reaction1)
        var reactionEntity = chatDomain.repos.reactions.select(data.message1.id, data.user1.id, data.reaction1.type)
        Truth.assertThat(reactionEntity!!.syncStatus).isEqualTo(SyncStatus.SYNC_NEEDED)
        chatDomain.setOnline()
        val reactionEntities = chatDomain.repos.reactions.retryReactions()
        Truth.assertThat(reactionEntities.size).isEqualTo(1)
        reactionEntity = chatDomain.repos.reactions.select(data.message1.id, data.user1.id, "like")
        Truth.assertThat(reactionEntity!!.syncStatus).isEqualTo(SyncStatus.SYNCED)

    }

    @Test
    fun deleteReaction() = runBlocking(Dispatchers.IO) {
        chatDomain.setOffline()

        channelController.sendReaction(data.reaction1)
        channelController.deleteReaction(data.reaction1)

        val reaction = chatDomain.repos.reactions.select(data.message1.id, data.user1.id, data.reaction1.type)
        Truth.assertThat(reaction!!.syncStatus).isEqualTo(SyncStatus.SYNC_NEEDED)
        Truth.assertThat(reaction!!.deletedAt).isNotNull()

        val reactions = chatDomain.repos.reactions.retryReactions()
        Truth.assertThat(reactions.size).isEqualTo(1)
    }

    @Test
    @Ignore("Needs a mock, message id already exists")
    fun sendMessage() = runBlocking(Dispatchers.IO){
        // send the message while offline
        chatDomain.createChannel(data.channel1)
        chatDomain.setOffline()
        channelController.sendMessage(data.message1)
        // get the message and channel state both live and offline versions
        var roomChannel = chatDomain.repos.channels.select(data.message1.channel.cid)
        var liveChannel = channelController.channel.getOrAwaitValue()
        var roomMessages = chatDomain.repos.messages.selectMessagesForChannel(data.message1.channel.cid, AnyChannelPaginationRequest().apply { messageLimit=10 })
        var liveMessages = channelController.messages.getOrAwaitValue()

        Truth.assertThat(liveMessages.size).isEqualTo(1)
        Truth.assertThat(liveMessages[0].syncStatus).isEqualTo(SyncStatus.SYNC_NEEDED)
        Truth.assertThat(roomMessages.size).isEqualTo(1)
        Truth.assertThat(roomMessages[0].syncStatus).isEqualTo(SyncStatus.SYNC_NEEDED)
        // verify the message is stored in room, and set to retry
        // verify the channel is updated as well (lastMessage at and lastMessageAt)
        Truth.assertThat(liveChannel.lastMessageAt).isEqualTo(data.message1.createdAt)
        Truth.assertThat(roomChannel!!.lastMessageAt).isEqualTo(data.message1.createdAt)

        var messageEntities = chatDomain.retryMessages()
        Truth.assertThat(messageEntities.size).isEqualTo(1)

        // now we go online and retry, after the retry all state should be updated
        chatDomain.setOnline()
        messageEntities = chatDomain.retryMessages()
        Truth.assertThat(messageEntities.size).isEqualTo(1)

        roomMessages = chatDomain.repos.messages.selectMessagesForChannel(data.message1.channel.cid, AnyChannelPaginationRequest().apply { messageLimit=10 })
        liveMessages = channelController.messages.getOrAwaitValue()
        Truth.assertThat(liveMessages[0].syncStatus).isEqualTo(SyncStatus.SYNCED)
        Truth.assertThat(roomMessages[0].syncStatus).isEqualTo(SyncStatus.SYNCED)

    }

    @Test
    fun clean() {
        // clean should remove old typing indicators
        channelController.setTyping(data.user1.id, data.user1TypingStartedOld)
        channelController.setTyping(data.user2.id, data.user2TypingStarted)

        Truth.assertThat(channelController.typing.getOrAwaitValue().size).isEqualTo(2)
        channelController.clean()
        Truth.assertThat(channelController.typing.getOrAwaitValue().size).isEqualTo(1)
    }

    @Test
    fun insertQuery() = runBlocking(Dispatchers.IO){
        val filter = Filters.eq("type", "messaging")
        val sort = null
        val query = QueryChannelsEntity(filter, sort)
        query.channelCIDs = sortedSetOf("messaging:123", "messaging:234")
        chatDomain.repos.queryChannels.insert(query)
    }

    @Test
    fun insertReaction() = runBlocking(Dispatchers.IO) {
        // check DAO layer and converters
        val reactionEntity = ReactionEntity(data.reaction1)
        chatDomain.repos.reactions.insert(reactionEntity)
        val reactionEntity2 = chatDomain.repos.reactions.select(reactionEntity.messageId, reactionEntity.userId, reactionEntity.type)
        Truth.assertThat(reactionEntity2).isEqualTo(reactionEntity)
        Truth.assertThat(reactionEntity2!!.extraData).isNotNull()
        // verify conversion logic is ok
        val userMap = mutableMapOf(data.user1.id to data.user1)
        val reactionConverted = reactionEntity2!!.toReaction(userMap)
        Truth.assertThat(reactionConverted).isEqualTo(data.reaction1)
    }


    @Test
    fun typing() = runBlocking(Dispatchers.IO){
        // second typing keystroke after each other should not resend the typing event
        Truth.assertThat(chatDomain.useCases.keystroke(data.channel1.cid).execute().data()).isTrue()
        Truth.assertThat(chatDomain.useCases.keystroke(data.channel1.cid).execute().data()).isFalse()

        sleep(3001)
        Truth.assertThat(chatDomain.useCases.keystroke(data.channel1.cid).execute().data()).isTrue()
    }

    @Test
    fun markRead() = runBlocking(Dispatchers.IO){
        // ensure there is at least one message
        channelController.upsertMessage(data.message1)
        Truth.assertThat(channelController.markRead()).isTrue()
        Truth.assertThat(channelController.markRead()).isFalse()

    }


}