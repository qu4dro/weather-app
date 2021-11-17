package ru.orlovvv.weather.utils

object Constants {

    // I did not hide it, but you generate your own
    const val API_KEY_PUBLIC = "960333831cf64e7788934918210111"

    const val FORECAST_DAYS = 7

    const val HOME_PAGES = 3

    const val DATABASE_NAME = "forecast_cache_db.db"

    const val DATE_PATTERN = "yyyy-MM-dd HH:mm"

    const val DATE_PATTERN_WITHOUT_HOURS = "yyyy-MM-dd"

    const val LOCATION_UPDATE_INTERVAL = 60000L
    const val FASTEST_LOCATION_INTERVAL = LOCATION_UPDATE_INTERVAL / 4
    const val REQUEST_CODE_LOCATION_PERMISSION = 0

}