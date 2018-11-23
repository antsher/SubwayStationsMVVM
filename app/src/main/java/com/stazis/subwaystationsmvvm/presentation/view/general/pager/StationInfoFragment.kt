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
import com.stazis.subwaystationsmvvm.presentation.view.common.TextViewWithFont
import com.stazis.subwaystationsmvvm.presentation.view.common.textViewWithFont
import org.jetbrains.anko.dip
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.topPadding
import org.jetbrains.anko.verticalLayout
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

    private lateinit var root: View
    private lateinit var latitude: TextViewWithFont
    private lateinit var longitude: TextViewWithFont
    private lateinit var distance: TextViewWithFont

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = UI {
        verticalLayout {
            setPaddingRelative(dip(10), paddingTop, paddingEnd, paddingBottom)
            topPadding = dip(10)
            setPaddingRelative(paddingStart, paddingTop, dip(10), paddingBottom)
            latitude = textViewWithFont("Montserrat-Regular") {
                text = resources.getString(R.string.latitude)
                textSize = 16f
            }.lparams {
                bottomMargin = dip(10)
            }
            longitude = textViewWithFont("Montserrat-Regular") {
                text = resources.getString(R.string.longitude)
                textSize = 16f
            }.lparams {
                bottomMargin = dip(10)
            }
            distance = textViewWithFont("Montserrat-Medium") {
                textSize = 24f
            }.lparams {
                bottomMargin = dip(10)
            }
        }
    }.view.apply { root = this }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize(arguments!!.get(STATION_KEY) as Station, arguments!!.get(LOCATION_KEY) as LatLng)
    }

    private fun initialize(station: Station, location: LatLng) {
        with(latitude) { text = String.format("%s %f", text, station.latitude) }
        with(longitude) { text = String.format("%s %f", text, station.longitude) }
        distance.text = String.format(
            "%s %d %s",
            resources.getString(R.string.distance_to_station_is),
            SphericalUtil.computeDistanceBetween(LatLng(station.latitude, station.longitude), location).roundToInt(),
            resources.getString(R.string.meters)
        )
    }
}