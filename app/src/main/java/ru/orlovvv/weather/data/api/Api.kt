package ru.orlovvv.weather.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.orlovvv.weather.data.model.responses.ForecastResponse
import ru.orlovvv.weather.data.model.responses.HistoryResponse
import ru.orlovvv.weather.data.model.responses.SearchResponse
import ru.orlovvv.weather.utils.Constants.API_KEY_PUBLIC
import ru.orlovvv.weather.utils.Constants.FORECAST_DAYS
import ru.orlovvv.weather.utils.DateUtils

interface Api {

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") locationName: String = "",
        @Query("days") days: Int = FORECAST_DAYS,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no",
        @Query("key") apiKey: String = API_KEY_PUBLIC
    ): Response<ForecastResponse>

    @GET("forecast.json")
    suspend fun searchLocation(
        @Query("q") searchQuery: String = "",
        @Query("key") apiKey: String = API_KEY_PUBLIC
    ): Response<SearchResponse>

    @GET("history.json")
    suspend fun getForecastHistory(
        @Query("q") locationName: String = "",
        @Query("dt") date: String = DateUtils.getLastWeek(),
        @Query("end_dt") dateEnd: String = DateUtils.getYesterday(),
        @Query("key") apiKey: String = API_KEY_PUBLIC
    ): Response<HistoryResponse>

}