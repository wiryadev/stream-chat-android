@file:Suppress("DEPRECATION_ERROR")

package io.getstream.chat.android.client.notifications.handler

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.edit
import io.getstream.chat.android.client.R
import io.getstream.chat.android.client.extensions.getUsersExcludingCurrent
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.receivers.NotificationMessageReceiver

/**
 * Class responsible for handling chat notifications.
 */
@Deprecated(
    message = "This class will be used internally on future versions and won't be accesible, you need to implement your own [NotificationHandler]"
)
public open class ChatNotificationHandler @JvmOverloads constructor(
    protected val context: Context,
    private val newMessageIntent: (messageId: String, channelType: String, channelId: String) -> Intent =
        { _, _, _ -> context.packageManager!!.getLaunchIntentForPackage(context.packageName)!! },
) : NotificationHandler {

    private val sharedPreferences: SharedPreferences by lazy { context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE) }
    private val notificationManager: NotificationManager by lazy {
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.createNotificationChannel(createNotificationChannel())
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public open fun createNotificationChannel(): NotificationChannel {
        return NotificationChannel(
            getNotificationChannelId(),
            getNotificationChannelName(),
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            setShowBadge(true)
            importance = NotificationManager.IMPORTANCE_HIGH
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            vibrationPattern = longArrayOf(
                100,
                200,
                300,
                400,
                500,
                400,
                300,
                200,
                400,
            )
        }
    }

    public open fun getNotificationChannelId(): String = context.getString(R.string.stream_chat_notification_channel_id)
    public open fun getNotificationChannelName(): String = context.getString(R.string.stream_chat_notification_channel_name)

    @Deprecated(
        message = "It is not used anymore",
        level = DeprecationLevel.ERROR
    )
    public open fun getErrorCaseNotificationTitle(): String = ""

    @Deprecated(
        message = "It is not used anymore",
        level = DeprecationLevel.ERROR
    )
    public open fun getErrorCaseNotificationContent(): String = ""

    @Deprecated(
        message = "Notification error is not used anymore",
        level = DeprecationLevel.ERROR
    )
    public open fun buildErrorCaseNotification(): Notification {
        return getNotificationBuilder(
            contentTitle = getErrorCaseNotificationTitle(),
            contentText = getErrorCaseNotificationContent(),
            groupKey = getErrorNotificationGroupKey(),
            intent = getErrorCaseIntent(),
        ).build()
    }

    public override fun showNotification(channel: Channel, message: Message) {
        val notificationId: Int = System.nanoTime().toInt()
        val notificationSummaryId = getNotificationGroupSummaryId(channel.type, channel.id)
        addNotificationId(notificationId, notificationSummaryId)
        showNotification(notificationId, buildNotification(notificationId, channel, message).build())
        showNotification(notificationSummaryId, buildNotificationGroupSummary(channel, message).build())
    }

    public open fun buildNotification(
        notificationId: Int,
        channel: Channel,
        message: Message,
    ): NotificationCompat.Builder {
        return getNotificationBuilder(
            contentTitle = channel.getNotificationContentTitle(),
            contentText = message.text,
            groupKey = getNotificationGroupKey(channelType = channel.type, channelId = channel.id),
            intent = getNewMessageIntent(messageId = message.id, channelType = channel.type, channelId = channel.id),
        ).apply {
            addAction(NotificationMessageReceiver.createReadAction(context, notificationId, channel, message))
            addAction(NotificationMessageReceiver.createReplyAction(context, notificationId, channel))
            setDeleteIntent(NotificationMessageReceiver.createDismissPendingIntent(context, notificationId, channel))
        }
    }

    public open fun buildNotificationGroupSummary(channel: Channel, message: Message): NotificationCompat.Builder {
        return getNotificationBuilder(
            contentTitle = channel.getNotificationContentTitle(),
            contentText = context.getString(R.string.stream_chat_notification_group_summary_content_text),
            groupKey = getNotificationGroupKey(channelType = channel.type, channelId = channel.id),
            intent = getNewMessageIntent(messageId = message.id, channelType = channel.type, channelId = channel.id),
        ).apply {
            setGroupSummary(true)
        }
    }

    @Deprecated(
        message = "Notification error is not used anymore",
        level = DeprecationLevel.ERROR
    )
    public open fun buildErrorNotificationGroupSummary(): Notification {
        return getNotificationBuilder(
            contentTitle = "",
            contentText = "",
            groupKey = getErrorNotificationGroupKey(),
            getErrorCaseIntent(),
        ).apply {
            setGroupSummary(true)
        }.build()
    }

    public open fun getNotificationGroupKey(channelType: String, channelId: String): String {
        return "$channelType:$channelId"
    }

    public open fun getNotificationGroupSummaryId(channelType: String, channelId: String): Int {
        return getNotificationGroupKey(channelType = channelType, channelId = channelId).hashCode()
    }

    public open fun getErrorNotificationGroupKey(): String = ERROR_NOTIFICATION_GROUP_KEY

    public open fun getErrorNotificationGroupSummaryId(): Int = getErrorNotificationGroupKey().hashCode()

    private fun getRequestCode(): Int {
        return System.currentTimeMillis().toInt()
    }

    public open fun getNewMessageIntent(
        messageId: String,
        channelType: String,
        channelId: String,
    ): Intent = newMessageIntent(messageId, channelType, channelId)

    /**
     * Dismiss notifications from a given [channelType] and [channelId].
     *
     * @param channelType String that represent the channel type of the channel you want to dismiss notifications.
     * @param channelId String that represent the channel id of the channel you want to dismiss notifications.
     */
    override fun dismissChannelNotifications(channelType: String, channelId: String) {
        dismissSummaryNotification(getNotificationGroupSummaryId(channelType, channelId))
    }

    /**
     * Dismiss all notifications.
     */
    override fun dismissAllNotifications() {
        getNotificationSummaryIds().forEach(::dismissSummaryNotification)
    }

    public open fun getErrorCaseIntent(): Intent {
        return context.packageManager!!.getLaunchIntentForPackage(context.packageName)!!
    }

    private fun showNotification(notificationId: Int, notification: Notification) {
        notificationManager.notify(notificationId, notification)
    }

    private fun getNotificationBuilder(
        contentTitle: String,
        contentText: String,
        groupKey: String,
        intent: Intent,
    ): NotificationCompat.Builder {
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val contentIntent = PendingIntent.getActivity(
            context,
            getRequestCode(),
            intent,
            flags,
        )

        return NotificationCompat.Builder(context, getNotificationChannelId())
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.stream_ic_notification)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setShowWhen(true)
            .setContentIntent(contentIntent)
            .setGroup(groupKey)
    }

    private fun Channel.getNotificationContentTitle(): String =
        name.takeIf { it.isNotEmpty() }
            ?: getMemberNamesWithoutCurrentUser()
            ?: context.getString(R.string.stream_chat_notification_title)

    private fun Channel.getMemberNamesWithoutCurrentUser(): String? = getUsersExcludingCurrent()
        .joinToString { it.name }
        .takeIf { it.isNotEmpty() }

    private fun dismissSummaryNotification(notificationSummaryId: Int) {
        getAssociatedNotificationIds(notificationSummaryId).forEach {
            notificationManager.cancel(it)
            removeNotificationId(it)
        }
        notificationManager.cancel(notificationSummaryId)
        sharedPreferences.edit { remove(getNotificationSummaryIdKey(notificationSummaryId)) }
    }

    private fun addNotificationId(notificationId: Int, notificationSummaryId: Int) {
        sharedPreferences.edit {
            putInt(getNotificationIdKey(notificationId), notificationSummaryId)
            putStringSet(
                KEY_NOTIFICATION_SUMMARY_IDS,
                (getNotificationSummaryIds() + notificationSummaryId).map(Int::toString).toSet()
            )
            putStringSet(
                getNotificationSummaryIdKey(notificationSummaryId),
                (getAssociatedNotificationIds(notificationSummaryId) + notificationId).map(Int::toString).toSet()
            )
        }
    }

    private fun removeNotificationId(notificationId: Int) {
        sharedPreferences.edit {
            val notificationSummaryId = getAssociatedNotificationSummaryId(notificationId)
            remove(getNotificationIdKey(notificationId))
            putStringSet(
                getNotificationSummaryIdKey(notificationSummaryId),
                (getAssociatedNotificationIds(notificationSummaryId) - notificationId).map(Int::toString).toSet()
            )
        }
    }

    private fun getNotificationSummaryIds(): Set<Int> = sharedPreferences.getStringSet(KEY_NOTIFICATION_SUMMARY_IDS, null).orEmpty().map(String::toInt).toSet()
    private fun getAssociatedNotificationSummaryId(notificationId: Int): Int = sharedPreferences.getInt(getNotificationIdKey(notificationId), 0)
    private fun getAssociatedNotificationIds(notificationSummaryId: Int): Set<Int> =
        sharedPreferences.getStringSet(getNotificationSummaryIdKey(notificationSummaryId), null).orEmpty().map(String::toInt).toSet()

    private fun getNotificationIdKey(notificationId: Int) = KEY_PREFIX_NOTIFICATION_ID + notificationId
    private fun getNotificationSummaryIdKey(notificationSummaryId: Int) = KEY_PREFIX_NOTIFICATION_SUMMARY_ID + notificationSummaryId

    private companion object {
        private const val ERROR_NOTIFICATION_GROUP_KEY = "error_notification_group_key"
        private const val SHARED_PREFERENCES_NAME = "stream_notifications.sp"
        private const val KEY_PREFIX_NOTIFICATION_ID = "nId-"
        private const val KEY_PREFIX_NOTIFICATION_SUMMARY_ID = "nSId-"
        private const val KEY_NOTIFICATION_SUMMARY_IDS = "notification_summary_ids"
    }
}
