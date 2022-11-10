package com.elkenany.views.home.home_service

//import android.util.Log
import android.content.Intent
import android.net.Uri
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
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentHomeServiceBinding
import com.elkenany.utilities.GlobalUiFunctions.Companion.enableImageSlider
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
//                9L -> {
//                    requireView().findNavController()
//                        .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToJobsFragment())
//                }
                else -> {
                    Toast.makeText(requireContext(), "سيتم توفير الخدمة قريبا!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
        binding.serviceRecyclerView.adapter = homeServiceAdapter
        partnerAdapter = ServicePartnerAdapter(ClickListener {
            navigateToBroswerIntent(it.link)
        })
        showsAdapter = ServiceShowsAdapter(ClickListener {
            requireView().findNavController()
                .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToShowsDetailsFragment(
                    it.id!!,
                    it.name!!))
        })
        recommendationAdapter = ServiceRecommendationAdapter(ClickListener {
            when (it.type) {
                "show" -> requireView().findNavController()
                    .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToShowsDetailsFragment(
                        it.id!!,
                        it.name!!))

                "magazines" -> requireView().findNavController()
                    .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToGuideMagazineDetailsFragment(
                        it.id!!))
            }
        })
        guideAndMagazineAdapter = ServiceGuideAndMagazineAdapter(ClickListener {
            requireView().findNavController()
                .navigate(HomeServiceFragmentDirections.actionHomeServiceFragmentToGuideMagazineDetailsFragment(
                    it.id!!))
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

        //submitting all the recyclerAdapter lists to it value
        viewModel.homeServiceData.observe(viewLifecycleOwner) {
            if (it != null) {
                homeServiceAdapter.submitList(it.services)
                partnerAdapter.submitList(it.serviceLogos)
                showsAdapter.submitList(it.serviceShows)
                guideAndMagazineAdapter.submitList(it.serviceMagazine)
                recommendationAdapter.submitList(it.serviceRecommendation)
                if (!it.banners.isNullOrEmpty()) {
                    enableImageSlider(it.banners,
                        binding.bannersImageSlider,
                        requireActivity())
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

    private fun navigateToBroswerIntent(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}