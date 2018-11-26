package com.stazis.subwaystationsmvvm.presentation.view.common.extensions

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView

fun MapView.getAsyncAndExecute(callback: GoogleMap.() -> Unit) = getMapAsync { map -> callback(map) }