package com.stazis.subwaystationsmvvm.presentation.vm

import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.stazis.subwaystationsmvvm.helpers.LocationHelper
import com.stazis.subwaystationsmvvm.model.entities.DetailedStation
import com.stazis.subwaystationsmvvm.model.repositories.StationRepository
import kotlinx.coroutines.*

class StationInfoViewModel(
    private val name: String,
    private val repository: StationRepository,
    private val locationHelper: LocationHelper
) : BaseViewModel() {

    val detailedStationAndLocation = MutableLiveData<Pair<DetailedStation, Location>>()

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
        val location = locationHelper.getLocation()
        try {
            detailedStationAndLocation.value = DetailedStation(basicInfo, detailedInfo) to location
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