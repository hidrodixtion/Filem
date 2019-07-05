package com.projectbox.filem.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by adinugroho
 */
object NetworkUtil {
    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)

        if (connectivityManager is ConnectivityManager) {
            val netInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            return netInfo?.isConnected ?: false
        }
        return false
    }
}