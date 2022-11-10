package com.elkenany.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.elkenany.R
import com.elkenany.databinding.FragmentHomeBinding
import com.elkenany.viewmodels.HomeViewModel
import com.elkenany.viewmodels.ViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
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


        binding.bottomNavigation.apply {
            selectedItemId = R.id.homeServiceFragment
            setupWithNavController(childFragmentManager.findFragmentById(R.id.container)!!
                .findNavController())
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.searchFragment -> {
                        childFragmentManager.findFragmentById(R.id.container)!!
                            .findNavController().navigate(R.id.searchFragment)
                        true
                    }
                    R.id.notificationFragment -> {
                        childFragmentManager.findFragmentById(R.id.container)!!
                            .findNavController().navigate(R.id.notificationFragment)
                        true
                    }
                    R.id.homeServiceFragment -> {
                        childFragmentManager.findFragmentById(R.id.container)!!
                            .findNavController().navigate(R.id.homeServiceFragment)
                        true
                    }
                    R.id.profileFragment -> {
                        childFragmentManager.findFragmentById(R.id.container)!!
                            .findNavController().navigate(R.id.profileFragment)
                        true
                    }
                    R.id.mainMenuFragment -> {
                        childFragmentManager.findFragmentById(R.id.container)!!
                            .findNavController().navigate(R.id.mainMenuFragment)
                        true
                    }
                    else -> false

                }
            }
            return binding.root
        }

    }
}
