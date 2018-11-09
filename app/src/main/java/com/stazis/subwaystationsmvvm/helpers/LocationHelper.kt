package com.stazis.subwaystationsmvvm.helpers

import android.content.Context
import android.location.Location

class LocationHelper(private val context: Context) {

    fun getLocation(): Location = Location("")
//            Task<Location> =
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED
//        ) {
//            LocationServices.getFusedLocationProviderClient(context).lastLocation
//        } else {
//            throw SecurityException("Missing location permissions!")
//        }
}