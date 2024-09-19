package com.sample.rickandmorty.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap

object DateUtils {

    // Cache for DateTimeFormatter instances based on Locale
    private val formatterCache = ConcurrentHashMap<Locale, DateTimeFormatter>()

    // Default locale formatter
    private val defaultFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")

    // Function to create or get cached DateTimeFormatter
    private fun getOutputFormatter(locale: Locale): DateTimeFormatter {
        return formatterCache.getOrPut(locale) {
            DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy HH:mm", locale)
        }
    }

    // Function to format ISO 8601 date string to a human-readable format
    fun formatDate(isoDate: String, locale: Locale = Locale.getDefault()): String {
        return try {
            val outputFormatter = getOutputFormatter(locale)

            // Parse and format the date
            ZonedDateTime.parse(isoDate, defaultFormatter).format(outputFormatter)
        } catch (e: DateTimeParseException) {
            "Unknown Date"
        }
    }
}
