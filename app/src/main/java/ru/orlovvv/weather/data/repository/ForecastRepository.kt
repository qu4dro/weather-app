package ru.orlovvv.weather.data.repository

import ru.orlovvv.weather.data.api.ApiHelper
import javax.inject.Inject

class ForecastRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getForecast(locationName: String) = apiHelper.getForecast(locationName)

    suspend fun searchLocation(searchQuery: String) = apiHelper.searchLocation(searchQuery)

}