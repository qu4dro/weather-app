package ru.orlovvv.weather.data.api

import retrofit2.Response
import ru.orlovvv.weather.data.model.cache.LocationCache
import ru.orlovvv.weather.data.model.responses.ForecastResponse
import ru.orlovvv.weather.data.model.responses.HistoryResponse
import ru.orlovvv.weather.data.model.responses.SearchResponse
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val api: Api) : ApiHelper {

    override suspend fun getForecast(locationName: String): Response<ForecastResponse> =
        api.getForecast(locationName)

    override suspend fun searchLocation(searchQuery: String): Response<List<LocationCache>> =
        api.searchLocation(searchQuery)

    override suspend fun getForecastHistory(locationName: String): Response<HistoryResponse> =
        api.getForecastHistory(locationName)


}