package com.stazis.subwaystationsmvvm.extensions

import com.stazis.subwaystationsmvvm.model.entities.Station

fun Station.hasCorrectCoordinates() = (latitude != 0.0 && longitude != 0.0)