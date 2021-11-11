package ru.orlovvv.weather.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    fun getLastWeek(): String {
        val calendar = Calendar.getInstance()
        val date = calendar.time
        return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(date.time - 7 * 24 * 60 * 60 * 1000)
    }

}