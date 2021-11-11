package ru.orlovvv.weather.data.model.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.orlovvv.weather.data.model.other.Current
import ru.orlovvv.weather.data.model.other.Forecast
import ru.orlovvv.weather.data.model.responses.HistoryResponse

@Entity(tableName = "forecast")
data class ForecastCache(
    var current: Current,
    var forecast: Forecast,
    val locationId: Int
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int? = null
    var dateCreated: Long = System.currentTimeMillis()
}
