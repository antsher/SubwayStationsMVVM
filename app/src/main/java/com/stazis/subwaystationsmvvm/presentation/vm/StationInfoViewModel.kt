package com.stazis.subwaystationsmvvm.presentation.vm

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.stazis.subwaystationsmvvm.extensions.toLatLng
import com.stazis.subwaystationsmvvm.helpers.LocationHelper
import com.stazis.subwaystationsmvvm.model.repositories.StationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class StationInfoViewModel(
    private val name: String,
    private val repository: StationRepository,
    private val locationHelper: LocationHelper
) : BaseViewModel() {

    val stationName = MutableLiveData<String>()
    val stationLatitude = MutableLiveData<Double>()
    val stationLongitude = MutableLiveData<Double>()
    val stationDescription = MutableLiveData<String>()
    val stationDistance = MutableLiveData<Int>()

    init {
        isLoading.value = true
        uiScope.launch {
            delay(1000)
            getDetailedStationAndLocation()
        }
    }

    private suspend fun getDetailedStationAndLocation() {
        val basicInfo = repository.getStationBasicInfo(name)
        val detailedInfo = repository.getStationDetailedInfo(name)
        val distance = SphericalUtil.computeDistanceBetween(
            LatLng(basicInfo.latitude, basicInfo.longitude),
            locationHelper.getLocation().toLatLng()
        ).roundToInt()
        try {
            stationName.value = basicInfo.name
            stationLatitude.value = basicInfo.latitude
            stationLongitude.value = basicInfo.longitude
            stationDistance.value = distance
            stationDescription.value = detailedInfo.description
        } catch (error: Throwable) {
            message.value = error.message ?: "Unknown error!!!"
        }
        isLoading.value = false
    }

    fun onDescriptionUpdated(description: String) {
        isLoading.value = true
        uiScope.launch {
            delay(1000)
            updateStationDescription(description)
        }
    }

    private suspend fun updateStationDescription(description: String) {
        try {
            message.value = repository.updateStationDescription(name, description)
            stationDescription.value = description
        } catch (error: Throwable) {
            message.value = error.message ?: "Unknown error!!!"
        }
        isLoading.value = false
    }
}