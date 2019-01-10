package com.stazis.subwaystationsmvvm

import android.app.Application
import android.content.Context
import com.stazis.subwaystationsmvvm.di.helpersModule
import com.stazis.subwaystationsmvvm.di.networkModule
import com.stazis.subwaystationsmvvm.di.repositoryModule
import com.stazis.subwaystationsmvvm.di.vmModule
import org.koin.android.ext.android.startKoin

class SubwayStationsMVVMApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        startKoin(this, listOf(helpersModule, networkModule, repositoryModule, vmModule))
    }
}