package com.stazis.subwaystationsmvvm.presentation.view.info

import android.location.Location
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.maps.android.SphericalUtil
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.extensions.toLatLng
import com.stazis.subwaystationsmvvm.model.entities.DetailedStation
import com.stazis.subwaystationsmvvm.presentation.view.common.BaseActivity
import com.stazis.subwaystationsmvvm.presentation.vm.StationInfoViewModel
import kotlinx.android.synthetic.main.activity_station_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.roundToInt

class StationInfoActivity : BaseActivity() {

    companion object {

        const val NAME_KEY = "NAME_KEY"
    }

    private val stationInfoViewModel: StationInfoViewModel by viewModel(parameters = {
        parametersOf(intent.getStringExtra(NAME_KEY))
    })
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_station_info)
        super.onCreate(savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        stationInfoViewModel.detailedStationAndLocation.observe(this, Observer(this::updateUI))
    }

    private fun updateUI(detailedStationAndLocation: Pair<DetailedStation, Location>) {
        detailedStationAndLocation.first.let {
            name.text = it.name
            latitude.text = String.format("Latitude: %f", it.latitude)
            longitude.text = String.format("Longitude: %f", it.longitude)
            val stationLocation = LatLng(it.latitude, it.longitude)
            val userLocation = detailedStationAndLocation.second.toLatLng()
            distance.text = String.format(
                "Distance to station from your current location is %d meters",
                SphericalUtil.computeDistanceBetween(stationLocation, userLocation).roundToInt()
            )
            descriptionLabel.text = resources.getText(R.string.description)
        }
    }
}