package com.stazis.subwaystationsmvvm.presentation.vm

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stazis.subwaystationsmvvm.helpers.LocationHelper
import com.stazis.subwaystationsmvvm.model.entities.Station
import com.stazis.subwaystationsmvvm.model.repositories.StationRepository

class StationsViewModel(repository: StationRepository, locationHelper: LocationHelper) : ViewModel() {

    val stationsAndLocation = MutableLiveData<Pair<List<Station>, Location>>()

    init {
        stationsAndLocation.value = repository.getStations() to locationHelper.getLocation()
    }
}