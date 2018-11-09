package com.stazis.subwaystationsmvvm.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stazis.subwaystationsmvvm.model.entities.Station
import com.stazis.subwaystationsmvvm.model.repositories.StationRepository

class StationsViewModel(val repository: StationRepository) : ViewModel() {

    val stations = MutableLiveData<List<Station>>()

    init {
        stations.value = repository.getStations()
    }
}