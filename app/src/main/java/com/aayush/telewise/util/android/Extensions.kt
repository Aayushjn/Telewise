package com.aayush.telewise.util.android

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

/**
 * [androidx.navigation.findNavController] works only when using the <fragment> tag in XML layouts
 * Since a [androidx.fragment.app.FragmentContainerView] is being used, it needs to be casted to a [NavHostFragment]
 * first
 */
fun AppCompatActivity.findNavController(@IdRes viewId: Int): NavController =
    (supportFragmentManager.findFragmentById(viewId) as NavHostFragment).navController

/**
 * Check whether device is connected to the internet
 */
fun ConnectivityManager.isNetworkAvailable(): Boolean {
    val network = this.activeNetwork
    return getNetworkCapabilities(network)?.run {
        when {
            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } ?: false
}
