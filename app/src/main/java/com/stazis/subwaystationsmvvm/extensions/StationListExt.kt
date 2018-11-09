package com.stazis.subwaystationsmvvm.extensions

import com.stazis.subwaystationsmvvm.model.entities.Station

fun List<Station>.correctStations() = ArrayList<Station>().also {
    forEach { station ->
        station.ifCorrectCoordinates {
            it.add(station)
        }
    }
}