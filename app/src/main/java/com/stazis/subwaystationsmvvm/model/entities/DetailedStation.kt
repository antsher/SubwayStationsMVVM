package com.stazis.subwaystationsmvvm.model.entities

data class DetailedStation(val name: String, val latitude: Double, val longitude: Double, val description: String) {

    constructor() : this("", 0.0, 0.0, "")
}