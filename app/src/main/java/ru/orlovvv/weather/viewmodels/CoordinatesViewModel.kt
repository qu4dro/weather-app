package ru.orlovvv.weather.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.orlovvv.weather.utils.LocationLiveData
import javax.inject.Inject

@HiltViewModel
class CoordinatesViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private val _locationLiveData = LocationLiveData(application)
    val locationLiveData
        get() = _locationLiveData

    var isGpsEnabled = false

}