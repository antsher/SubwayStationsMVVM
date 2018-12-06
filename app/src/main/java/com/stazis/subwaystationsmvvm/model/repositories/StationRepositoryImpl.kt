package com.stazis.subwaystationsmvvm.model.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.stazis.subwaystationsmvvm.extensions.correctStations
import com.stazis.subwaystationsmvvm.helpers.ConnectionHelper
import com.stazis.subwaystationsmvvm.helpers.PreferencesHelper
import com.stazis.subwaystationsmvvm.model.entities.Station
import com.stazis.subwaystationsmvvm.model.entities.StationDetailedInfo
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

    override suspend fun getStations(): List<Station> =
        if (preferencesHelper.retrieveBoolean(DATA_IN_FIRESTORE_KEY)) {
            getStationsFromFirestore()
        } else {
            getStationsFromServer()
        }

    private suspend fun getStationsFromServer(): List<Station> =
        stationService.getStations().await().correctStations().apply {
            postBasicStationsToFirestore(this)
            createAdvancedStationsCollectionInFirestore(this)
            preferencesHelper.saveBoolean(DATA_IN_FIRESTORE_KEY, true)
        }

    private fun postBasicStationsToFirestore(stations: List<Station>) = stations.forEach {
        firestore.collection(STATION_BASIC_INFO_COLLECTION_NAME).document(it.name).set(it)
    }

    private fun createAdvancedStationsCollectionInFirestore(stations: List<Station>) =
        stations.forEach {
            firestore.collection(STATION_DETAILED_INFO_COLLECTION_NAME).document(it.name)
                .set(mapOf("description" to ""))
        }

    private suspend fun getStationsFromFirestore(): List<Station> = suspendCoroutine {
        firestore.collection(STATION_BASIC_INFO_COLLECTION_NAME).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && !task.result!!.isEmpty) {
                    it.resume(task.result!!.toObjects(Station::class.java))
                } else {
                    it.resumeWithException(task.exception!!)
                }
            }
    }

    override suspend fun getStationBasicInfo(name: String): Station = suspendCoroutine {
        firestore.collection(STATION_BASIC_INFO_COLLECTION_NAME).document(name).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    it.resume(task.result!!.toObject(Station::class.java)!!)
                } else {
                    it.resumeWithException(task.exception!!)
                }
            }
    }

    override suspend fun getStationDetailedInfo(name: String): StationDetailedInfo =
        suspendCoroutine {
            firestore.collection(STATION_DETAILED_INFO_COLLECTION_NAME).document(name).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        it.resume(task.result!!.toObject(StationDetailedInfo::class.java)!!)
                    } else {
                        it.resumeWithException(task.exception!!)
                    }
                }
        }

    override suspend fun updateStationDescription(name: String, description: String): String =
        if (connectionHelper.isOnline()) {
            suspendCoroutine {
                firestore.collection(STATION_DETAILED_INFO_COLLECTION_NAME)
                    .document(name)
                    .update("description", description)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            it.resume("Station updated successfully!")
                        } else {
                            it.resumeWithException(task.exception!!)
                        }
                    }
            }
        } else {
            throw ConnectException("Cannot update station description due to no internet connection is present")
        }
}