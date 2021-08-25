package com.scc.shake.utils

import com.scc.shake.utils.NetworkUtil
import android.net.NetworkInfo
import android.net.ConnectivityManager
import android.app.Activity
import android.content.Context

object NetworkUtil {
    fun isInternetConnect(context: Context): Boolean {
        return isConnectedMobileData(context) || isConnectedWifi(context) || isConnectedRoaming(
            context
        )
    }

    fun isConnectedMobileData(context: Context): Boolean {
        val info = getNetworkInfo(context)
        return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_MOBILE
    }

    fun isConnectedRoaming(context: Context): Boolean {
        val info = getNetworkInfo(context)
        return info != null && info.isConnected && info.isRoaming && info.type == ConnectivityManager.TYPE_MOBILE
    }

    fun isConnectedWifi(context: Context): Boolean {
        val info = getNetworkInfo(context)
        return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_WIFI
    }

    private fun getNetworkInfo(context: Context): NetworkInfo? {
        return getConnectivityManager(context).activeNetworkInfo
    }

    fun getConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Activity.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}