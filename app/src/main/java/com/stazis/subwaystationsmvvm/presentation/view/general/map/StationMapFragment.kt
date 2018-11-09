package com.stazis.subwaystationsmvvm.presentation.view.general.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.SphericalUtil
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.model.entities.Station
import com.stazis.subwaystationsmvvm.presentation.view.common.BaseFragment
import com.stazis.subwaystationsmvvm.presentation.vm.StationsViewModel
import kotlinx.android.synthetic.main.fragment_station_map.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class StationMapFragment : BaseFragment() {

    private val stationsViewModel: StationsViewModel by viewModel()

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

    private fun bindViewModel() {
        stationsViewModel.stations.observe(this, Observer(this::updateUI))
    }

    private fun updateUI(stations: List<Station>) {
        addMarkersToMap(initMarkers(stations))
    }

    private fun initMarkers(stations: List<Station>) = stations.map {
        val stationLocation = LatLng(it.latitude, it.longitude)
        val distanceToStation = SphericalUtil.computeDistanceBetween(stationLocation, LatLng(53.0, 27.0)).roundToInt()
        MarkerOptions().position(stationLocation).title(it.name).snippet("${distanceToStation}m")
    }

    private fun addMarkersToMap(markers: List<MarkerOptions>) = map.getMapAsync { googleMap ->
        googleMap.setOnInfoWindowClickListener { marker ->
            //            navigateToStationInfo(stations.find { it.name == marker.title }!!, location)
        }
        markers.forEach { googleMap.addMarker(it) }
    }

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

//class StationMapFragment : BaseMvpFragment<StationsPresenter>(), StationsView {
//
//    @Inject
//    @InjectPresenter
//    override lateinit var presenter: StationsPresenter
//    private lateinit var stations: List<Station>
//    private lateinit var location: LatLng
//
//    @ProvidePresenter
//    fun providePresenter() = presenter
//
//    private fun setupUI() {
//        showClickableMarkersOnMap(initMarkers())
//        navigateToPager.setOnClickListener { (activity as GeneralActivity).navigateToPager(stations, location) }
//    }
//
//    override fun updateUI(stationsAndLocation: Pair<List<Station>, Location>) {
//        stations = ArrayList(stationsAndLocation.first)
//        location = stationsAndLocation.second.toLatLng()
//        setupUI()
//    }
//
//    private fun initMarkers() = stations.map {
//        val stationLocation = LatLng(it.latitude, it.longitude)
//        val distanceToStation = SphericalUtil.computeDistanceBetween(stationLocation, location).roundToInt()
//        MarkerOptions().position(stationLocation).title(it.name).snippet("${distanceToStation}m")
//    }
//
//    private fun showClickableMarkersOnMap(markers: List<MarkerOptions>) = map.getMapAsync { googleMap ->
//        googleMap.setOnInfoWindowClickListener { marker ->
//            navigateToStationInfo(stations.find { it.name == marker.title }!!, location)
//        }
//        markers.forEach { googleMap.addMarker(it) }
//    }
//
//    private fun navigateToStationInfo(station: Station, currentLocation: LatLng) =
//        startActivity(Intent(context, StationInfoActivity::class.java).let {
//            it.putExtra(StationInfoActivity.STATION_KEY, station)
//            it.putExtra(StationInfoActivity.CURRENT_LOCATION_KEY, currentLocation)
//        })
//}
