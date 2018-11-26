package com.stazis.subwaystationsmvvm.presentation.view.general.pager

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import com.stazis.subwaystationsmvvm.model.entities.Station
import com.stazis.subwaystationsmvvm.presentation.view.common.extensions.stationViewPager
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.support.v4.UI

class StationPagerFragment : Fragment() {

    companion object {

        const val STATIONS_KEY = "STATIONS_KEY"
        const val LOCATION_KEY = "LOCATION_KEY"
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = UI {
        stationViewPager {
            backgroundColor = Color.WHITE
            isClickable = true
            isFocusable = true
        }.apply {
            with(arguments!!) {
                initialize(childFragmentManager, get(STATIONS_KEY) as List<Station>, get(LOCATION_KEY) as LatLng)
            }
        }
    }.view
}