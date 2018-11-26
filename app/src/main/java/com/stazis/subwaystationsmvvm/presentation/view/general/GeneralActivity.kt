package com.stazis.subwaystationsmvvm.presentation.view.general

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.helpers.PermissionState
import com.stazis.subwaystationsmvvm.helpers.checkPermissionState
import com.stazis.subwaystationsmvvm.helpers.requestPermission
import kotlinx.android.synthetic.main.activity_general.*


@SuppressLint("MissingPermission")
class GeneralActivity : AppCompatActivity() {

    companion object {

        const val PERMISSION_REQUEST_CODE = 9001
        const val SETTINGS_ACTIVITY_CODE = 9002
        private const val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    }

    private val firebaseAnalytics by lazy { FirebaseAnalytics.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)
        bottomNavigation.setupWithNavController(findNavController(R.id.nav_host_fragment))
        requestPermissionsIfNecessary()
    }

    private fun requestPermissionsIfNecessary() = when (checkPermissionState(this, locationPermission)) {
        PermissionState.GRANTED -> {
        }
        PermissionState.NOT_GRANTED -> requestPermission(this, locationPermission)
        PermissionState.REJECTED -> {
            firebaseAnalytics.logEvent("permissions_are_rejected", null)
            askNicelyForPermissions()
        }
    }

    private fun askNicelyForPermissions(onDismiss: (() -> Unit)? = null) = AlertDialog.Builder(this)
        .setTitle(resources.getString(R.string.permissions_denied))
        .setMessage(resources.getString(R.string.cannot_run_without_location_permissions))
        .setNeutralButton("OK") { dialog, _ ->
            dialog.dismiss()
            onDismiss?.invoke() ?: requestPermission(this, locationPermission)
        }
        .create()
        .show()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, locationPermission)) {
                        askNicelyForPermissions()
                    } else {
                        askNicelyForPermissions { navigateToSettings() }
                    }
                }
            }
            else -> throw IllegalArgumentException("Invalid request code!")
        }
    }

    private fun navigateToSettings() =
        startActivityForResult(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }, SETTINGS_ACTIVITY_CODE)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            SETTINGS_ACTIVITY_CODE -> requestPermissionsIfNecessary()
            else -> throw IllegalArgumentException("Invalid request code!")
        }
    }
}
