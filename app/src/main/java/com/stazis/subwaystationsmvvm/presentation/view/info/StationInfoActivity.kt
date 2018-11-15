package com.stazis.subwaystationsmvvm.presentation.view.info

import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.firebase.analytics.FirebaseAnalytics
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.model.entities.Station
import com.stazis.subwaystationsmvvm.presentation.view.common.BaseActivity
import com.stazis.subwaystationsmvvm.presentation.vm.StationInfoViewModel
import kotlinx.android.synthetic.main.activity_station_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StationInfoActivity : BaseActivity() {

    companion object {

        const val NAME_KEY = "NAME_KEY"
    }

    override val vm: StationInfoViewModel by viewModel(parameters = { parametersOf(intent.getStringExtra(NAME_KEY)) })
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_station_info)
        super.onCreate(savedInstanceState)
        bindViewModel()
        description.onTextUpdated = this::onDescriptionUpdated
    }

    override fun bindViewModel() {
        super.bindViewModel()
        vm.detailedStationAndLocation.observe(this, Observer(this::updateUI))
    }

    private fun updateUI(detailedStationAndLocation: Pair<DetailedStation, Int>) {
        showStationInfo(detailedStationAndLocation.first)
        showDistance(detailedStationAndLocation.second)
        description.enable()
    }

    private fun showStationInfo(detailedStation: DetailedStation) {
        showBasicStationInfo(detailedStation.station)
        description.savedText = detailedStation.detailedInfo.description
    }

    private fun showBasicStationInfo(station: Station) {
        name.text = station.name
        latitude.text = String.format("%s %f", latitude.text, station.latitude)
        longitude.text = String.format("%s %f", longitude.text, station.longitude)
    }

    private fun showDistance(distanceToStation: Int) {
        distance.text = String.format(
            "%s %d %s",
            resources.getString(R.string.distance_to_station_is),
            distanceToStation,
            resources.getString(R.string.meters)
        )
    }

    private fun onDescriptionUpdated() {
        firebaseAnalytics.logEvent("updated_station_description", null)
        vm.onDescriptionUpdated(description.savedText)
    }
}