package ru.orlovvv.weather.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.orlovvv.weather.data.model.responses.ForecastResponse
import ru.orlovvv.weather.utils.Constants.API_KEY_PUBLIC
import ru.orlovvv.weather.utils.Constants.FORECAST_DAYS

interface Api {

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") locationName: String = "",
        @Query("days") days: Int = FORECAST_DAYS,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no",
        @Query("key") apiKey: String = API_KEY_PUBLIC
    ): Response<ForecastResponse>

}