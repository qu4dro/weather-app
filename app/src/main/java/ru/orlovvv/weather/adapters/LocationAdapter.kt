package ru.orlovvv.weather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.orlovvv.weather.data.model.cache.LocationCache
import ru.orlovvv.weather.data.model.cache.LocationCallback
import ru.orlovvv.weather.databinding.ItemFoundedLocationBinding
import ru.orlovvv.weather.databinding.ItemSavedLocationBinding

class LocationAdapter(private val isSaved: Boolean = true) :
    ListAdapter<LocationCache, RecyclerView.ViewHolder>(LocationCallback) {

    class LocationSavedViewHolder(private val binding: ItemSavedLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(location: LocationCache) {
            binding.location = location
            binding.executePendingBindings()
        }
    }

    class LocationFoundedViewHolder(private val binding: ItemFoundedLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(location: LocationCache) {
            binding.location = location
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (isSaved) {
            return LocationSavedViewHolder(
                ItemSavedLocationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return LocationFoundedViewHolder(
                ItemFoundedLocationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val location = getItem(position)
        when (holder) {
            is LocationFoundedViewHolder -> holder.bind(location)
            is LocationSavedViewHolder -> holder.bind(location)
        }
    }
}