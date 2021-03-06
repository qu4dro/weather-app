package ru.orlovvv.weather.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.orlovvv.weather.data.model.cache.ForecastCache
import ru.orlovvv.weather.data.model.cache.HistoryCache
import ru.orlovvv.weather.data.model.cache.LocationCache

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecastCache(forecastCache: ForecastCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryCache(historyCache: HistoryCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationCache(locationCache: LocationCache)

    @Query("DELETE FROM locations WHERE id = :id")
    suspend fun deleteLocation(id: Int)

    @Query("DELETE FROM forecast WHERE locationId = :id")
    suspend fun deleteForecastCache(id: Int)

    @Query("DELETE FROM history WHERE locationId = :id")
    suspend fun deleteHistoryCache(id: Int)

    @Query("SELECT * FROM forecast WHERE locationId = :id ORDER BY dateCreated DESC LIMIT 1")
    fun getForecastCache(id: Int): LiveData<ForecastCache>

    @Query("SELECT * FROM history WHERE locationId = :id ORDER BY dateCreated DESC LIMIT 1")
    fun getHistoryCache(id: Int): LiveData<HistoryCache>

    @Query("SELECT * FROM locations ORDER BY isMain AND dateCreated DESC")
    fun getLocationsCache(): LiveData<List<LocationCache>>

    @Query("SELECT * FROM locations ORDER BY isMain DESC LIMIT 1")
    fun getMainLocation(): LiveData<LocationCache>

    @Query("UPDATE locations SET isMain = :isMain WHERE id = :id")
    fun updateIsMain(isMain: Boolean, id: Int)

    @Transaction
    suspend fun updateMainLocation(
        oldLocation: LocationCache,
        newLocation: LocationCache
    ) {
        updateIsMain(false, oldLocation.id)
        updateIsMain(true, newLocation.id)
    }

    @Transaction
    suspend fun clearAndInsertForecastCache(
        forecastCache: ForecastCache
    ) {
        deleteForecastCache(forecastCache.locationId)
        insertForecastCache(forecastCache)
    }

    @Transaction
    suspend fun clearAndInsertHistoryCache(
        historyCache: HistoryCache
    ) {
        deleteHistoryCache(historyCache.locationId)
        insertHistoryCache(historyCache)
    }

}