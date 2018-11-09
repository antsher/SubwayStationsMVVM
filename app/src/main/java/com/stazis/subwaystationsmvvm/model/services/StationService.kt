package com.stazis.subwaystationsmvvm.model.services

import com.stazis.subwaystationsmvvm.model.entities.Station
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface StationService {

    @GET("stations/")
    fun getStations(): Deferred<List<Station>>
}