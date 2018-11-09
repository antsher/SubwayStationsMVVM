package com.stazis.subwaystationsmvvm.model.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.stazis.subwaystationsmvvm.extensions.correctStations
import com.stazis.subwaystationsmvvm.helpers.ConnectionHelper
import com.stazis.subwaystationsmvvm.helpers.PreferencesHelper
import com.stazis.subwaystationsmvvm.model.entities.Station
import com.stazis.subwaystationsmvvm.model.services.StationService
import java.net.ConnectException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class StationRepositoryImpl(
    private val stationService: StationService,
    private val connectionHelper: ConnectionHelper,
    private val preferencesHelper: PreferencesHelper
) : StationRepository {

    companion object {

        private const val DATA_IN_FIRESTORE_KEY = "DATA_IN_FIRESTORE_KEY"
        private const val STATION_BASIC_INFO_COLLECTION_NAME = "StationBasicInfo"
        private const val STATION_DETAILED_INFO_COLLECTION_NAME = "StationDetailedInfo"
    }

    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun getStations(): List<Station> = if (preferencesHelper.retrieveBoolean(DATA_IN_FIRESTORE_KEY)) {
        fetchStationsFromFirestore()
    } else {
        if (connectionHelper.isOnline()) {
            fetchStationsFromServer()
        } else {
            throw ConnectException("No internet connection is present, and local database is empty")
        }
    }

    private suspend fun fetchStationsFromServer(): List<Station> =
        stationService.getStations().await().correctStations().apply {
            writeBasicStationsToFirestore(this)
            createAdvancedStationsCollectionInFirestore(this)
        }

    private fun writeBasicStationsToFirestore(stations: List<Station>) = stations.forEach {
        firestore.collection(STATION_BASIC_INFO_COLLECTION_NAME).document(it.name).set(it)
    }

    private fun createAdvancedStationsCollectionInFirestore(stations: List<Station>) = stations.forEach {
        firestore.collection(STATION_DETAILED_INFO_COLLECTION_NAME).document(it.name).set(mapOf("description" to ""))
    }

    private suspend fun fetchStationsFromFirestore(): List<Station> = suspendCoroutine { continuation ->
        firestore.collection(STATION_BASIC_INFO_COLLECTION_NAME).get().addOnCompleteListener { task ->
            if (task.isSuccessful && !task.result!!.isEmpty) {
                continuation.resume(task.result!!.toObjects(Station::class.java))
            } else {
                continuation.resumeWithException(task.exception!!)
            }
        }
    }

    override suspend fun getStationDescription(name: String): String {
        return ""
    }

    override suspend fun updateStationDescription(name: String, description: String): String {
        return ""
    }
}