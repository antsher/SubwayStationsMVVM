package com.stazis.subwaystationsmvvm

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.stazis.subwaystationsmvvm.helpers.LocationHelper
import com.stazis.subwaystationsmvvm.model.repositories.StationRepository
import com.stazis.subwaystationsmvvm.model.repositories.StationRepositoryImpl
import com.stazis.subwaystationsmvvm.presentation.vm.StationsViewModel
import org.koin.android.ext.android.startKoin
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class SubwayStationsMVVMApplication : Application() {

    private val appModule = module {
        single { LocationHelper(get()) }
        single<StationRepository> { StationRepositoryImpl() }
        viewModel { StationsViewModel(get(), get()) }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        startKoin(this, listOf(appModule))
        if (android.os.Build.VERSION.SDK_INT < 21) {
            MultiDex.install(this)
        }
    }
}