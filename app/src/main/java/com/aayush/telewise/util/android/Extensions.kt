package com.aayush.telewise.util.android

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

/**
 * [androidx.navigation.findNavController] works only when using the <fragment> tag in XML layouts
 * Since a [androidx.fragment.app.FragmentContainerView] is being used, it needs to be casted to a [NavHostFragment] first
 */
fun AppCompatActivity.findNavController(@IdRes viewId: Int): NavController =
    (supportFragmentManager.findFragmentById(viewId) as NavHostFragment).navController
