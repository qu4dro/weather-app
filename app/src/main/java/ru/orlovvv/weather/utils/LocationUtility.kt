package ru.orlovvv.weather.utils

import android.Manifest
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions
import ru.orlovvv.weather.data.model.other.Coordinates
import java.util.*

object LocationUtility {

    fun hasLocationPermissions(context: Context) =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
                //place for code for background location
            )
        }


    fun getCityString(coordinates: Coordinates, context: Context): String {
        val addresses: List<Address>
        val geocoder: Geocoder = Geocoder(context, Locale.getDefault())

        addresses = geocoder.getFromLocation(
            coordinates.lat,
            coordinates.lng,
            1
        )
        val city: String =
            addresses[0].locality
        return city
    }
}