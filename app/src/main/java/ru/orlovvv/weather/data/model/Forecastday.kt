package ru.orlovvv.weather.data.model

data class Forecastday(
    var astro: Astro,
    var date: String,
    var date_epoch: Int,
    var day: Day,
    var hour: List<Hour>
)