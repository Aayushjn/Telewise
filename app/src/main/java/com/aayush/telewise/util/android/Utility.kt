package com.aayush.telewise.util.android

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.aayush.telewise.util.common.CACHE_SIZE
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object Utility {
    init {
        System.loadLibrary("keys")
    }

    @JvmStatic external fun getNativeKey(): String
}

fun toast(context: Context, message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

fun toastLong(context: Context, message: String) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()

fun getOkHttpClient(context: Context): OkHttpClient = OkHttpClient.Builder()
    .cache(Cache(context.cacheDir, CACHE_SIZE))
    .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
    .addInterceptor(CacheInterceptor(context))
    .addInterceptor(RequestInterceptor)
    .callTimeout(1L, TimeUnit.MINUTES)
    .connectTimeout(1L, TimeUnit.MINUTES)
    .readTimeout(1L, TimeUnit.MINUTES)
    .writeTimeout(1L, TimeUnit.MINUTES)
    .build()

fun launchBrowser(context: Context, uri: Uri) {
    val intent = Intent(Intent.ACTION_VIEW, uri)
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        toast(context, "Unable to  open the link")
    }
}
