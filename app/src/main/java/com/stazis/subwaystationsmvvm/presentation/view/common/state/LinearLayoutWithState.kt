package com.stazis.subwaystationsmvvm.presentation.view.common.state

import android.content.Context
import android.os.Parcelable
import android.util.SparseArray
import androidx.core.view.children
import org.jetbrains.anko._LinearLayout

class LinearLayoutWithState(context: Context) : _LinearLayout(context) {

    override fun onSaveInstanceState(): Parcelable? = LayoutState(super.onSaveInstanceState()!!).also {
        it.childrenStates = SparseArray()
        children.forEach { child -> child.saveHierarchyState(it.childrenStates) }
        it.visibility = visibility
    }

    override fun onRestoreInstanceState(state: Parcelable) = (state as LayoutState).let {
        super.onRestoreInstanceState(it.superState)
        children.forEach { child -> child.restoreHierarchyState(it.childrenStates) }
        visibility = it.visibility
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) = dispatchFreezeSelfOnly(container)

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) = dispatchThawSelfOnly(container)
}