package com.aayush.telewise.ui.fragment.people

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.aayush.telewise.R
import com.aayush.telewise.databinding.FragmentPeopleBinding
import com.aayush.telewise.util.android.toast
import com.aayush.telewise.util.android.viewBinding
import com.aayush.telewise.util.common.ROOT_IDS

class PeopleFragment : Fragment(R.layout.fragment_people) {
    private val binding by viewBinding(FragmentPeopleBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appBarConfiguration = AppBarConfiguration(ROOT_IDS)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.layoutToolbar.toolbar)
        binding.layoutToolbar.toolbar.setupWithNavController(findNavController(), appBarConfiguration)

        toast(requireContext(), "People")
    }
}
