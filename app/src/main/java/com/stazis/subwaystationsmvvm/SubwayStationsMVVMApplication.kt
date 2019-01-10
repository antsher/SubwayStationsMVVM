package com.stazis.subwaystationsmvvm

import android.app.Application
import android.content.Context
import com.stazis.subwaystationsmvvm.di.getKoinModules
import org.koin.android.ext.android.startKoin

class SubwayStationsMVVMApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        startKoin(this, getKoinModules())
    }
}