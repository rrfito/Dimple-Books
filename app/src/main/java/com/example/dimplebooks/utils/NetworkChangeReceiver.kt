package com.example.dimplebooks.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkChangeReceiver(private val onNetworkChange: (Boolean) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val connectvityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val active : NetworkInfo? = connectvityManager.activeNetworkInfo
        val isConnected = active?.isConnectedOrConnecting == true
        onNetworkChange(isConnected)

    }
}