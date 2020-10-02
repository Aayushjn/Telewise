package com.aayush.telewise.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.aayush.telewise.R
import com.aayush.telewise.databinding.ActivityMainBinding
import com.aayush.telewise.util.android.findNavController
import com.aayush.telewise.util.android.viewBinding

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController(R.id.nav_host_fragment) }

    private val appBarConfiguration by lazy(LazyThreadSafetyMode.NONE) {
        AppBarConfiguration(setOf(R.id.nav_movies, R.id.nav_tv, R.id.nav_people))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}
