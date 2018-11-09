package com.stazis.subwaystationsmvvm.model.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.stazis.subwaystationsmvvm.model.entities.Station

class StationRepositoryImpl : StationRepository {

    companion object {

        private const val DATA_IN_FIRESTORE_KEY = "DATA_IN_FIRESTORE_KEY"
        private const val STATION_BASIC_INFO_COLLECTION_NAME = "StationBasicInfo"
        private const val STATION_DETAILED_INFO_COLLECTION_NAME = "StationDetailedInfo"
    }

    private val firestore = FirebaseFirestore.getInstance()

    override fun getStations(): List<Station> = listOf(Station("Trololo", 53.9384151, 27.6663906))

    override fun getStationDescription(name: String): String {
        return ""
    }

    override fun updateStationDescription(name: String, description: String): String {
        return ""
    }
}