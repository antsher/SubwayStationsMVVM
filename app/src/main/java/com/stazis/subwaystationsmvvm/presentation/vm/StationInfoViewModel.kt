package com.stazis.subwaystationsmvvm.presentation.vm

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.stazis.subwaystationsmvvm.extensions.toLatLng
import com.stazis.subwaystationsmvvm.helpers.LocationHelper
import com.stazis.subwaystationsmvvm.model.repositories.StationRepository
import com.stazis.subwaystationsmvvm.presentation.view.info.DetailedStation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class StationInfoViewModel(
    private val name: String,
    private val repository: StationRepository,
    private val locationHelper: LocationHelper
) : BaseViewModel() {

    val detailedStationAndLocation = MutableLiveData<Pair<DetailedStation, Int>>()

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
            detailedStationAndLocation.value = DetailedStation(
                basicInfo,
                detailedInfo
            ) to distance
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
        } catch (error: Throwable) {
            message.value = error.message ?: "Unknown error!!!"
        }
        isLoading.value = false
    }
}