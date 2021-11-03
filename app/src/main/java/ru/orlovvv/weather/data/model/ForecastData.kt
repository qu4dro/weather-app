package ru.orlovvv.weather.data.model

data class ForecastData(
    var current: Current,
    var forecast: Forecast,
    var location: Location
)