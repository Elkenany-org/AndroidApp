package com.example.elkenany.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentHomeBinding
import com.example.elkenany.viewmodels.HomeViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.home.home_sector.HomeSectorFragment
import com.example.elkenany.views.profile.ProfileFragment

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var transaction: FragmentTransaction
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        binding.bottomNavigation.setupWithNavController(childFragmentManager.findFragmentById(R.id.container)!!.findNavController())
        return binding.root
    }

}