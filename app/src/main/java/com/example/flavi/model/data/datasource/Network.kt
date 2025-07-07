package com.example.flavi.model.data.datasource

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Network @Inject constructor (
    @ApplicationContext context: Context
) {
    private val connectivityManager = context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager

    fun stateNetwork(): Boolean {
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        Log.d("Auth", networkInfo.toString())
        return networkInfo?.isConnected == true
    }
}