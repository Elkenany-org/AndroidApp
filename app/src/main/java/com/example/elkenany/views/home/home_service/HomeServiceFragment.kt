package com.example.elkenany.views.home.home_service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentHomeSectorBinding
import com.example.elkenany.viewmodels.HomeSectorViewModel
import com.example.elkenany.viewmodels.ViewModelFactory


class HomeServiceFragment : Fragment() {
    private lateinit var binding: FragmentHomeSectorBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: HomeSectorViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_sector, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeSectorViewModel::class.java]

        return binding.root
    }

}