package com.stazis.subwaystationsmvvm.presentation.view.common.state

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.ClassLoaderCreator
import android.util.SparseArray
import android.view.View

class LayoutState : View.BaseSavedState {

    internal var visibility: Int = 0
    internal var childrenStates: SparseArray<Parcelable>? = null

    constructor(superState: Parcelable) : super(superState)

    @Suppress("UNCHECKED_CAST")
    private constructor(`in`: Parcel, classLoader: ClassLoader?) : super(`in`) {
        childrenStates = `in`.readSparseArray(classLoader) as SparseArray<Parcelable>?
        visibility = `in`.readInt()
    }

    @Suppress("UNCHECKED_CAST")
    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeSparseArray(childrenStates as SparseArray<Any>?)
        out.writeInt(visibility)
    }

    @Suppress("UNUSED")
    companion object {

        @JvmField
        val CREATOR = object : ClassLoaderCreator<LayoutState> {
            override fun createFromParcel(source: Parcel, loader: ClassLoader?): LayoutState {
                return LayoutState(source, loader)
            }

            override fun createFromParcel(source: Parcel): LayoutState {
                return createFromParcel(source, null)
            }

            override fun newArray(size: Int): Array<LayoutState?> {
                return arrayOfNulls(size)
            }
        }
    }
}