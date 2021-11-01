package ru.orlovvv.weather.data.entities

data class ForecastData(
    var current: Current,
    var forecast: Forecast,
    var location: Location
)