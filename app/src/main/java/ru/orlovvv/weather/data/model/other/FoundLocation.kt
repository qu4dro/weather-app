package ru.orlovvv.weather.data.model.other

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class FoundLocation(
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
}