package io.getstream.chat.android.offline.querychannels

import androidx.recyclerview.widget.DiffUtil
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.getstream.chat.android.client.api.models.FilterObject
import io.getstream.chat.android.client.api.models.QuerySort
import io.getstream.chat.android.client.events.ChannelUpdatedByUserEvent
import io.getstream.chat.android.client.events.HasChannel
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.offline.ChatDomainImpl
import io.getstream.chat.android.offline.integration.BaseConnectedMockedTest
import io.getstream.chat.android.offline.utils.ChannelDiffCallback
import io.getstream.chat.android.offline.utils.DiffUtilOperationCounter
import io.getstream.chat.android.offline.utils.UpdateOperationCounts
import io.getstream.chat.android.test.asCall
import io.getstream.chat.android.test.randomCID
import io.getstream.chat.android.test.randomDate
import io.getstream.chat.android.test.randomString
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class QueryChannelsControllerIntegrationTest : BaseConnectedMockedTest() {

    @Test
    fun `Given initialized SDK When request channels Should not crash`(): Unit = runBlocking {
        val queryChannelsController =
            Fixture(chatDomainImpl, data.filter1).givenChannelInOfflineStorage(data.channel1).get()

        val queryChannelsResult = queryChannelsController.query()

        queryChannelsResult.isSuccess shouldBe true
    }

    @Test
    fun `Given the same channels in cache and BE When observing channels Should receive the correct number of events with channels`(): Unit =
        runBlocking {
            val counter = DiffUtilOperationCounter { old: List<Channel>, new: List<Channel> ->
                DiffUtil.calculateDiff(ChannelDiffCallback(old, new), true)
            }
            val sut = Fixture(chatDomainImpl, data.filter1)
                .givenChannelInOfflineStorage(data.channel1)
                .givenMessageInOfflineStorage(data.message1)
                .withCounter(counter)
                .get()

            // querying channels, should trigger 2 events (once for offline and another for online),
            // but there should be only 1 insert since the channels in our case are the same
            sut.query()
            counter.counts shouldBeEqualTo UpdateOperationCounts(events = 2, changed = 0, inserted = 1)

            // adding a new channel, should trigger 1 "insert" operation, 1 "update" caused by refresh flow values
            chatDomainImpl.eventHandler.handleEvent(data.notificationAddedToChannel2Event)
            counter.counts shouldBeEqualTo UpdateOperationCounts(events = 4, changed = 1, inserted = 2)

            // adding a new message, should trigger 1 "changed" operation
            chatDomainImpl.eventHandler.handleEvent(data.newMessageFromUser2)
            counter.counts shouldBeEqualTo UpdateOperationCounts(events = 5, changed = 2, inserted = 2)

            // updating the last message, should should trigger 1 "changed" operation
            chatDomainImpl.eventHandler.handleEvent(data.messageUpdatedEvent)
            counter.counts shouldBeEqualTo UpdateOperationCounts(events = 6, changed = 3, inserted = 2)
        }

    @Test
    fun `Given channel events handler When handle channel events Should invoke channel events handler`(): Unit =
        runBlocking {
            val channelEventsHandler = object : ChannelEventsHandler {
                var didHandle = false
                override fun onChannelEvent(event: HasChannel, filter: FilterObject): EventHandlingResult {
                    didHandle = true
                    return EventHandlingResult.SKIP
                }
            }
            val sut = Fixture(chatDomainImpl, data.filter1).givenChannelInOfflineStorage(data.channel1).get()
            sut.channelEventsHandler = channelEventsHandler

            sut.handleEvent(
                ChannelUpdatedByUserEvent(
                    type = randomString(),
                    createdAt = randomDate(),
                    cid = randomCID(),
                    channelType = randomString(),
                    channelId = randomString(),
                    user = mock(),
                    message = mock(),
                    channel = mock()
                )
            )

            channelEventsHandler.didHandle shouldBe true
        }

    private class Fixture(
        private val chatDomainImpl: ChatDomainImpl,
        private val filter: FilterObject,
    ) {
        private val queryChannelsControllerImpl = chatDomainImpl.queryChannels(filter, QuerySort()).apply {
            newChannelEventFilter = { _, _ -> true }
        }

        fun givenChannelInOfflineStorage(channel: Channel): Fixture {
            runBlocking {
                val query = QueryChannelsSpec(filter).apply { cids = setOf(channel.cid) }
                chatDomainImpl.repos.insertChannel(channel)
                chatDomainImpl.repos.insertQueryChannels(query)
                whenever(chatDomainImpl.client.queryChannelsInternal(any())) doReturn listOf(channel).asCall()
            }
            return this
        }

        fun givenMessageInOfflineStorage(message: Message): Fixture {
            runBlocking {
                chatDomainImpl.repos.insertMessage(message)
            }
            return this
        }

        suspend fun withCounter(counter: DiffUtilOperationCounter<Channel>): Fixture {
            chatDomainImpl.scope.launch {
                queryChannelsControllerImpl.channels.collect { channels ->
                    val ids = channels.map { it.cid }
                    println("Channel ids is now equal to $ids")
                    counter.onEvent(channels)
                }
            }
            return this
        }

        fun get(): QueryChannelsController = queryChannelsControllerImpl
    }
}
