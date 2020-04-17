package io.getstream.chat.android.livedata.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import io.getstream.chat.android.client.api.models.Pagination
import io.getstream.chat.android.client.utils.SyncStatus
import io.getstream.chat.android.livedata.BaseDomainTest
import io.getstream.chat.android.livedata.request.AnyChannelPaginationRequest
import io.getstream.chat.android.livedata.utils.calendar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MessageRepositoryTest: BaseDomainTest() {
    val repo by lazy { chatDomain.repos.messages }

    @Before
    fun setup() {
        client = createDisconnectedMockClient()
        setupChatDomain(client, false)
    }

    @After
    fun tearDown() {
        chatDomain.disconnect()
        db.close()
    }

    @Test
    fun testInsertAndRead() = runBlocking(Dispatchers.IO) {
        repo.insertMessage(data.message1)
        val entity = repo.select(data.message1.id)
        val message = entity!!.toMessage(data.userMap)
        Truth.assertThat(message).isEqualTo(data.message1)
    }

    @Test
    fun testUpdate() = runBlocking(Dispatchers.IO) {
        repo.insertMessage(data.message1, true)
        repo.insertMessage(data.message1Updated, true)

        val entity = repo.select(data.message1Updated.id)
        val message = entity!!.toMessage(data.userMap)
        Truth.assertThat(message).isEqualTo(data.message1Updated)
        Truth.assertThat(repo.messageCache.size()).isEqualTo(1)
    }

    @Test
    fun testSyncNeeded() = runBlocking(Dispatchers.IO) {
        val message1 = data.message1.copy().apply { text="yoyo"; syncStatus=SyncStatus.SYNC_NEEDED; user=data.user1  }
        val message2 = message1.copy().apply { id="helloworld"; text="hi123"; syncStatus=SyncStatus.SYNC_FAILED; user=data.user1 }
        repo.insertMessages(listOf(message1, message2), true)

        var messages = repo.selectSyncNeeded()
        Truth.assertThat(messages.size).isEqualTo(1)
        Truth.assertThat(messages.first().syncStatus).isEqualTo(SyncStatus.SYNC_NEEDED)

        messages = repo.retryMessages()
        Truth.assertThat(messages.size).isEqualTo(1)
        Truth.assertThat(messages.first().syncStatus).isEqualTo(SyncStatus.SYNCED)

        messages = repo.selectSyncNeeded()
        Truth.assertThat(messages.size).isEqualTo(0)
    }

    @Test
    fun testSelectMessagesForChannel() = runBlocking(Dispatchers.IO) {
        val message1 = data.message1.copy().apply { text="yoyo"; syncStatus=SyncStatus.SYNC_NEEDED; user=data.user1; createdAt=calendar(2019, 11, 1)  }
        val message2 = message1.copy().apply { id="helloworld"; text="hi123"; syncStatus=SyncStatus.SYNC_FAILED; user=data.user1; createdAt=calendar(2019, 10, 1) }
        val message3 = message1.copy().apply { id="helloworl123d"; text="hi123123"; syncStatus=SyncStatus.SYNC_FAILED; user=data.user1; createdAt=calendar(2019, 9, 1) }
        repo.insertMessages(listOf(message1, message2, message3), true)

        // this should select the first message
        var pagination = AnyChannelPaginationRequest(1)
        pagination.setFilter(Pagination.GREATER_THAN, message2.id)
        var messages = repo.selectMessagesForChannel(data.message1.cid, pagination)
        Truth.assertThat(messages.size).isEqualTo(1)
        Truth.assertThat(messages.first().id).isEqualTo(message1.id)
        // this should select the third message
        pagination.setFilter(Pagination.LESS_THAN, message2.id)
        messages = repo.selectMessagesForChannel(data.message1.cid, pagination)
        Truth.assertThat(messages.size).isEqualTo(1)
        Truth.assertThat(messages.first().id).isEqualTo(message3.id)
    }

}