package com.example.elkenany.views.home.home_service

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentHomeServiceBinding
import com.example.elkenany.viewmodels.HomeServiceViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.home.home_service.adapter.ServiceGuideAndMagazineAdapter
import com.example.elkenany.views.home.home_service.adapter.ServicePartnerAdapter
import com.example.elkenany.views.home.home_service.adapter.ServiceRecommendationAdapter
import com.example.elkenany.views.home.home_service.adapter.ServiceShowsAdapter


class HomeServiceFragment : Fragment() {
    private lateinit var binding: FragmentHomeServiceBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: HomeServiceViewModel
    private lateinit var partnerAdapter: ServicePartnerAdapter
    private lateinit var recommendationAdapter: ServiceRecommendationAdapter
    private lateinit var showsAdapter: ServiceShowsAdapter
    private lateinit var guideAndMagazineAdapter: ServiceGuideAndMagazineAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_service, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeServiceViewModel::class.java]

        //Adapters initialization
        partnerAdapter = ServicePartnerAdapter(ClickListener { })
        showsAdapter = ServiceShowsAdapter(ClickListener { })
        recommendationAdapter = ServiceRecommendationAdapter(ClickListener { })
        guideAndMagazineAdapter = ServiceGuideAndMagazineAdapter(ClickListener { })

        //assign each adapter to its own recyclerView
        binding.recommendationRecyclerView.adapter = recommendationAdapter
        binding.partnersRecyclerView.adapter = partnerAdapter
        binding.showsRecyclerView.adapter = showsAdapter
        binding.guidesRecyclerView.adapter = guideAndMagazineAdapter

        //navigate to sectorsFragment
        binding.sectorsBtn.setOnClickListener {
            view!!.findNavController()
                .navigate(R.id.action_homeServiceFragment2_to_homeSectorFragment2)
        }

        // Loading progressbar
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.line2.visibility = View.INVISIBLE
            } else {
                binding.loadingProgressbar.visibility = View.INVISIBLE
                binding.line2.visibility = View.VISIBLE
            }
        }

        //submitting all the recyclerAdapter lists to it value
        viewModel.homeServiceData.observe(viewLifecycleOwner) {
            if (it != null) {
//              serviceAdapter.submitList(it.services)
                partnerAdapter.submitList(it.serviceLogos)
                showsAdapter.submitList(it.serviceShows)
                guideAndMagazineAdapter.submitList(it.serviceMagazine)
                recommendationAdapter.submitList(it.serviceRecommendation)
            } else {
                Log.i("list", "Have no data to load")
            }
        }

        return binding.root
    }

}