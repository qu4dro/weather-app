package ru.orlovvv.weather.data.model

data class Current(
    var cloud: Int,
    var condition: Condition,
    var feelslike_c: Double,
    var feelslike_f: Double,
    var gust_kph: Double,
    var gust_mph: Double,
    var humidity: Int,
    var is_day: Int,
    var last_updated: String,
    var last_updated_epoch: Int,
    var precip_in: Double,
    var precip_mm: Double,
    var pressure_in: Double,
    var pressure_mb: Double,
    var temp_c: Double,
    var temp_f: Double,
    var uv: Double,
    var vis_km: Double,
    var vis_miles: Double,
    var wind_degree: Int,
    var wind_dir: String,
    var wind_kph: Double,
    var wind_mph: Double
)