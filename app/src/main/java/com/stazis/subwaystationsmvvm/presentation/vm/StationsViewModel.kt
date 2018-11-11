package com.stazis.subwaystationsmvvm.presentation.vm

import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.stazis.subwaystationsmvvm.helpers.LocationHelper
import com.stazis.subwaystationsmvvm.model.entities.Station
import com.stazis.subwaystationsmvvm.model.repositories.StationRepository
import kotlinx.coroutines.*

class StationsViewModel(private val repository: StationRepository, private val locationHelper: LocationHelper) :
    BaseViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val stationsAndLocation = MutableLiveData<Pair<List<Station>, Location>>()

    init {
        isLoading.value = true
        uiScope.launch {
            delay(1000)
            updateStations()
        }
    }

    private suspend fun updateStations() {
        try {
            stationsAndLocation.value = repository.getStations() to locationHelper.getLocation()
        } catch (error: Throwable) {
            message.value = error.message ?: "Unknown error!!!"
        }
        isLoading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}