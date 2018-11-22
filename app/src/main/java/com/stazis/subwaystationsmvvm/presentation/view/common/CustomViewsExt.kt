package com.stazis.subwaystationsmvvm.presentation.view.common

import android.view.ViewManager
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.presentation.view.common.state.LinearLayoutWithState
import com.stazis.subwaystationsmvvm.presentation.view.common.state.RelativeLayoutWithState
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.textViewWithFont(typeface: String? = null, init: TextViewWithFont.() -> Unit) =
    ankoView({ TextViewWithFont(it).withTypeface(typeface) }, R.style.AppTheme, init)

inline fun ViewManager.editTextWithFont(typeface: String? = null, init: EditTextWithFont.() -> Unit) =
    ankoView({ EditTextWithFont(it).withTypeface(typeface) }, R.style.AppTheme, init)

inline fun ViewManager.linearLayoutWithState(init: LinearLayoutWithState.() -> Unit) =
    ankoView({ LinearLayoutWithState(it) }, R.style.AppTheme, init)

inline fun ViewManager.relativeLayoutWithState(init: RelativeLayoutWithState.() -> Unit) =
    ankoView({ RelativeLayoutWithState(it) }, R.style.AppTheme, init)