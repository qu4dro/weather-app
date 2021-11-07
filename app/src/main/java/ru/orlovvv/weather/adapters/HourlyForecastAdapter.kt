package ru.orlovvv.weather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.orlovvv.weather.data.model.other.Hour
import ru.orlovvv.weather.data.model.other.HourCallback
import ru.orlovvv.weather.databinding.ItemHourlyForecastBinding

class HourlyForecastAdapter :
    ListAdapter<Hour, HourlyForecastAdapter.HourlyForecastViewHolder>(HourCallback) {

    class HourlyForecastViewHolder(private val binding: ItemHourlyForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hour: Hour) {
            binding.hour = hour
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HourlyForecastViewHolder {
        return HourlyForecastViewHolder(
            ItemHourlyForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: HourlyForecastViewHolder,
        position: Int
    ) {
        val hour = getItem(position)
        holder.bind(hour)
    }

}