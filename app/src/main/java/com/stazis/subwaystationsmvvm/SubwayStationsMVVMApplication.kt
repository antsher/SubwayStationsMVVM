package com.stazis.subwaystationsmvvm

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

class SubwayStationsMVVMApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        if (android.os.Build.VERSION.SDK_INT < 21) {
            MultiDex.install(this)
        }
    }
}