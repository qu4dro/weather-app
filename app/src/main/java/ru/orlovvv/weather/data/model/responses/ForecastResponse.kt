package ru.orlovvv.weather.data.model.responses

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.orlovvv.weather.data.model.other.Current
import ru.orlovvv.weather.data.model.other.Forecast
import ru.orlovvv.weather.data.model.other.Location

@Entity(tableName = "forecast")
data class ForecastResponse(
    var current: Current,
    var forecast: Forecast,
    var location: Location
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var dateCreated: Long = System.currentTimeMillis()
}