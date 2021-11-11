package ru.orlovvv.weather.utils

import ru.orlovvv.weather.utils.Constants.DATE_PATTERN
import ru.orlovvv.weather.utils.Constants.DATE_PATTERN_WITHOUT_HOURS
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val calendar = Calendar.getInstance()
    private val date = calendar.time

    fun getLastWeek(pattern: String = DATE_PATTERN_WITHOUT_HOURS): String =
        SimpleDateFormat(pattern, Locale.ENGLISH).format(date.time - 7 * 24 * 60 * 60 * 1000)

    fun getToday(pattern: String = DATE_PATTERN_WITHOUT_HOURS): String =
        SimpleDateFormat(pattern, Locale.ENGLISH).format(date.time)

    fun getYesterday(pattern: String = DATE_PATTERN_WITHOUT_HOURS): String =
        SimpleDateFormat(pattern, Locale.ENGLISH).format(date.time - 1 * 24 * 60 * 60 * 1000)

    fun formatDateToHour(date: String): String {
        val outputFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val inputFormat: DateFormat = SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH)
        return outputFormat.format(inputFormat.parse(date))
    }

    fun formatDateToDayString(date: String): String {
        val outputFormat: DateFormat = SimpleDateFormat("EEEE, d MMM", Locale.ENGLISH)
        val inputFormat: DateFormat = SimpleDateFormat(DATE_PATTERN_WITHOUT_HOURS, Locale.ENGLISH)
        return outputFormat.format(inputFormat.parse(date))
    }

}