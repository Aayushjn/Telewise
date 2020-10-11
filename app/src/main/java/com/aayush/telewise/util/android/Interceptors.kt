package com.aayush.telewise.util.android

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Provide cache control when using [okhttp3.OkHttpClient] with [retrofit2.Retrofit]
 *
 * If the network is not available, it uses the cached data, otherwise fetches the latest data and stores in cache
 */
class CacheInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = if (context.getSystemService<ConnectivityManager>()!!.isNetworkAvailable()) {
            request.newBuilder()
                .header(
                    "Cache-Control",
                    "public, max-age=60"
                )
                .build()
        } else {
            request.newBuilder()
                .header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=86400"
                )
                .build()
        }
        return chain.proceed(request)
    }
}

/**
 * The TMDb API requires the API key to be passed in each API call.
 * Instead of passing it as a parameter, the requests are intercepted and the api_key query parameter is inserted
 */
object RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter("api_key", Utility.getNativeKey())
            .build()
        request = request.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(request)
    }
}
