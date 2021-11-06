package ru.orlovvv.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.orlovvv.weather.data.model.responses.ForecastResponse
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

    private val _forecast = MutableLiveData<Resource<ForecastResponse>>()
    val forecast: LiveData<Resource<ForecastResponse>>
        get() = _forecast

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
                val response = forecastRepository.getForecast("Irkutsk")
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
        _searchQuery.postValue(query)
    }

}