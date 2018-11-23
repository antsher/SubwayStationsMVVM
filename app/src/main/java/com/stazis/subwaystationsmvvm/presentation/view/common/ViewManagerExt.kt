package com.stazis.subwaystationsmvvm.presentation.view.common

import android.view.ViewManager
import com.google.android.gms.maps.MapView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.presentation.view.general.pager.StationViewPager
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.textViewWithFont(typeface: String? = null, init: TextViewWithFont.() -> Unit) =
    ankoView({ TextViewWithFont(it).withTypeface(typeface) }, R.style.AppTheme, init)

inline fun ViewManager.editTextWithFont(typeface: String? = null, init: EditTextWithFont.() -> Unit) =
    ankoView({ EditTextWithFont(it).withTypeface(typeface) }, R.style.AppTheme, init)

inline fun ViewManager.inputView(label: String, init: InputView.() -> Unit) =
    ankoView({ InputView(it, label) }, R.style.AppTheme, init)

fun ViewManager.mapView() = mapView { }
inline fun ViewManager.mapView(init: MapView.() -> Unit) = ankoView({ MapView(it) }, R.style.AppTheme, init)

inline fun ViewManager.floatingActionButton(init: FloatingActionButton.() -> Unit) =
    ankoView({ FloatingActionButton(it) }, R.style.AppTheme, init)

inline fun ViewManager.editableTextView(noinline onTextUpdated: (String) -> Unit, init: EditableTextView.() -> Unit) =
    ankoView({ EditableTextView(it, onTextUpdated) }, R.style.AppTheme, init)

inline fun ViewManager.stationViewPager(init: StationViewPager.() -> Unit) =
    ankoView({ StationViewPager(it) }, R.style.AppTheme, init)

inline fun ViewManager.freezingTextViewWithFont(typeface: String? = null, init: TextViewWithFont.() -> Unit) =
    textViewWithFont(typeface) {
        init()
        freezesText = true
    }