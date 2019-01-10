package com.stazis.subwaystationsmvvm.di

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.stazis.subwaystationsmvvm.helpers.ConnectionHelper
import com.stazis.subwaystationsmvvm.helpers.LocationHelper
import com.stazis.subwaystationsmvvm.helpers.PreferencesHelper
import com.stazis.subwaystationsmvvm.model.repositories.StationRepository
import com.stazis.subwaystationsmvvm.model.repositories.StationRepositoryImpl
import com.stazis.subwaystationsmvvm.model.services.StationService
import com.stazis.subwaystationsmvvm.presentation.vm.StationInfoViewModel
import com.stazis.subwaystationsmvvm.presentation.vm.StationsViewModel
import com.stazis.subwaystationsmvvm.util.exceptions.ConnectionException
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.experimental.builder.create
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val helpersModule = module {
    single { create<LocationHelper>() }
    single { create<ConnectionHelper>() }
    single { create<PreferencesHelper>() }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                if (get<ConnectionHelper>().isOnline()) {
                    chain.proceed(chain.request())
                } else {
                    throw ConnectionException("The internet connection appears to be offline")
                }
            }
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/BeeWhy/metro/")
            .client(get())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }
}

val repositoryModule = module {
    single<StationRepository> {
        StationRepositoryImpl(get<Retrofit>().create(StationService::class.java), get(), get())
    }
}

val vmModule = module {
    viewModel { create<StationsViewModel>() }
    viewModel { (name: String) -> StationInfoViewModel(name, get(), get()) }
}