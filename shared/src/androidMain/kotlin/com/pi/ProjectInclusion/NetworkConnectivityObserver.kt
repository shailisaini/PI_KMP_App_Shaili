package com.pi.ProjectInclusion

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission
import com.pi.ProjectInclusion.domain.ConnectivityObserver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class AndroidConnectivityObserver(
    private val context: Context
) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @SuppressLint("MissingPermission")
    override fun observe(): Flow<ConnectivityObserver.Status> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(ConnectivityObserver.Status.Available)
            }

            override fun onLost(network: Network) {
                trySend(ConnectivityObserver.Status.Lost)
            }

            override fun onUnavailable() {
                trySend(ConnectivityObserver.Status.Unavailable)
            }
        }

        try {
            val request = NetworkRequest.Builder().build()
            connectivityManager.registerNetworkCallback(request, callback)
        } catch (exp: Exception) {
            trySend(ConnectivityObserver.Status.Available)
        }

        awaitClose {
            try {
                connectivityManager.unregisterNetworkCallback(callback)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }.distinctUntilChanged()

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun getCurrentStatus(): ConnectivityObserver.Status {
        val networkInfo = connectivityManager.activeNetworkInfo
        return if (networkInfo != null && networkInfo.isConnected) {
            ConnectivityObserver.Status.Available
        } else {
            ConnectivityObserver.Status.Unavailable
        }
    }
}