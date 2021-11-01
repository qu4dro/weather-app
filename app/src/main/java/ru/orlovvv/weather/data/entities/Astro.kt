package ru.orlovvv.weather.data.entities

data class Astro(
    var moon_illumination: String,
    var moon_phase: String,
    var moonrise: String,
    var moonset: String,
    var sunrise: String,
    var sunset: String
)