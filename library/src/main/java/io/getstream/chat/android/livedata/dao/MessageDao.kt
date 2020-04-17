package io.getstream.chat.android.livedata.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.getstream.chat.android.livedata.entity.MessageEntity
import java.util.*


@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(messageEntities: List<MessageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(messageEntity: MessageEntity)

    @Query("SELECT * from stream_chat_message WHERE cid = :cid AND createdAt > :dateFilter ORDER BY createdAt ASC LIMIT :limit")
    fun messagesForChannelNewerThan(cid: String, limit: Int = 100, dateFilter: Date): List<MessageEntity>

    @Query("SELECT * from stream_chat_message WHERE cid = :cid AND createdAt < :dateFilter ORDER BY createdAt ASC LIMIT :limit")
    fun messagesForChannelOlderThan(cid: String, limit: Int = 100, dateFilter: Date): List<MessageEntity>

    @Query("SELECT * from stream_chat_message WHERE cid = :cid ORDER BY createdAt ASC LIMIT :limit")
    fun messagesForChannel(cid: String, limit: Int = 100): List<MessageEntity>


    @Query(
            "SELECT * FROM stream_chat_message " +
                    "WHERE stream_chat_message.id IN (:ids)"
    )
    suspend fun select(ids: List<String>): List<MessageEntity>

    @Query(
            "SELECT * FROM stream_chat_message " +
                    "WHERE stream_chat_message.id IN (:id)"
    )
    suspend fun select(id: String?): MessageEntity?

    @Query(
            "SELECT * FROM stream_chat_message " +
                    "WHERE stream_chat_message.syncStatus IN (-1)"
    )
    suspend fun selectSyncNeeded(): List<MessageEntity>


}