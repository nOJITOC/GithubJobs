package com.mmteams91.githubjobs.common.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import io.reactivex.Single
import io.reactivex.processors.BehaviorProcessor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkStatusProvider @Inject constructor(context: Context) {

    private val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val isNetworkAvailableProcessor = BehaviorProcessor.create<Boolean>()


    init {
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), object : ConnectivityManager.NetworkCallback() {
            val availableNetworks = mutableSetOf<String>()
            override fun onLost(network: Network) {
                availableNetworks.remove(network.toString())
                updateNetworkAvailability()
                super.onLost(network)
            }

            private fun updateNetworkAvailability() {
                if (isNetworkAvailableProcessor.value != availableNetworks.isNotEmpty()) {
                    isNetworkAvailableProcessor.onNext(availableNetworks.isNotEmpty())
                }
            }


            override fun onAvailable(network: Network) {
                availableNetworks.add(network.toString())
                updateNetworkAvailability()
                super.onAvailable(network)
            }
        })

    }

    fun isNetworkAvailable(): Boolean = isNetworkAvailableProcessor.value == true
    fun isNetworkAvailableSingle(): Single<Boolean> {
        return isNetworkAvailableProcessor.firstOrError()
    }


}