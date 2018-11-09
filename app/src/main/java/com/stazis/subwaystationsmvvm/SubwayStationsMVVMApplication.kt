package com.stazis.subwaystationsmvvm

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.stazis.subwaystationsmvvm.helpers.ConnectionHelper
import com.stazis.subwaystationsmvvm.helpers.LocationHelper
import com.stazis.subwaystationsmvvm.helpers.PreferencesHelper
import com.stazis.subwaystationsmvvm.model.repositories.StationRepository
import com.stazis.subwaystationsmvvm.model.repositories.StationRepositoryImpl
import com.stazis.subwaystationsmvvm.model.services.StationService
import com.stazis.subwaystationsmvvm.presentation.vm.StationsViewModel
import org.koin.android.ext.android.startKoin
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SubwayStationsMVVMApplication : Application() {

    private val appModule = module {
        single {
            Retrofit.Builder()
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .baseUrl("https://my-json-server.typicode.com/BeeWhy/metro/")
                .build()
        }

        single { LocationHelper(get()) }
        single { ConnectionHelper(get()) }
        single { PreferencesHelper(get()) }

        single<StationRepository> {
            StationRepositoryImpl(
                get<Retrofit>().create(StationService::class.java),
                get(),
                get()
            )
        }

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