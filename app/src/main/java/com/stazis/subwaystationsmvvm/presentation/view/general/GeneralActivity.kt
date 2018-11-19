package com.stazis.subwaystationsmvvm.presentation.view.general

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.helpers.PermissionState
import com.stazis.subwaystationsmvvm.helpers.checkPermissionState
import com.stazis.subwaystationsmvvm.helpers.requestPermission
import com.stazis.subwaystationsmvvm.model.entities.Station
import kotlinx.android.synthetic.main.activity_general.*

@SuppressLint("MissingPermission")
class GeneralActivity : AppCompatActivity() {

    companion object {

        const val PERMISSION_REQUEST_CODE = 9001
        private const val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    }

    private val firebaseAnalytics by lazy { FirebaseAnalytics.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        emptyList<Station>()
        setContentView(R.layout.activity_general)
        bottomNavigation.setupWithNavController(findNavController(R.id.nav_host_fragment))
        requestPermissions()
    }

    private fun requestPermissions() = when (checkPermissionState(this, locationPermission)) {
        PermissionState.GRANTED -> {
        }
        PermissionState.NOT_GRANTED -> requestPermission(this, locationPermission)
        PermissionState.REJECTED -> {
            firebaseAnalytics.logEvent("permissions_are_rejected", null)
            askNicelyForPermissions()
        }
    }

    private fun askNicelyForPermissions() = AlertDialog.Builder(this)
        .setTitle("Permission denied")
        .setMessage("The app cannot run without location permissions. Please, grant them")
        .setNeutralButton("OK") { dialog, _ ->
            dialog.dismiss()
            requestPermission(this, locationPermission)
        }
        .create()
        .show()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
                    askNicelyForPermissions()
                }
            }
            else -> throw IllegalArgumentException("Invalid request code!")
        }
    }
}
