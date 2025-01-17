package io.getstream.chat.android.client.parser2

import io.getstream.chat.android.client.parser2.adapters.DateAdapter
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.jupiter.api.Test
import java.util.Date

internal class DateAdapterTest {

    private val dateAdapter = DateAdapter()

    @Test
    fun readValidDates() {
        dateAdapter.fromJson("\"2020-06-29T06:14:28Z\"")!!.time shouldBeEqualTo 1593411268000
        dateAdapter.fromJson("\"2020-06-29T06:14:28.0Z\"")!!.time shouldBeEqualTo 1593411268000
        dateAdapter.fromJson("\"2020-06-29T06:14:28.00Z\"")!!.time shouldBeEqualTo 1593411268000
        dateAdapter.fromJson("\"2020-06-29T06:14:28.000Z\"")!!.time shouldBeEqualTo 1593411268000
        dateAdapter.fromJson("\"2020-06-29T06:14:28.100Z\"")!!.time shouldBeEqualTo 1593411268100
    }

    @Test
    fun readEmptyDate() {
        dateAdapter.fromJson("\"\"").shouldBeNull()
    }

    @Test
    fun readNullDate() {
        dateAdapter.fromJson("null").shouldBeNull()
    }

    @Test
    fun readNonsenseDate() {
        dateAdapter.fromJson("\"bla bla bla\"").shouldBeNull()
    }

    @Test
    fun writeValidDate() {
        val result = dateAdapter.toJson(Date(1593411268000))
        result shouldBeEqualTo "\"2020-06-29T06:14:28.000Z\""
    }

    @Test
    fun writeNullValue() {
        val result = dateAdapter.toJson(null)
        result shouldBeEqualTo "null"
    }
}
