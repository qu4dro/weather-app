package ru.orlovvv.weather.data.model.other

import androidx.recyclerview.widget.DiffUtil

data class Hour(
    var chance_of_rain: Int,
    var chance_of_snow: Int,
    var cloud: Int,
    var condition: Condition,
    var dewpoint_c: Double,
    var dewpoint_f: Double,
    var feelslike_c: Double,
    var feelslike_f: Double,
    var gust_kph: Double,
    var gust_mph: Double,
    var heatindex_c: Double,
    var heatindex_f: Double,
    var humidity: Int,
    var is_day: Int,
    var precip_in: Double,
    var precip_mm: Double,
    var pressure_in: Double,
    var pressure_mb: Double,
    var temp_c: Double,
    var temp_f: Double,
    var time: String,
    var time_epoch: Int,
    var uv: Double = 0.0,
    var vis_km: Double,
    var vis_miles: Double,
    var will_it_rain: Int,
    var will_it_snow: Int,
    var wind_degree: Int,
    var wind_dir: String,
    var wind_kph: Double,
    var wind_mph: Double,
    var windchill_c: Double,
    var windchill_f: Double
)

object HourCallback : DiffUtil.ItemCallback<Hour>() {
    override fun areItemsTheSame(
        oldItem: Hour,
        newItem: Hour
    ) =
        oldItem.time_epoch == newItem.time_epoch

    override fun areContentsTheSame(
        oldItem: Hour,
        newItem: Hour
    ) =
        oldItem == newItem

}