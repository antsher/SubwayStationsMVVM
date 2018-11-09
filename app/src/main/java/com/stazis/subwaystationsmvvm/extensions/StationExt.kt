package com.stazis.subwaystationsmvvm.extensions

import com.stazis.subwaystationsmvvm.model.entities.Station

inline fun Station.ifCorrectCoordinates(f: () -> Unit) {
    if (latitude != 0.0 && longitude != 0.0) {
        f()
    }
}