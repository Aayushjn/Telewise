package com.aayush.telewise.util.android

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.PagingSource
import com.aayush.telewise.api.model.TmdbFailure
import com.haroldadmin.cnradapter.NetworkResponse

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

/**
 * Convert a [NetworkResponse] retrieved from API calls to [PagingSource.LoadResult] for paging
 * This method has been extracted since it will be reused across movie, TV and people APIs that provide paged data
 */
inline fun <reified T : Any, reified R : Any> NetworkResponse<T, TmdbFailure>.toLoadResult(
    currentPage: Int,
    mapper: (T) -> List<R>
): PagingSource.LoadResult<Int, R> =
    when (this) {
        is NetworkResponse.Success -> PagingSource.LoadResult.Page(
            mapper(body),
            if (currentPage == 1) null else currentPage - 1,
            currentPage + 1
        )
        is NetworkResponse.ServerError -> PagingSource.LoadResult.Error(body ?: Throwable("Server Error"))
        is NetworkResponse.NetworkError -> PagingSource.LoadResult.Error(error)
        is NetworkResponse.UnknownError -> PagingSource.LoadResult.Error(error)
    }
