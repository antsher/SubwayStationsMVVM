package com.stazis.subwaystationsmvvm.presentation.view.general

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stazis.subwaystationsmvvm.R
import kotlinx.android.synthetic.main.activity_general.*

class GeneralActivity : AppCompatActivity() {

    companion object {

        const val PERMISSION_REQUEST_CODE = 9001
        private const val ACTIVE_TAB_KEY = "ACTIVE_TAB_KEY"
        private const val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    }

    enum class TabName { Map, List }

    private lateinit var activeTab: TabName
    private val navigationController: NavigationController = NavigationController(this, R.id.fragmentContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)
        navigateToMap()
    }

    private fun navigateToMap() {
        activeTab = TabName.Map
        listTab.makeInactive()
        mapTab.makeActive()
        navigationController.navigateToStationMap()
    }
}
