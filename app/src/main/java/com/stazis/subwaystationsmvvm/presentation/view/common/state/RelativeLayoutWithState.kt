package com.stazis.subwaystationsmvvm.presentation.view.common.state

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.widget.RelativeLayout
import androidx.core.view.children

class RelativeLayoutWithState @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

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