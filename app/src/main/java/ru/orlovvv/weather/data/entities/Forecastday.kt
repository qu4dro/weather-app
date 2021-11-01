package ru.orlovvv.weather.data.entities

data class Forecastday(
    var astro: Astro,
    var date: String,
    var date_epoch: Int,
    var day: Day,
    var hour: List<Hour>
)