package com.stazis.subwaystationsmvvm.presentation.view.common.state

import android.os.Parcel
import android.os.Parcelable
import android.util.SparseArray
import android.view.View

class LayoutState(superState: Parcelable) : View.BaseSavedState(superState) {

    internal var visibility: Int = 0
    internal var childrenStates: SparseArray<Parcelable>? = null

    @Suppress("UNCHECKED_CAST")
    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeSparseArray(childrenStates as SparseArray<Any>?)
        out.writeInt(visibility)
    }
}