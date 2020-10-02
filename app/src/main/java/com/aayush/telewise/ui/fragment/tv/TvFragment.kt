package com.aayush.telewise.ui.fragment.tv

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.aayush.telewise.R
import com.aayush.telewise.databinding.FragmentTvBinding
import com.aayush.telewise.util.android.toast
import com.aayush.telewise.util.android.viewBinding

class TvFragment : Fragment(R.layout.fragment_tv) {
    private val binding by viewBinding(FragmentTvBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_movies, R.id.nav_tv, R.id.nav_people))
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.layoutToolbar.toolbar)
        binding.layoutToolbar.toolbar.setupWithNavController(findNavController(), appBarConfiguration)

        toast(requireContext(), "TV")
    }
}
