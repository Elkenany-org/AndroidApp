package com.example.elkenany.views.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentStoreBinding
import com.example.elkenany.viewmodels.StoreViewModel
import com.example.elkenany.viewmodels.ViewModelFactory


class StoreFragment : Fragment() {

    private lateinit var binding: FragmentStoreBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: StoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[StoreViewModel::class.java]
        return binding.root
    }

}