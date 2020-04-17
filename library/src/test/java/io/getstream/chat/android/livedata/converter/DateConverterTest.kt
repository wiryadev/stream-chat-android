package io.getstream.chat.android.livedata.converter

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import io.getstream.chat.android.client.api.models.QuerySort
import io.getstream.chat.android.livedata.BaseTest
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class DateConverterTest: BaseTest() {
    @Test
    fun testNullEncoding() {
        val converter = DateConverter()
        val output = converter.dateToTimestamp(null)
        val converted = converter.fromTimestamp(output)
        Truth.assertThat(converted).isNull()
    }

    @Test
    fun testSortEncoding() {
        val converter = DateConverter()
        val date = Date()
        val output = converter.dateToTimestamp(date)
        val converted = converter.fromTimestamp(output)
        Truth.assertThat(converted!!).isEqualTo(date)
    }

}