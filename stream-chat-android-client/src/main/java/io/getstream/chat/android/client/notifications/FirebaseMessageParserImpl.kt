package io.getstream.chat.android.client.notifications

import com.google.firebase.messaging.RemoteMessage
import io.getstream.chat.android.client.extensions.isValid
import io.getstream.chat.android.client.notifications.handler.NotificationConfig

internal class FirebaseMessageParserImpl(val config: NotificationConfig) : FirebaseMessageParser {

    private val messageIdKey = config.firebaseMessageIdKey
    private val channelTypeKey = config.firebaseChannelIdKey
    private val channelIdKey = config.firebaseChannelTypeKey

    override fun isValid(message: RemoteMessage): Boolean = message.isValid(config)

    override fun parse(message: RemoteMessage): FirebaseMessageParser.Data {
        val messageId = message.data[messageIdKey]!!
        val channelId = message.data[channelIdKey]!!
        val channelType = message.data[channelTypeKey]!!

        return FirebaseMessageParser.Data(messageId, channelType, channelId)
    }
}
