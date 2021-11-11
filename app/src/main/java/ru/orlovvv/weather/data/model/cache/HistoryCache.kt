package ru.orlovvv.weather.data.model.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.orlovvv.weather.data.model.other.Forecast

@Entity(tableName = "history")
class HistoryCache(
    var forecast: Forecast,
    val locationId: Int
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int? = null
    var dateCreated: Long = System.currentTimeMillis()
}