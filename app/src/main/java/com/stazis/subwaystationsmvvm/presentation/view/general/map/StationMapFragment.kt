package com.stazis.subwaystationsmvvm.presentation.view.general.map

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.SphericalUtil
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.extensions.toLatLng
import com.stazis.subwaystationsmvvm.model.entities.Station
import com.stazis.subwaystationsmvvm.presentation.view.common.BaseFragment
import com.stazis.subwaystationsmvvm.presentation.view.general.pager.StationPagerFragment.Companion.LOCATION_KEY
import com.stazis.subwaystationsmvvm.presentation.view.general.pager.StationPagerFragment.Companion.STATIONS_KEY
import com.stazis.subwaystationsmvvm.presentation.view.info.StationInfoActivity
import com.stazis.subwaystationsmvvm.presentation.vm.StationsViewModel
import kotlinx.android.synthetic.main.fragment_station_map.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.math.roundToInt

class StationMapFragment : BaseFragment() {

    override val vm by sharedViewModel<StationsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        (inflater.inflate(R.layout.fragment_station_map, container, false) as ViewGroup).apply { root = this }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        map.onCreate(savedInstanceState)
        bindViewModel()
        if (savedInstanceState == null) {
            map.getMapAsync { googleMap ->
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(53.9086154, 27.5735358), 10.5f))
            }
        }
    }

    override fun bindViewModel() {
        super.bindViewModel()
        vm.stationsAndLocation.observe(this, Observer(this::updateUI))
    }

    private fun updateUI(stationsAndLocation: Pair<List<Station>, Location>) {
        addMarkersToMapAndSetListeners(initMarkers(stationsAndLocation))
        navigateToPager.setOnClickListener {
            bundleOf(
                STATIONS_KEY to stationsAndLocation.first,
                LOCATION_KEY to stationsAndLocation.second.toLatLng()
            ).run {
                findNavController().navigate(R.id.station_pager_dest, this)
            }
        }
    }

    private fun initMarkers(stationsAndLocation: Pair<List<Station>, Location>) =
        stationsAndLocation.first.map {
            val stationLocation = LatLng(it.latitude, it.longitude)
            val userLocation = stationsAndLocation.second.toLatLng()
            val distanceToStation = SphericalUtil.computeDistanceBetween(stationLocation, userLocation).roundToInt()
            MarkerOptions().position(stationLocation)
                .title(it.name)
                .snippet("$distanceToStation${resources.getString(R.string.meter_short)}")
        }

    private fun addMarkersToMapAndSetListeners(markers: List<MarkerOptions>) =
        map.getMapAsync { googleMap ->
            googleMap.setOnInfoWindowClickListener { marker -> navigateToStationInfo(marker.title) }
            markers.forEach { googleMap.addMarker(it) }
        }

    private fun navigateToStationInfo(name: String) =
        startActivity(Intent(context, StationInfoActivity::class.java).putExtra(StationInfoActivity.NAME_KEY, name))

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        map?.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }
}
