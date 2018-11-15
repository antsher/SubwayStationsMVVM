package com.stazis.subwaystationsmvvm.presentation.view.general.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.model.entities.Station
import kotlinx.android.synthetic.main.fragment_station_info.*
import kotlin.math.roundToInt

class StationInfoFragment : Fragment() {

    companion object {

        private const val STATION_KEY = "STATION_KEY"
        private const val LOCATION_KEY = "LOCATION_KEY"

        fun newInstance(station: Station, location: LatLng) = StationInfoFragment().apply {
            arguments = Bundle().apply {
                putParcelable(STATION_KEY, station)
                putParcelable(LOCATION_KEY, location)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_station_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize(arguments!!.get(STATION_KEY) as Station, arguments!!.get(LOCATION_KEY) as LatLng)
    }

    private fun initialize(station: Station, location: LatLng) {
        latitude.text = String.format("%s %f", latitude.text, station.latitude)
        longitude.text = String.format("%s %f", longitude.text, station.longitude)
        distance.text = String.format(
            "%s %d %s",
            resources.getString(R.string.distance_to_station_is),
            SphericalUtil.computeDistanceBetween(LatLng(station.latitude, station.longitude), location).roundToInt(),
            resources.getString(R.string.meters)
        )
    }
}