package com.elkenany.views.home.home_service

//import android.util.Log
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentHomeServiceBinding
import com.elkenany.entities.home_data.ServiceRecomandtion
import com.elkenany.utilities.GlobalUiFunctions.Companion.enableImageSlider
import com.elkenany.utilities.GlobalUiFunctions.Companion.navigateToBroswerIntent
import com.elkenany.viewmodels.HomeServiceViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.home.HomeFragmentDirections
import com.elkenany.views.home.home_service.adapter.*


class HomeServiceFragment : Fragment() {
    private lateinit var binding: FragmentHomeServiceBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: HomeServiceViewModel
    private lateinit var homeServiceAdapter: HomeServiceAdapter
    private lateinit var partnerAdapter: GeneralLogosAdapter
    private lateinit var recommendationAdapter: ServiceRecommendationAdapter
    private lateinit var showsAdapter: ServiceShowsAdapter
    private lateinit var guideAndMagazineAdapter: ServiceGuideAndMagazineAdapter
    private var rndm = (0..20).random()
    private var sectionId = 0
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
            when (it.id) {
                1L -> {
                    requireView().findNavController()
                        .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToLocalStockFragment())
                }
                2L -> {
                    requireView().findNavController()
                        .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToStoreFragment())
                }
                3L -> {
                    requireView().findNavController()
                        .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToGuideFragment())
                }
                4L -> {
                    requireView().findNavController()
                        .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToNewsFragment())
                }
                5L -> {
                    requireView().findNavController()
                        .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToShowsFragment())
                }
                6L -> {
                    requireView().findNavController()
                        .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToShipsFragment())
                }
                7L -> {
                    requireView().findNavController()
                        .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToGuideMagazineFragment())
                }
                8L -> {
                    requireView().findNavController()
                        .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToJobsFragment())
                }
                9L -> {
                    requireView().findNavController()
                        .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToTendersFragment())
                }
                else -> {
                    Toast.makeText(requireContext(), "سيتم توفير الخدمة قريبا!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
        binding.serviceRecyclerView.adapter = homeServiceAdapter
        partnerAdapter = GeneralLogosAdapter(ClickListener {
            when (it.type) {
                "internal" -> requireView().findNavController()
                    .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToCompanyFragment(
                        it.companyId!!.toLong(),
                        it.companyName!!))
                else -> navigateToBroswerIntent(it.link, requireActivity())
            }
        })
        showsAdapter = ServiceShowsAdapter(ClickListener {
            requireView().findNavController()
                .navigate(
                    HomeServiceFragmentDirections.actionHomeServiceFragmentToShowsDetailsFragment(
                        it.id!!,
                        it.name!!
                    )
                )
        })
        recommendationAdapter = ServiceRecommendationAdapter(ClickListener {
            onSectorRecommendationNavigation(it)
        })
        guideAndMagazineAdapter = ServiceGuideAndMagazineAdapter(ClickListener {
            requireView().findNavController()
                .navigate(
                    HomeServiceFragmentDirections.actionHomeServiceFragmentToGuideMagazineDetailsFragment(
                        it.id!!
                    )
                )
        })

        //assign each adapter to its own recyclerView
        binding.recommendationRecyclerView.adapter = recommendationAdapter
//        binding.partnersRecyclerView.adapter = partnerAdapter
//        binding.showsRecyclerView.adapter = showsAdapter
//        binding.guidesRecyclerView.adapter = guideAndMagazineAdapter

//        //navigate to sectorsFragment
//        binding.sectorsBtn.setOnClickListener {
//            requireView().findNavController()
//                .navigate(HomeServiceFragmentDirections.actionHomeServiceFragment2ToHomeSectorFragment2())
//        }
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
        viewModel.popUpData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.popup != null && !it.popup.link.isNullOrEmpty()) {
                    Log.i("rndm", rndm.toString())
                    if (rndm == 3) {
                        requireParentFragment().requireParentFragment().findNavController()
                            .navigate(HomeFragmentDirections.actionHomeFragmentToPopUpAdFragment(
                                it.popup.link
                            ))
                        rndm = 0
                    }
                }
            }
        }
        //submitting all the recyclerAdapter lists to it value
        viewModel.homeServiceData.observe(viewLifecycleOwner) {
            if (it != null) {
                homeServiceAdapter.submitList(it.services)
                partnerAdapter.submitList(it.serviceLogos)
                showsAdapter.submitList(it.serviceShows)
                guideAndMagazineAdapter.submitList(it.serviceMagazine)
                recommendationAdapter.submitList(it.serviceRecommendation)
//                sectionId = it.type!!.toInt()
                if (it.serviceRecommendation.isNullOrEmpty()) {
                    binding.recommendationTextView.visibility = View.GONE
                } else {
                    binding.recommendationTextView.visibility = View.VISIBLE
                }
                if (!it.banners.isNullOrEmpty()) {
                    enableImageSlider(
                        it.banners,
                        binding.bannersImageSlider,
                        requireActivity()
                    )
                }
                binding.errorMessage.visibility = View.GONE
                binding.line2.visibility = View.VISIBLE
            } else {
                binding.errorMessage.visibility = View.VISIBLE
                binding.line2.visibility = View.GONE
                binding.line2.visibility = View.GONE
                Log.i("list", "Have no data to load + $it")
            }
        }
        return binding.root
    }

    private fun onSectorRecommendationNavigation(recommendation: ServiceRecomandtion) {
        when (recommendation.type) {
            "guide" -> requireView().findNavController()
                .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToGuideCompaniesFragment(
                    sectionId.toLong(),
                    recommendation.id!!,
                    recommendation.name,
                    ""))
            "local" -> requireView().findNavController()
                .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToLocalStockDetailsFragment(
                    recommendation.id!!.toLong(),
                    recommendation.name,
                    recommendation.type))
            "fodder" -> requireView().findNavController()
                .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToLocalStockDetailsFragment(
                    recommendation.id!!.toLong(),
                    recommendation.name,
                    recommendation.type))
            "news" -> requireView().findNavController()
                .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToNewsDetailsFragment(
                    recommendation.id!!.toInt()))
            "store" -> requireView().findNavController()
                .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToAdDetailsFragment(
                    recommendation.id!!.toLong()))
            "show" -> requireView().findNavController()
                .navigate(
                    HomeServiceFragmentDirections.actionHomeServiceFragmentToShowsDetailsFragment(
                        recommendation.id!!,
                        recommendation.name!!
                    )
                )
            "magazines" -> requireView().findNavController()
                .navigate(
                    HomeServiceFragmentDirections.actionHomeServiceFragmentToGuideMagazineDetailsFragment(
                        recommendation.id!!
                    )
                )
            else -> Toast.makeText(requireContext(), "لم يتم تفعيل الخدمة بعد", Toast.LENGTH_SHORT)
                .show()
        }
    }
}