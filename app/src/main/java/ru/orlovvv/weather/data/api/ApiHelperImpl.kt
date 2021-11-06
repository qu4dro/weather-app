package ru.orlovvv.weather.data.api

import retrofit2.Response
import ru.orlovvv.weather.data.model.responses.ForecastResponse
import ru.orlovvv.weather.data.model.responses.SearchResponse
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val api: Api) : ApiHelper {

    override suspend fun getForecast(locationName: String): Response<ForecastResponse> =
        api.getForecast(locationName)

    override suspend fun searchLocation(searchQuery: String): Response<SearchResponse> =
        api.searchLocation(searchQuery)


}