package com.patres.timetable.web.rest

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.hamcrest.Description
import org.hamcrest.TypeSafeDiagnosingMatcher
import org.springframework.http.MediaType

import java.io.IOException
import java.nio.charset.Charset
import java.time.ZonedDateTime
import java.time.format.DateTimeParseException

import org.assertj.core.api.Assertions.assertThat

/**
 * Utility class for testing REST controllers.
 */
object TestUtil {

    /** MediaType for JSON UTF8  */
    val APPLICATION_JSON_UTF8 = MediaType(
            MediaType.APPLICATION_JSON.type,
            MediaType.APPLICATION_JSON.subtype, Charset.forName("utf8"))

    /**
     * Convert an object to JSON byte array.
     *
     * @param object
     * the object to convertToServer
     * @return the JSON byte array
     * @throws IOException
     */
    @Throws(IOException::class)
    fun convertObjectToJsonBytes(`object`: Any): ByteArray {
        val mapper = ObjectMapper()
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)

        val module = JavaTimeModule()
        mapper.registerModule(module)

        return mapper.writeValueAsBytes(`object`)
    }

    /**
     * Create a byte array with a specific size filled with specified data.
     *
     * @param size the size of the byte array
     * @param data the data to put in the byte array
     * @return the JSON byte array
     */
    fun createByteArray(size: Int, data: String): ByteArray {
        val byteArray = ByteArray(size)
        for (i in 0 until size) {
            byteArray[i] = java.lang.Byte.parseByte(data, 2)
        }
        return byteArray
    }

    /**
     * A matcher that tests that the examined string represents the same instant as the reference datetime.
     */
    class ZonedDateTimeMatcher(private val date: ZonedDateTime) : TypeSafeDiagnosingMatcher<String>() {

        override fun matchesSafely(item: String, mismatchDescription: Description): Boolean {
            try {
                if (!date.isEqual(ZonedDateTime.parse(item))) {
                    mismatchDescription.appendText("was ").appendValue(item)
                    return false
                }
                return true
            } catch (e: DateTimeParseException) {
                mismatchDescription.appendText("was ").appendValue(item)
                        .appendText(", which could not be parsed as a ZonedDateTime")
                return false
            }

        }

        override fun describeTo(description: Description) {
            description.appendText("a String representing the same Instant as ").appendValue(date)
        }
    }

    /**
     * Creates a matcher that matches when the examined string reprensents the same instant as the reference datetime
     * @param date the reference datetime against which the examined string is checked
     */
    fun sameInstant(date: ZonedDateTime): ZonedDateTimeMatcher {
        return ZonedDateTimeMatcher(date)
    }


}
