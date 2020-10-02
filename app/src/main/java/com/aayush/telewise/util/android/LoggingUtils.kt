package com.aayush.telewise.util.android

import timber.log.Timber

class DebugTree : Timber.DebugTree() {
    /**
     * The tags produced will provide information on calling functions and line numbers
     * Might be inaccurate when using libraries whose sources aren't available or during asynchronous calls
     */
    override fun createStackElementTag(element: StackTraceElement): String? =
        "(${element.fileName}:${element.lineNumber})${element.methodName}"
}

class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {}
}

/**
 * Helper function to log to [android.util.Log.DEBUG]
 */
inline fun <reified T : Any?> T.log(prefix: String = "") = Timber.d("$prefix$this")
