package ru.orlovvv.weather.data.model

data class Day(
    var avghumidity: Double,
    var avgtemp_c: Double,
    var avgtemp_f: Double,
    var avgvis_km: Double,
    var avgvis_miles: Double,
    var condition: Condition,
    var daily_chance_of_rain: Int,
    var daily_chance_of_snow: Int,
    var daily_will_it_rain: Int,
    var daily_will_it_snow: Int,
    var maxtemp_c: Double,
    var maxtemp_f: Double,
    var maxwind_kph: Double,
    var maxwind_mph: Double,
    var mintemp_c: Double,
    var mintemp_f: Double,
    var totalprecip_in: Double,
    var totalprecip_mm: Double,
    var uv: Double
)