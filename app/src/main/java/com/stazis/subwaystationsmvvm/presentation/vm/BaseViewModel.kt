package com.stazis.subwaystationsmvvm.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
}