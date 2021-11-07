package ru.orlovvv.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.orlovvv.weather.data.model.other.Current
import ru.orlovvv.weather.data.model.other.FoundLocation
import ru.orlovvv.weather.data.model.other.Location
import ru.orlovvv.weather.data.model.other.LocationCacheData
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

    private var _searchQuery = MutableLiveData("")
    val searchQuery
        get() = _searchQuery

    private var _selectedLocation =
        MutableLiveData<FoundLocation>(FoundLocation("", -1, 0.0, 0.0, "", "", ""))

    val selectedLocation
        get() = _selectedLocation

    private val _forecast = MutableLiveData<Resource<ForecastResponse>>()
    val forecast: LiveData<Resource<ForecastResponse>>
        get() = _forecast

    private val _forecastCache =
        forecastRepository.getForecastCache(_selectedLocation.value?.id!!)
    val forecastCache
        get() = _forecastCache

    private val _foundedLocations = MutableLiveData<Resource<SearchResponse>>()
    val foundedLocations: LiveData<Resource<SearchResponse>>
        get() = _foundedLocations

    init {
        getForecast()
    }

    fun getForecast() = viewModelScope.launch(Dispatchers.IO) {
        try {
            _forecast.postValue(Resource.Loading())
            if (networkHelper.isNetworkConnected()) {
                val response = forecastRepository.getForecast("London")
                _forecast.postValue(handleForecastResponse(response))
            } else {
                _forecast.postValue(Resource.Error("Error"))
            }
        } catch (e: Exception) {
            _forecast.postValue(Resource.Error("Can not get forecast: ${e.message}"))
        }
    }

    fun searchLocation(searchQuery: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _foundedLocations.postValue(Resource.Loading())
            if (networkHelper.isNetworkConnected()) {
                val response = forecastRepository.searchLocation(searchQuery)
                _foundedLocations.postValue(handleSearchLocationResponse(response))
            } else {
                _foundedLocations.postValue(Resource.Error("Error"))
            }
        } catch (e: Exception) {
            _foundedLocations.postValue(Resource.Error("Can not get forecast: ${e.message}"))
        }
    }

    private fun handleSearchLocationResponse(response: Response<SearchResponse>): Resource<SearchResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.errorBody().toString())
    }

    private fun handleForecastResponse(response: Response<ForecastResponse>): Resource<ForecastResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.errorBody().toString())
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }


    // TODO REFACTOR
    fun insertCache() = viewModelScope.launch {

        if (forecast.value?.data?.current!! != forecastCache.value?.current) {
            val current = _forecast.value?.data?.current
            val forecast = _forecast.value?.data?.forecast
            val locationId = _selectedLocation.value?.id

            val locationCacheData = LocationCacheData(
                current!!,
                forecast!!,
                HistoryResponse(forecast, Location("", 2.0, "", 2, 2.0, "", "", "")),
                locationId!!
            )

            forecastRepository.insertCache(locationCacheData)
        }

    }

}