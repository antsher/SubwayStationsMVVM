package com.stazis.subwaystationsmvvm.presentation.view.general.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.model.entities.Station

class StationPagerFragment : Fragment() {

    companion object {

        const val STATIONS_KEY = "STATIONS_KEY"
        const val LOCATION_KEY = "LOCATION_KEY"
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        (inflater.inflate(R.layout.fragment_station_pager, container, false) as StationViewPager).apply {
            with(arguments!!) {
                initialize(childFragmentManager, get(STATIONS_KEY) as List<Station>, get(LOCATION_KEY) as LatLng)
            }
        }
}