package com.stazis.subwaystationsmvvm.presentation.view.general.pager

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stazis.subwaystationsmvvm.presentation.view.common.extensions.stationViewPager
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.support.v4.UI

class StationPagerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = UI {
        stationViewPager {
            backgroundColor = Color.WHITE
            isClickable = true
            isFocusable = true
        }.apply {
            with(StationPagerFragmentArgs.fromBundle(arguments!!)) {
                initialize(childFragmentManager, stations.toList(), location)
            }
        }
    }.view
}