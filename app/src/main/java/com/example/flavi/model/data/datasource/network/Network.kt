package com.example.flavi.model.data.datasource.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Network @Inject constructor (
    @ApplicationContext context: Context
) {
    val connectivityManager = context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager

    inline fun lostNetwork(crossinline actionWhenChangeTheNetwork: () -> Unit) {
        connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {
            override fun onLost(network: Network) { actionWhenChangeTheNetwork() }
        })
    }

    inline fun onAvailableNetwork(crossinline actionWhenChangeTheNetwork: () -> Unit) {
        connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {
            override fun onAvailable(network: Network) { actionWhenChangeTheNetwork() }
        })
    }

    fun stateNetwork(): Boolean {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        Log.d("Auth", "Сеть: ${activeNetworkInfo?.isConnectedOrConnecting.toString()}")
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}