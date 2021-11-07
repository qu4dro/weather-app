package ru.orlovvv.weather.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.orlovvv.weather.data.model.other.Current
import ru.orlovvv.weather.data.model.other.Forecast
import ru.orlovvv.weather.data.model.other.Location

class Converters {

    @TypeConverter
    fun currentToString(current: Current): String = Gson().toJson(current)

    @TypeConverter
    fun stringToCurrent(currentString: String): Current =
        Gson().fromJson(currentString, Current::class.java)

    @TypeConverter
    fun forecastToString(forecast: Forecast): String = Gson().toJson(forecast)

    @TypeConverter
    fun stringToForecast(currentForecast: String): Forecast =
        Gson().fromJson(currentForecast, Forecast::class.java)


    @TypeConverter
    fun locationToString(location: Location): String = Gson().toJson(location)

    @TypeConverter
    fun stringToLocation(locationString: String): Location =
        Gson().fromJson(locationString, Location::class.java)


}