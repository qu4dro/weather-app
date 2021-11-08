package ru.orlovvv.weather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.orlovvv.weather.data.model.other.Hour
import ru.orlovvv.weather.data.model.other.HourCallback
import ru.orlovvv.weather.databinding.ItemHourlyForecastHorizontalBinding
import ru.orlovvv.weather.databinding.ItemHourlyForecastVerticalBinding

class HourlyForecastAdapter(private val isHorizontal: Boolean = false) :
    ListAdapter<Hour, RecyclerView.ViewHolder>(HourCallback) {

    class HourlyForecastVerticalViewHolder(private val binding: ItemHourlyForecastVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hour: Hour) {
            binding.hour = hour
            binding.executePendingBindings()
        }
    }

    class HourlyForecastHorizontalViewHolder(private val binding: ItemHourlyForecastHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hour: Hour) {
            binding.hour = hour
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if (isHorizontal) {
            return HourlyForecastHorizontalViewHolder(
                ItemHourlyForecastHorizontalBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return HourlyForecastVerticalViewHolder(
                ItemHourlyForecastVerticalBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val hour = getItem(position)
        when (holder) {
            is HourlyForecastHorizontalViewHolder -> holder.bind(hour)
            is HourlyForecastVerticalViewHolder -> holder.bind(hour)
        }
    }

}