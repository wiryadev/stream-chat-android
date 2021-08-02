package io.getstream.chat.android.client.models

import com.google.gson.annotations.SerializedName
import java.util.Date

public data class Config(

    /**
     * Date of channel creation.
     */
    @Deprecated("Use createdAt instead", level = DeprecationLevel.WARNING, replaceWith = ReplaceWith("createdAt"))
    @SerializedName("created_at")
    val created_at: Date? = null,

    /**
     * Date of last channel update.
     */
    @Deprecated("Use updatedAt instead", level = DeprecationLevel.WARNING, replaceWith = ReplaceWith("updatedAt"))
    @SerializedName("updated_at")
    val updated_at: Date? = null,

    /**
     * The name of the channel type must be unique per application
     */
    val name: String = "",

    /**
     * Controls if typing indicators are shown. Enabled by default.
     */
    @Deprecated("Use typingEventsEnabled instead", level = DeprecationLevel.WARNING, replaceWith = ReplaceWith("typingEventsEnabled"))
    @SerializedName("typing_events")
    val isTypingEvents: Boolean = true,

    /**
     * Controls whether the chat shows how far you’ve read. Enabled by default.
     */
    @Deprecated("Use readEventsEnabled instead", level = DeprecationLevel.WARNING, replaceWith = ReplaceWith("readEventsEnabled"))
    @SerializedName("read_events")
    val isReadEvents: Boolean = true,

    /**
     * Determines if events are fired for connecting and disconnecting to a chat. Enabled by default.
     */
    @Deprecated("Use connectEventsEnabled instead", level = DeprecationLevel.WARNING, replaceWith = ReplaceWith("connectEventsEnabled"))
    @SerializedName("connect_events")
    val isConnectEvents: Boolean = true,

    /**
     * Controls if messages should be searchable (this is a premium feature). Disabled by default.
     */
    @Deprecated("Use searchEnabled instead", level = DeprecationLevel.WARNING, replaceWith = ReplaceWith("searchEnabled"))
    @SerializedName("search")
    val isSearch: Boolean = true,

    /**
     * If users are allowed to add reactions to messages. Enabled by default.
     */
    @SerializedName("reactions")
    val isReactionsEnabled: Boolean = true,

    /**
     * Enables message threads and replies. Enabled by default.
     */
    @SerializedName("replies")
    val isRepliesEnabled: Boolean = true,

    /**
     * Determines if users are able to mute other users. Enabled by default.
     */
    @Deprecated("Use mutesEnabled instead", level = DeprecationLevel.WARNING, replaceWith = ReplaceWith("muteEnabled"))
    @SerializedName("mutes")
    val isMutes: Boolean = true,

    /**
     * Allows image and file uploads within messages. Enabled by default.
     */
    @SerializedName("uploads")
    val uploadsEnabled: Boolean = true,

    /**
     * Determines if URL enrichment enabled to show they as attachments. Enabled by default.
     */
    @SerializedName("url_enrichment")
    val urlEnrichmentEnabled: Boolean = true,

    @SerializedName("custom_events")
    val customEventsEnabled: Boolean = false,

    @SerializedName("push_notifications")
    val pushNotificationsEnabled: Boolean = true,

    /**
     * A number of days or infinite. "Infinite" by default.
     */
    @SerializedName("message_retention")
    val messageRetention: String = "infinite",

    /**
     * The max message length. 5000 by default.
     */
    @SerializedName("max_message_length")
    val maxMessageLength: Int = 5000,

    /**
     * Disabled, simple or AI are valid options for the Automod. AI based moderation is a premium feature.
     */
    val automod: String = "disabled",

    @SerializedName("automod_behavior")
    val automodBehavior: String = "",

    @SerializedName("blocklist_behavior")
    val blocklistBehavior: String = "",

    /**
     * The commands that are available on this channel type, e.g. /giphy.
     */
    val commands: List<Command> = mutableListOf(),
) {
    /**
     * Date of channel creation.
     */
    val createdAt: Date?
        get() = created_at

    /**
     * Date of last channel update.
     */
    val updatedAt: Date?
        get() = updated_at

    /**
     * Controls if typing indicators are shown. Enabled by default.
     */
    val typingEventsEnabled: Boolean
        get() = isTypingEvents

    /**
     * Controls whether the chat shows how far you’ve read. Enabled by default.
     */
    val readEventsEnabled: Boolean
        get() = isReadEvents

    /**
     * Determines if events are fired for connecting and disconnecting to a chat. Enabled by default.
     */
    val connectEventsEnabled: Boolean
        get() = isConnectEvents

    /**
     * Controls if messages should be searchable (this is a premium feature). Disabled by default.
     */
    val searchEnabled: Boolean
        get() = isSearch

    /**
     * Determines if users are able to mute other users. Enabled by default.
     */
    val mutesEnabled: Boolean
        get() = isMutes
}