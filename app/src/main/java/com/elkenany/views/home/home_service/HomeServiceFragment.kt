package com.elkenany.views.home.home_service

//import android.util.Log
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentHomeServiceBinding
import com.elkenany.entities.home_data.HomeServiceDaum
import com.elkenany.viewmodels.HomeServiceViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.home.home_service.adapter.*


class HomeServiceFragment : Fragment() {
    private lateinit var binding: FragmentHomeServiceBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: HomeServiceViewModel
    private lateinit var homeServiceAdapter: HomeServiceAdapter
    private lateinit var partnerAdapter: ServicePartnerAdapter
    private lateinit var recommendationAdapter: ServiceRecommendationAdapter
    private lateinit var showsAdapter: ServiceShowsAdapter
    private lateinit var guideAndMagazineAdapter: ServiceGuideAndMagazineAdapter
    private val serviceList = listOf(HomeServiceDaum(1, "المعارض", "shows", ""),
        HomeServiceDaum(2, "الدلائل والمجلات", "magazine", ""),
        HomeServiceDaum(3, "حركة السفن", "ships", ""))

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
        homeServiceAdapter = HomeServiceAdapter(ClickListener {
            when (it.type) {
                "shows" -> {
                    //ToDo -> Navigate to showsFragment
                    requireView().findNavController()
                        .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToShowsFragment())
                }
                "magazine" -> {
                    //ToDo -> Navigate to magazineFragment
                    requireView().findNavController()
                        .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToGuideMagazineFragment())
                }
                "ships" -> {
                    //ToDo -> Navigate to shipsFragment
                }
                else -> {
                    throw  IllegalArgumentException("Uknow direction to navigate to")
                }
            }
        })
        binding.serviceRecyclerView.adapter = homeServiceAdapter
        partnerAdapter = ServicePartnerAdapter(ClickListener { })
        showsAdapter = ServiceShowsAdapter(ClickListener { })
        recommendationAdapter = ServiceRecommendationAdapter(ClickListener { })
        guideAndMagazineAdapter = ServiceGuideAndMagazineAdapter(ClickListener { })

        //assign each adapter to its own recyclerView
        binding.recommendationRecyclerView.adapter = recommendationAdapter
        binding.partnersRecyclerView.adapter = partnerAdapter
        binding.showsRecyclerView.adapter = showsAdapter
        binding.guidesRecyclerView.adapter = guideAndMagazineAdapter

//        //navigate to sectorsFragment
        binding.sectorsBtn.setOnClickListener {
            requireView().findNavController()
                .navigate(HomeServiceFragmentDirections.actionHomeServiceFragment2ToHomeSectorFragment2())
        }
//
        // Loading progressbar
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.line2.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
            } else {
                binding.loadingProgressbar.visibility = View.INVISIBLE

            }
        }

        //submitting all the recyclerAdapter lists to it value
        viewModel.homeServiceData.observe(viewLifecycleOwner) {
            if (it != null) {
                homeServiceAdapter.submitList(serviceList)
                partnerAdapter.submitList(it.serviceLogos)
                showsAdapter.submitList(it.serviceShows)
                guideAndMagazineAdapter.submitList(it.serviceMagazine)
                recommendationAdapter.submitList(it.serviceRecommendation)
                binding.errorMessage.visibility = View.GONE
                binding.line2.visibility = View.VISIBLE
            } else {
                binding.errorMessage.visibility = View.VISIBLE
                binding.line2.visibility = View.GONE
                binding.line2.visibility = View.GONE
                Log.i("list", "Have no data to load")
            }
        }

        return binding.root
    }

}