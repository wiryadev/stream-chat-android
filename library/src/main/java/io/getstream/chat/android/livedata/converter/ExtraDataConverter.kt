package io.getstream.chat.android.livedata.converter

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import io.getstream.chat.android.livedata.gson


object ExtraDataConverter {
    @TypeConverter
    @JvmStatic
    fun stringToMap(data: String?): MutableMap<String, Any> {
        if (data == null) {
            return mutableMapOf()
        }
        val mapType = object :
            TypeToken<MutableMap<String?, Any?>?>() {}.type
        return gson.fromJson(
            data,
            mapType
        )
    }

    @TypeConverter
    @JvmStatic
    fun mapToString(someObjects: MutableMap<String?, Any?>?): String {
        return gson.toJson(someObjects)
    }
}