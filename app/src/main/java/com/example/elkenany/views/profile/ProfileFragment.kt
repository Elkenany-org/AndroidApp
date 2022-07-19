package com.example.elkenany.views.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentProfileBinding
import com.example.elkenany.viewmodels.ProfileViewModel
import com.example.elkenany.viewmodels.ViewModelFactory


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]

        viewModel.authData.observe(viewLifecycleOwner) {
            if (it !=null) {
                binding.profileLayout.visibility = View.VISIBLE
                binding.signInBtn.visibility = View.GONE
                binding.nameText.text = it.name
                binding.emailText.text = it.email
                binding.url = ""
            } else {
                binding.profileLayout.visibility = View.GONE
                binding.signInBtn.visibility = View.VISIBLE
                
            }
        }

        return binding.root
    }

}