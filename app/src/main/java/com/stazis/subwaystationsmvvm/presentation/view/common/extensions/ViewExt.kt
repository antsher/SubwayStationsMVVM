package com.stazis.subwaystationsmvvm.presentation.view.common.extensions

import android.view.View

fun View.setPaddingPartly(
    start: Int = paddingStart,
    top: Int = paddingTop,
    end: Int = paddingEnd,
    bottom: Int = paddingBottom
) = setPadding(start, top, end, bottom)