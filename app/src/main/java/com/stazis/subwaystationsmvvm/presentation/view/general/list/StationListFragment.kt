package com.stazis.subwaystationsmvvm.presentation.view.general.list

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
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

    companion object {

        private const val STATES_KEY = "STATES_KEY"
    }

    override val vm by viewModel<StationsViewModel>()
    private var states = HashMap<String, Boolean>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        (inflater.inflate(R.layout.fragment_station_list, container, false) as ViewGroup).apply { root = this }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        if (savedInstanceState !== null && savedInstanceState.containsKey(STATES_KEY)) {
            states = savedInstanceState.getSerializable(STATES_KEY) as HashMap<String, Boolean>
        }
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

    private fun initStationViews(stationsAndLocation: Pair<List<Station>, Location>) = stationsAndLocation.first.map {
        val distanceBetweenUserAndStationLocations = SphericalUtil.computeDistanceBetween(
            LatLng(it.latitude, it.longitude),
            stationsAndLocation.second.toLatLng()
        ).roundToInt()
        it to distanceBetweenUserAndStationLocations
    }.sortedBy { it.second }.map {
        AnimatedStationWidget(context, states[it.first.name] ?: false, it.first, it.second) {
            navigateToStationInfo(it.first.name)
        }
    }

    private fun navigateToStationInfo(name: String) =
        startActivity(Intent(context, StationInfoActivity::class.java).putExtra(StationInfoActivity.NAME_KEY, name))

    private fun addStationViewsToContainer(stationViewsWithDistances: List<AnimatedStationWidget>) =
        stationViewsWithDistances.forEach { stationsContainer.addView(it) }

    override fun onSaveInstanceState(outState: Bundle) {
        stationsContainer.forEach { (it as AnimatedStationWidget).run { states[this.stationName] = expanded } }
        outState.putSerializable(STATES_KEY, states)
        super.onSaveInstanceState(outState)
    }
}