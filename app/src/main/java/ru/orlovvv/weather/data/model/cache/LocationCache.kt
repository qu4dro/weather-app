package ru.orlovvv.weather.data.model.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationCache(
    var country: String,
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var lat: Double,
    var lon: Double,
    var name: String,
    var region: String,
    var url: String
) {
    var dateCreated: Long = System.currentTimeMillis()
    var isMain: Boolean = false
}