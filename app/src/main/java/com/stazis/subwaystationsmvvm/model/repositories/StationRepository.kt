package com.stazis.subwaystationsmvvm.model.repositories

import com.stazis.subwaystationsmvvm.model.entities.Station
import com.stazis.subwaystationsmvvm.model.entities.StationDetailedInfo

interface StationRepository {

    suspend fun getStations(): List<Station>
    suspend fun getStationBasicInfo(name: String): Station
    suspend fun getStationDetailedInfo(name: String): StationDetailedInfo
    suspend fun updateStationDescription(name: String, description: String): String
}