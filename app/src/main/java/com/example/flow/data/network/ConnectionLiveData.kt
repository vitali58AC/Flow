package com.example.flow.data.network

import android.content.Context
import android.net.Network
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import timber.log.Timber

class ConnectionLiveData(context: Context) : LiveData<Boolean>() {

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val connectionManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetwork: MutableSet<Network> = HashSet()

    private fun checkValidNetworks() {
        postValue(validNetwork.size > 0)
    }

    override fun onActive() {
        networkCallback = createNetworkCallBack()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectionManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onInactive() {
        connectionManager.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkCallBack() = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            Timber.e("call on available network $network")
            val networkCapabilities = connectionManager.getNetworkCapabilities(network)
            val isInternet =
                networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            Timber.e("on available is internet $isInternet")
            if (isInternet == true) {
                validNetwork.add(network)
            }
            checkValidNetworks()
        }

        override fun onLost(network: Network) {
            Timber.e("onLost call $network")
            validNetwork.remove(network)
            checkValidNetworks()
        }
    }
}
