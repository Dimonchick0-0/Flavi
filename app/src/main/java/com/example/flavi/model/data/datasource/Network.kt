package com.example.flavi.model.data.datasource

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import leakcanary.AppWatcher
import javax.inject.Inject

class Network @Inject constructor (
    @ApplicationContext context: Context
) {
    private val connectivityManager = context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager

    fun lostNetwork(actionWhenChangeTheNetwork: () -> Unit) {
        connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {
            override fun onLost(network: Network) { actionWhenChangeTheNetwork() }
        })
    }

    fun onAvailableNetwork(actionWhenChangeTheNetwork: () -> Unit) {
        connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {
            override fun onAvailable(network: Network) { actionWhenChangeTheNetwork() }
        })
    }

    fun stateNetwork(): Boolean {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        Log.d("Auth", activeNetworkInfo?.isConnectedOrConnecting.toString())
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}