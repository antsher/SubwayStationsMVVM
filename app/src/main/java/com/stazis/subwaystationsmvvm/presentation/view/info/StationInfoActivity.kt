package com.stazis.subwaystationsmvvm.presentation.view.info

import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.firebase.analytics.FirebaseAnalytics
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.presentation.view.common.*
import com.stazis.subwaystationsmvvm.presentation.vm.StationInfoViewModel
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.verticalLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StationInfoActivity : BaseActivity() {

    companion object {

        const val NAME_KEY = "NAME_KEY"
    }

    override val vm: StationInfoViewModel by viewModel(parameters = { parametersOf(intent.getStringExtra(NAME_KEY)) })
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    private lateinit var name: TextViewWithFont
    private lateinit var latitude: TextViewWithFont
    private lateinit var longitude: TextViewWithFont
    private lateinit var distance: TextViewWithFont
    private lateinit var description: EditableTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        initUI()
        super.onCreate(savedInstanceState)
        bindViewModel()
    }

    private fun initUI() = relativeLayout {
        verticalLayout {
            name = freezingTextViewWithFont("Montserrat-SemiBold") {
                textSize = 24f
            }.lparams {
                bottomMargin = dip(10)
            }
            latitude = freezingTextViewWithFont("Montserrat-Regular") {
                text = resources.getString(R.string.latitude)
                textSize = 16f
            }.lparams {
                bottomMargin = dip(10)
            }
            longitude = freezingTextViewWithFont("Montserrat-Regular") {
                text = resources.getString(R.string.longitude)
                textSize = 16f
            }.lparams {
                bottomMargin = dip(10)
            }
            distance = freezingTextViewWithFont("Montserrat-Medium") {
                textSize = 24f
            }.lparams {
                bottomMargin = dip(10)
            }
            freezingTextViewWithFont("Montserrat-SemiBold") {
                text = resources.getString(R.string.description)
                textSize = 20f
            }.lparams {
                bottomMargin = dip(10)
            }
            description = editableTextView(this@StationInfoActivity::onDescriptionUpdated) {
                id = R.id.stationInfoActivityDescription
            }.lparams(matchParent)
        }.lparams(matchParent) {
            leftMargin = dip(10)
            rightMargin = dip(10)
        }
    }

    override fun bindViewModel() {
        super.bindViewModel()
        vm.stationName.observe(this, Observer { name.text = it })
        vm.stationLatitude.observe(this, Observer { latitude.text = String.format("%s %f", latitude.text, it) })
        vm.stationLongitude.observe(this, Observer { longitude.text = String.format("%s %f", longitude.text, it) })
        vm.stationDistance.observe(this, Observer {
            distance.text = String.format(
                "%s %d %s",
                resources.getString(R.string.distance_to_station_is),
                it,
                resources.getString(R.string.meters)
            )
        })
        vm.stationDescription.observe(this, Observer { description.updateSavedText(it) })
    }

    private fun onDescriptionUpdated(newDescription: String) {
        firebaseAnalytics.logEvent("updated_station_description", null)
        vm.onDescriptionUpdated(newDescription)
    }
}