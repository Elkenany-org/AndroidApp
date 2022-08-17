package com.example.elkenany.views.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentAdDetailsBinding
import com.example.elkenany.viewmodels.AdDetailsViewModel
import com.example.elkenany.viewmodels.ViewModelFactory


class AdDetailsFragment : Fragment() {

    private lateinit var binding: FragmentAdDetailsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AdDetailsViewModel
    private val args: AdDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ad_details, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[AdDetailsViewModel::class.java]
        viewModel.getAdDetailsData(args.id)
        //I dont know how or why but this makes all the data recieved to be presented
        //Otherwise it wont work
        binding.apply {
            phone = args.id.toString()
            location = args.id.toString()
            image = args.id.toString()
            description = args.id.toString()
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.adsLayout.visibility = View.GONE
                binding.loadingProgressbar.visibility = View.VISIBLE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        viewModel.adDetailsData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    adsLayout.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                    phone = it.phone
                    location = it.address
                    image = if (it.images.isEmpty()) {
                        null
                    } else {
                        it.images[0]!!.image
                    }
                    description = it.desc
                }

            } else {
                binding.apply {
                    adsLayout.visibility = View.GONE
                    errorMessage.visibility = View.VISIBLE
                }
            }
        }
        return binding.root
    }

}