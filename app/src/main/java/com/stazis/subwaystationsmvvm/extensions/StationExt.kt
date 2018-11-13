package com.stazis.subwaystationsmvvm.extensions

import com.stazis.subwaystationsmvvm.model.entities.Station

fun Station.correctCoordinates() = (latitude != 0.0 && longitude != 0.0)