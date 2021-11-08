package ru.orlovvv.weather.data.model.other

import androidx.recyclerview.widget.DiffUtil

data class Forecastday(
    var astro: Astro,
    var date: String,
    var date_epoch: Int,
    var day: Day,
    var hour: List<Hour>
)

object DayCallback : DiffUtil.ItemCallback<Forecastday>() {
    override fun areItemsTheSame(
        oldItem: Forecastday,
        newItem: Forecastday
    ) =
        oldItem.date_epoch == newItem.date_epoch

    override fun areContentsTheSame(
        oldItem: Forecastday,
        newItem: Forecastday
    ) =
        oldItem == newItem

}