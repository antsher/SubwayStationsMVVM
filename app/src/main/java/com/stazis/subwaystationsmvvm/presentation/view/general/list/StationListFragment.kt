package com.stazis.subwaystationsmvvm.presentation.view.general.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.presentation.view.common.BaseFragment
import com.stazis.subwaystationsmvvm.presentation.vm.StationsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StationListFragment : BaseFragment() {

    override val vm by viewModel<StationsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        (inflater.inflate(R.layout.fragment_station_list, container, false) as ViewGroup).apply { root = this }
}