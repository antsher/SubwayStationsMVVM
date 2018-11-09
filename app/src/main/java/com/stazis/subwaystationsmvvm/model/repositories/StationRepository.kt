package com.stazis.subwaystationsmvvm.model.repositories

import com.stazis.subwaystationsmvvm.model.entities.Station

interface StationRepository {

    fun getStations(): List<Station>
    fun getStationDescription(name: String): String
    fun updateStationDescription(name: String, description: String): String
}