package ru.orlovvv.weather.data.repository

import ru.orlovvv.weather.data.api.ApiHelper
import ru.orlovvv.weather.data.db.WeatherDao
import ru.orlovvv.weather.data.model.other.LocationCacheData
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val weatherDao: WeatherDao
) {

    // Network
    suspend fun getForecast(locationName: String) = apiHelper.getForecast(locationName)

    suspend fun searchLocation(searchQuery: String) = apiHelper.searchLocation(searchQuery)

    suspend fun getForecastHistory(locationName: String) = apiHelper.getForecastHistory(locationName)

    // DB cache
    suspend fun insertCache(locationCacheData: LocationCacheData) =
        weatherDao.clearAndInsertCache(locationCacheData)

    fun getForecastCache(id: Int) = weatherDao.getForecastCache(id)
}