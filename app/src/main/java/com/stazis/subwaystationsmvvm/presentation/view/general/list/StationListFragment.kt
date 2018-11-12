package com.stazis.subwaystationsmvvm.presentation.view.general.list

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.extensions.toLatLng
import com.stazis.subwaystationsmvvm.model.entities.Station
import com.stazis.subwaystationsmvvm.presentation.view.common.BaseFragment
import com.stazis.subwaystationsmvvm.presentation.view.general.GeneralActivity
import com.stazis.subwaystationsmvvm.presentation.view.info.StationInfoActivity
import com.stazis.subwaystationsmvvm.presentation.vm.StationsViewModel
import kotlinx.android.synthetic.main.fragment_station_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class StationListFragment : BaseFragment() {

    override val vm by viewModel<StationsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        (inflater.inflate(R.layout.fragment_station_list, container, false) as ViewGroup).apply { root = this }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    override fun bindViewModel() {
        super.bindViewModel()
        vm.stationsAndLocation.observe(this, Observer(this::updateUI))
    }

    private fun updateUI(stationsAndLocation: Pair<List<Station>, Location>) {
        addStationViewsToContainer(initStationViews(stationsAndLocation))
        navigateToPager.setOnClickListener {
            (activity as GeneralActivity).navigateToPager(
                stationsAndLocation.first,
                stationsAndLocation.second.toLatLng()
            )
        }
    }

    private fun initStationViews(stationsAndLocation: Pair<List<Station>, Location>) =
        stationsAndLocation.first.map {
            val distanceBetweenUserAndStationLocations = SphericalUtil.computeDistanceBetween(
                LatLng(it.latitude, it.longitude),
                stationsAndLocation.second.toLatLng()
            ).roundToInt()
            it to distanceBetweenUserAndStationLocations
        }.sortedBy { it.second }
            .map {
                AnimatedStationWidget(context, it.first, it.second) { navigateToStationInfo(it.first.name) }.apply {
                    id = 1_000_000 + it.second
                }
            }

    private fun navigateToStationInfo(name: String) =
        startActivity(Intent(context, StationInfoActivity::class.java).putExtra(StationInfoActivity.NAME_KEY, name))

    private fun addStationViewsToContainer(stationViewsWithDistances: List<AnimatedStationWidget>) =
        stationViewsWithDistances.forEach { stationsContainer.addView(it) }
}