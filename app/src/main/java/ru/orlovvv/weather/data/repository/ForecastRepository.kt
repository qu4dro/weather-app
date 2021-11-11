package ru.orlovvv.weather.data.repository

import ru.orlovvv.weather.data.api.ApiHelper
import ru.orlovvv.weather.data.db.WeatherDao
import ru.orlovvv.weather.data.model.cache.ForecastCache
import ru.orlovvv.weather.data.model.cache.HistoryCache
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
    suspend fun insertForecastCache(forecastCache: ForecastCache) =
        weatherDao.insertForecastCache(forecastCache)

    suspend fun insertHistoryCache(historyCache: HistoryCache) =
        weatherDao.insertHistoryCache(historyCache)

    fun getForecastCache(id: Int) = weatherDao.getForecastCache(id)
    fun getHistoryCache(id: Int) = weatherDao.getHistoryCache(id)
}