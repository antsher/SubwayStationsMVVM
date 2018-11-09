package com.stazis.subwaystationsmvvm.presentation.vm

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stazis.subwaystationsmvvm.helpers.LocationHelper
import com.stazis.subwaystationsmvvm.model.entities.DetailedStation
import com.stazis.subwaystationsmvvm.model.repositories.StationRepository
import kotlinx.coroutines.*

class StationInfoViewModel(
    private val name: String,
    private val repository: StationRepository,
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val detailedStationAndLocation = MutableLiveData<Pair<DetailedStation, Location>>()
    val error = MutableLiveData<String>()

    init {
        uiScope.launch {
            delay(1000)
            getDetailedStationAndLocation()
        }
    }

    private suspend fun getDetailedStationAndLocation() {
        val basicInfo = repository.getStationBasicInfo(name)
        val detailedInfo = repository.getStationDetailedInfo(name)
        val location = locationHelper.getLocation()
        detailedStationAndLocation.value = DetailedStation(basicInfo, detailedInfo) to location
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}