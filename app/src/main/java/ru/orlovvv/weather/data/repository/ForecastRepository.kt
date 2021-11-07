package ru.orlovvv.weather.data.repository

import ru.orlovvv.weather.data.api.ApiHelper
import ru.orlovvv.weather.data.db.WeatherDao
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val weatherDao: WeatherDao
) {

    suspend fun getForecast(locationName: String) = apiHelper.getForecast(locationName)

    suspend fun searchLocation(searchQuery: String) = apiHelper.searchLocation(searchQuery)

}