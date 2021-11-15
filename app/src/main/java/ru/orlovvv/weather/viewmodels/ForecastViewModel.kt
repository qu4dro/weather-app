package ru.orlovvv.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.orlovvv.weather.data.model.cache.LocationCache
import ru.orlovvv.weather.data.model.cache.ForecastCache
import ru.orlovvv.weather.data.model.cache.HistoryCache
import ru.orlovvv.weather.data.model.responses.ForecastResponse
import ru.orlovvv.weather.data.model.responses.HistoryResponse
import ru.orlovvv.weather.data.model.responses.SearchResponse
import ru.orlovvv.weather.data.repository.ForecastRepository
import ru.orlovvv.weather.utils.NetworkHelper
import ru.orlovvv.weather.utils.Resource
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private var _selectedLocation = forecastRepository.getMainLocation()
    val selectedLocation
        get() = _selectedLocation

    private var _searchQuery = MutableLiveData("")
    val searchQuery
        get() = _searchQuery

    // Forecast from server
    private val _forecast = MutableLiveData<Resource<ForecastResponse>>()
    val forecast: LiveData<Resource<ForecastResponse>>
        get() = _forecast

    // Locations list founded by search from server
    private val _foundedLocations = MutableLiveData<Resource<List<LocationCache>>>()
    val foundedLocations: LiveData<Resource<List<LocationCache>>>
        get() = _foundedLocations

    // Forecast history from server
    private val _forecastHistory = MutableLiveData<Resource<HistoryResponse>>()
    val forecastHistory: LiveData<Resource<HistoryResponse>>
        get() = _forecastHistory

    // Cached forecast data
    private val _forecastCache =
        forecastRepository.getForecastCache(_selectedLocation.value?.id ?: -1)
    val forecastCache
        get() = _forecastCache

    // Cached history data
    private val _historyCache =
        forecastRepository.getHistoryCache(_selectedLocation.value?.id ?: -1)
    val historyCache
        get() = _historyCache

    init {
        _selectedLocation.value?.let { getForecast(it.name) }
        _selectedLocation.value?.let { getForecastHistory(it.name) }
    }

    /* Network requests */
    fun getForecast(locationName: String? = _selectedLocation.value?.name) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (networkHelper.isNetworkConnected()) {
                    _forecast.postValue(Resource.Loading())
                    val response = locationName?.let { forecastRepository.getForecast(it) }
                    _forecast.postValue(response?.let { handleForecastResponse(it) })
                } else {
                    _forecast.postValue(Resource.Error("Error"))
                }
            } catch (e: Exception) {
                _forecast.postValue(Resource.Error("Can not get forecast: ${e.message}"))
            }
        }

    private fun handleForecastResponse(response: Response<ForecastResponse>): Resource<ForecastResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.errorBody().toString())
    }

    fun searchLocation(searchQuery: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (networkHelper.isNetworkConnected()) {
                _foundedLocations.postValue(Resource.Loading())
                val response = forecastRepository.searchLocation(searchQuery)
                _foundedLocations.postValue(handleSearchLocationResponse(response))
            } else {
                _foundedLocations.postValue(Resource.Error("Error"))
            }
        } catch (e: Exception) {
            _foundedLocations.postValue(Resource.Error("Can not get location: ${e.message}"))
        }
    }

    private fun handleSearchLocationResponse(response: Response<List<LocationCache>>): Resource<List<LocationCache>> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.errorBody().toString())
    }

    fun getForecastHistory(locationName: String? = _selectedLocation.value?.name) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (networkHelper.isNetworkConnected()) {
                    _forecastHistory.postValue(Resource.Loading())
                    val response = locationName?.let { forecastRepository.getForecastHistory(it) }
                    _forecastHistory.postValue(response?.let { handleForecastHistoryResponse(it) })
                } else {
                    _forecastHistory.postValue(Resource.Error("Error"))
                }
            } catch (e: Exception) {
                _forecastHistory.postValue(Resource.Error("Can not get forecast history: ${e.message}"))
            }
        }

    private fun handleForecastHistoryResponse(response: Response<HistoryResponse>): Resource<HistoryResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.errorBody().toString())
    }
    /* --------------- */

    fun insertHistoryCache() = viewModelScope.launch {
        val networkHistory = _forecastHistory.value?.data
        val cacheHistory = _historyCache.value

        if (networkHistory?.forecast != cacheHistory?.forecast) {
            val forecast = networkHistory?.forecast
            val locationId = _selectedLocation.value?.id
            val newCache = HistoryCache(forecast!!, locationId!!)
            forecastRepository.insertHistoryCache(newCache)
        }
    }

    fun insertForecastCache() = viewModelScope.launch {
        val networkForecast = _forecast.value?.data
        val cacheForecast = _forecastCache.value

        if (networkForecast?.current != cacheForecast?.current) {
            val current = _forecast.value?.data?.current
            val forecast = _forecast.value?.data?.forecast
            val locationId = _selectedLocation.value?.id
            val newCache = ForecastCache(
                current!!,
                forecast!!,
                locationId!!
            )
            forecastRepository.insertForecastCache(newCache)
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

}