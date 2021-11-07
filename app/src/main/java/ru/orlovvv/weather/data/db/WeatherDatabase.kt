package ru.orlovvv.weather.data.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.orlovvv.weather.data.Converters
import ru.orlovvv.weather.data.model.responses.ForecastResponse
import ru.orlovvv.weather.utils.Constants.DATABASE_NAME

@Database(entities = [ForecastResponse::class], exportSchema = false, version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun getWeatherDao(): WeatherDao

    companion object {
        @Volatile
        private var instance: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): WeatherDatabase {
            val database =
                Room.databaseBuilder(context, WeatherDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                        }
                    })
                    .build()

            return database
        }
    }

}