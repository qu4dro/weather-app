package ru.orlovvv.weather.data.model.responses

import ru.orlovvv.weather.data.model.other.Forecast
import ru.orlovvv.weather.data.model.other.Location

data class HistoryResponse(
    var forecast: Forecast,
    var location: Location
)