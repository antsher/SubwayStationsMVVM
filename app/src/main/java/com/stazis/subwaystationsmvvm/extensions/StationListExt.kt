package com.stazis.subwaystationsmvvm.extensions

import com.stazis.subwaystationsmvvm.model.entities.Station

fun List<Station>.correctStations() = mapNotNull {
    if (it.hasCorrectCoordinates()) it else null
}