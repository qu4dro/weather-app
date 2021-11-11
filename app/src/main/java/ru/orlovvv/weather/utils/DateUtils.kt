package ru.orlovvv.weather.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    private val calendar = Calendar.getInstance()
    private val date = calendar.time

    fun getLastWeek(): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(date.time - 7 * 24 * 60 * 60 * 1000)

    fun getToday(): String = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(date.time)

    fun getYesterday(): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(date.time - 1 * 24 * 60 * 60 * 1000)

}