package ru.orlovvv.weather.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.orlovvv.weather.data.model.other.LocationCacheData
import ru.orlovvv.weather.data.model.responses.ForecastResponse

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecastCache(locationCacheData: LocationCacheData)

    @Query("DELETE FROM forecast WHERE locationId = :id")
    suspend fun clearCache(id: Int)

    @Query("SELECT * FROM forecast WHERE locationId = :id ORDER BY dateCreated DESC LIMIT 1")
    fun getForecastCache(id: Int): LiveData<LocationCacheData>

    @Transaction
    suspend fun clearAndInsertCache(
        locationCacheData: LocationCacheData
    ) {
        clearCache(locationCacheData.locationId)
        insertForecastCache(locationCacheData)
    }

}