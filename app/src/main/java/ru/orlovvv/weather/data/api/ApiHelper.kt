package ru.orlovvv.weather.data.api

import retrofit2.Response
import ru.orlovvv.weather.data.model.responses.ForecastResponse

interface ApiHelper {

    suspend fun getForecast(locationName: String): Response<ForecastResponse>

}