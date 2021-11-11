package ru.orlovvv.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.orlovvv.weather.data.model.other.FoundLocation
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

    private var _selectedLocation =
        MutableLiveData<FoundLocation>(FoundLocation("", -2, 0.0, 0.0, "Irkutsk", "", ""))
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
    private val _foundedLocations = MutableLiveData<Resource<SearchResponse>>()
    val foundedLocations: LiveData<Resource<SearchResponse>>
        get() = _foundedLocations

    // Forecast history from server
    private val _forecastHistory = MutableLiveData<Resource<HistoryResponse>>()
    val forecastHistory: LiveData<Resource<HistoryResponse>>
        get() = _forecastHistory

    // Cached forecast data
    private val _forecastCache =
        forecastRepository.getForecastCache(_selectedLocation.value?.id!!)
    val forecastCache
        get() = _forecastCache

    init {
        _selectedLocation.value?.let { getForecast(it.name) }
        _selectedLocation.value?.let { getHistory(it.name) }
    }

    fun getForecast(locationName: String? = _selectedLocation.value?.name) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _forecast.postValue(Resource.Loading())
            if (networkHelper.isNetworkConnected()) {
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

    fun getHistory(locationName: String? = _selectedLocation.value?.name) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _forecastHistory.postValue(Resource.Loading())
            if (networkHelper.isNetworkConnected()) {
                val response = locationName?.let { forecastRepository.getForecastHistory(it) }
                _forecastHistory.postValue(response?.let { handleForecastHistoryResponse(it) })
            } else {
                _foundedLocations.postValue(Resource.Error("Error"))
            }
        } catch (e: Exception) {
            _foundedLocations.postValue(Resource.Error("Can not get forecast history: ${e.message}"))
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


    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }


    // TODO REFACTOR
//    fun insertCache() = viewModelScope.launch {
//
//        if (forecast.value?.data?.current!! != forecastCache.value?.current) {
//            val current = _forecast.value?.data?.current
//            val forecast = _forecast.value?.data?.forecast
//            val locationId = _selectedLocation.value?.id
//            val history = _forecastHistory.value?.data
//
//            val locationCacheData = LocationCacheData(
//                current!!,
//                forecast!!,
//                history!!,
//                locationId!!
//            )
//
//            forecastRepository.insertCache(locationCacheData)
//        }
//
//    }

}