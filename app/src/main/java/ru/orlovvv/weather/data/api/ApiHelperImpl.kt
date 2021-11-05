package ru.orlovvv.weather.data.api

import retrofit2.Response
import ru.orlovvv.weather.data.model.ForecastData
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val api: Api) : ApiHelper {

    override suspend fun getForecast(locationName: String): Response<ForecastData> =
        api.getForecast(locationName)

}