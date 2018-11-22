package com.stazis.subwaystationsmvvm.presentation.view.info

import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.google.firebase.analytics.FirebaseAnalytics
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.model.entities.Station
import com.stazis.subwaystationsmvvm.presentation.view.common.*
import com.stazis.subwaystationsmvvm.presentation.vm.StationInfoViewModel
import org.jetbrains.anko.dip
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.relativeLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StationInfoActivity : BaseActivity() {

    companion object {

        const val NAME_KEY = "NAME_KEY"
    }

    override val vm: StationInfoViewModel by viewModel(parameters = { parametersOf(intent.getStringExtra(NAME_KEY)) })
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    private val name by lazy { findViewById<TextViewWithFont>(R.id.stationInfoActivityName) }
    private val latitude by lazy { findViewById<TextViewWithFont>(R.id.stationInfoActivityLatitude) }
    private val longitude by lazy { findViewById<TextViewWithFont>(R.id.stationInfoActivityLongitude) }
    private val distance by lazy { findViewById<TextViewWithFont>(R.id.stationInfoActivityDistance) }
    private val description by lazy { findViewById<EditableTextView>(R.id.stationInfoActivityDescription) }

    override fun onCreate(savedInstanceState: Bundle?) {
        initUI()
        super.onCreate(savedInstanceState)
        bindViewModel()
        description.onTextUpdated = this::onDescriptionUpdated
    }

    private fun initUI() = relativeLayout {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            textViewWithFont("Montserrat-SemiBold") {
                freezesText = true
                id = R.id.stationInfoActivityName
                textSize = 24f
            }.lparams {
                bottomMargin = dip(10)
            }
            textViewWithFont("Montserrat-Regular") {
                text = resources.getString(R.string.latitude)
                freezesText = true
                id = R.id.stationInfoActivityLatitude
                textSize = 16f
            }.lparams {
                bottomMargin = dip(10)
            }
            textViewWithFont("Montserrat-Regular") {
                text = resources.getString(R.string.longitude)
                freezesText = true
                id = R.id.stationInfoActivityLongitude
                textSize = 16f
            }.lparams {
                bottomMargin = dip(10)
            }
            textViewWithFont("Montserrat-Medium") {
                freezesText = true
                id = R.id.stationInfoActivityDistance
                textSize = 24f
            }.lparams {
                bottomMargin = dip(10)
            }
            textViewWithFont("Montserrat-SemiBold") {
                text = resources.getString(R.string.description)
                freezesText = true
                textSize = 20f
            }.lparams {
                bottomMargin = dip(10)
            }
            editableTextView {
                id = R.id.stationInfoActivityDescription
            }.lparams(matchParent)
        }.lparams(matchParent) {
            leftMargin = dip(10)
            rightMargin = dip(10)
            topMargin = dip(10)
        }
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