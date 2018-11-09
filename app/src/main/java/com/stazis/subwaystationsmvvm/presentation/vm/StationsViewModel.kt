package com.stazis.subwaystationsmvvm.presentation.vm

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stazis.subwaystationsmvvm.helpers.LocationHelper
import com.stazis.subwaystationsmvvm.model.entities.Station
import com.stazis.subwaystationsmvvm.model.repositories.StationRepository
import kotlinx.coroutines.*

class StationsViewModel(private val repository: StationRepository, private val locationHelper: LocationHelper) :
    ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val stationsAndLocation = MutableLiveData<Pair<List<Station>, Location>>()
    val error = MutableLiveData<String>()

    init {
        uiScope.launch {
            delay(1000)
            updateStations()
        }
    }

    private suspend fun updateStations() {
        stationsAndLocation.value = repository.getStations() to locationHelper.getLocation()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}