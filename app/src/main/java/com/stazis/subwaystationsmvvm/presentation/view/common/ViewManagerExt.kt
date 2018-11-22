package com.stazis.subwaystationsmvvm.presentation.view.common

import android.view.ViewManager
import com.google.android.gms.maps.MapView
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

inline fun ViewManager.inputView(label: String, init: InputView.() -> Unit) =
    ankoView({ InputView(it, label) }, R.style.AppTheme, init)

inline fun ViewManager.mapView(init: MapView.() -> Unit) = ankoView({ MapView(it) }, R.style.AppTheme, init)

inline fun ViewManager.floatingActionButton(init: FloatingActionButton.() -> Unit) =
    ankoView({ FloatingActionButton(it) }, R.style.AppTheme, init)
