package com.example.elkenany.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
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
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            viewModel.onItemClicked(item)
        }

        viewModel.homePage.observe(viewLifecycleOwner) {
            if (it) {
                // ToDo --> Fixing layout inflation cause it's crashing
//                loadFragment(HomeSectorFragment())
                binding.bottomNavigation.selectedItemId = R.id.homePage
                viewModel.onDoneNavigating()
            }
        }
        viewModel.searchPage.observe(viewLifecycleOwner) {
            if (it) {
//                loadFragment(SearchFragment())
                binding.bottomNavigation.selectedItemId = R.id.searchPage
                viewModel.onDoneNavigating()
            }
        }
        viewModel.stockPage.observe(viewLifecycleOwner) {
            if (it) {
//                loadFragment(StockPage())
                binding.bottomNavigation.selectedItemId = R.id.stockPage
                viewModel.onDoneNavigating()
            }
        }
        viewModel.profilePage.observe(viewLifecycleOwner) {
            if (it) {
                loadFragment(ProfileFragment())
                binding.bottomNavigation.selectedItemId = R.id.profilePage
                viewModel.onDoneNavigating()
            }
        }
        return binding.root
    }

    // function to switch between fragments
    private fun loadFragment(fragment: Fragment) {
        transaction = this.childFragmentManager.beginTransaction()
        transaction.apply {
            replace(R.id.container, fragment)
            addToBackStack(null)
            commit()
        }
    }
}