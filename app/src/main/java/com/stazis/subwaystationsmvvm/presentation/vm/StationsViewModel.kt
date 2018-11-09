package com.stazis.subwaystationsmvvm.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stazis.subwaystationsmvvm.model.entities.Station

class StationsViewModel : ViewModel() {

    val stations = MutableLiveData<List<Station>>()

    init {
        stations.value = listOf(Station("Trololo", 53.9384151, 27.6663906))
    }
}