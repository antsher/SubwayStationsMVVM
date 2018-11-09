package com.stazis.subwaystationsmvvm.helpers

import android.content.Context
import android.net.ConnectivityManager

class ConnectionHelper(private val context: Context) {

    fun isOnline() = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        .activeNetworkInfo
        .let {
            it != null && it.isConnected
        }
}