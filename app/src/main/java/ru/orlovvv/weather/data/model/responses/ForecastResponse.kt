package ru.orlovvv.weather.data.model.responses

import ru.orlovvv.weather.data.model.other.Current
import ru.orlovvv.weather.data.model.other.Forecast
import ru.orlovvv.weather.data.model.other.Location

data class ForecastResponse(
    var current: Current,
    var forecast: Forecast,
    var location: Location
)