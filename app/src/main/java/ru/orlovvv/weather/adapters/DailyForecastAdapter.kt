package ru.orlovvv.weather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.orlovvv.weather.data.model.other.DayCallback
import ru.orlovvv.weather.data.model.other.Forecastday
import ru.orlovvv.weather.databinding.ItemDailyForecastBinding

class DailyForecastAdapter :
    ListAdapter<Forecastday, DailyForecastAdapter.DailyForecastViewHolder>(DayCallback) {

    class DailyForecastViewHolder(val binding: ItemDailyForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var isExpanded = false

        fun bind(day: Forecastday) {
            binding.day = day
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DailyForecastViewHolder {
        return DailyForecastViewHolder(
            ItemDailyForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: DailyForecastViewHolder,
        position: Int
    ) {
        val day = getItem(position)
        holder.apply {
            binding.rvDailyForecast.adapter = HourlyForecastAdapter(isHorizontal = true)
            itemView.setOnClickListener {
                if (isExpanded) {
                    binding.groupExpanded.visibility = View.GONE
                    isExpanded = !isExpanded
                } else {
                    binding.groupExpanded.visibility = View.VISIBLE
                    isExpanded = !isExpanded
                }
            }
        }


        holder.bind(day)
    }
}