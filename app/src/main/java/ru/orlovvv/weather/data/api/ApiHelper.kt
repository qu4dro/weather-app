package ru.orlovvv.weather.data.api

import retrofit2.Response
import ru.orlovvv.weather.data.model.ForecastData

interface ApiHelper {

    suspend fun getForecast(locationName: String): Response<ForecastData>

}