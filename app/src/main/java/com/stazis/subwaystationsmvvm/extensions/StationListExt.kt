package com.stazis.subwaystationsmvvm.extensions

import com.stazis.subwaystationsmvvm.model.entities.Station

fun List<Station>.getCorrectStations() = mapNotNull {
    if (it.hasCorrectCoordinates()) it else null
}