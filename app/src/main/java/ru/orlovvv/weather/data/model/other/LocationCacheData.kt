package ru.orlovvv.weather.data.model.other

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.orlovvv.weather.data.model.responses.HistoryResponse

@Entity(tableName = "forecast")
data class LocationCacheData(
    var current: Current,
    var forecast: Forecast,
    var history: HistoryResponse,
    val locationId: Int
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int? = null
    var dateCreated: Long = System.currentTimeMillis()
}
