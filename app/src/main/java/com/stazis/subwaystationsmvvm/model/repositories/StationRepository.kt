package com.stazis.subwaystationsmvvm.model.repositories

import com.stazis.subwaystationsmvvm.model.entities.Station

interface StationRepository {

    suspend fun getStations(): List<Station>
    suspend fun getStationDescription(name: String): String
    suspend fun updateStationDescription(name: String, description: String): String
}