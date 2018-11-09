package com.stazis.subwaystationsmvvm.model.entities

import android.os.Parcel
import android.os.Parcelable

data class Station(val name: String, val latitude: Double, val longitude: Double) : Parcelable {

    companion object CREATOR : Parcelable.Creator<Station> {

        override fun createFromParcel(parcel: Parcel) = Station(parcel)

        override fun newArray(size: Int) = arrayOfNulls<Station?>(size)
    }

    constructor() : this("", 0.0, 0.0)

    constructor(parcel: Parcel) : this(parcel.readString()!!, parcel.readDouble(), parcel.readDouble())

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        writeString(name)
        writeDouble(latitude)
        writeDouble(longitude)
    }

    override fun describeContents() = 0
}