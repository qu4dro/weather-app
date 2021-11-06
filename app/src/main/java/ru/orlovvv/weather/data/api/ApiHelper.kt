package ru.orlovvv.weather.data.api

import retrofit2.Response
import ru.orlovvv.weather.data.model.responses.ForecastResponse
import ru.orlovvv.weather.data.model.responses.SearchResponse

interface ApiHelper {

    suspend fun getForecast(locationName: String): Response<ForecastResponse>

    suspend fun searchLocation(searchQuery: String): Response<SearchResponse>

}