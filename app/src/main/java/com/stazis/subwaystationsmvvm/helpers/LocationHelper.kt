package com.stazis.subwaystationsmvvm.helpers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay

class LocationHelper(private val context: Context) {

    suspend fun getLocation(): Location =
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            delay(1000)
            Location("").apply {
                latitude = 53.8851807
                longitude = 27.5370945
            }
//            LocationServices.getFusedLocationProviderClient(context).lastLocation.asDeferred().await()
        } else {
            throw SecurityException("Missing location permissions!")
        }
}