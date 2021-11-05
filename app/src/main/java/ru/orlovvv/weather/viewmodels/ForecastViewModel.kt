package ru.orlovvv.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.orlovvv.weather.data.model.ForecastData
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

    private val _forecast = MutableLiveData<Resource<ForecastData>>()
    val forecast: LiveData<Resource<ForecastData>>
        get() = _forecast

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
                _forecast.postValue(Resource.Error("No internet connection"))
            }
        } catch (e: Exception) {
            _forecast.postValue(Resource.Error("Can not get forecast: ${e.message}"))
        }
    }

    private fun handleForecastResponse(response: Response<ForecastData>): Resource<ForecastData> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.errorBody().toString())
    }

}