package com.elkenany.views.home.home_sector

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentHomeSectorBinding
import com.elkenany.entities.home_data.SectorsRecomandtion
import com.elkenany.viewmodels.HomeSectorViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.home.home_sector.adapter.*


class HomeSectorFragment : Fragment() {
    private lateinit var binding: FragmentHomeSectorBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: HomeSectorViewModel

    // Recycler adapters
    private lateinit var sectorsAdapter: SectorsAdapter
    private lateinit var recommendationAdapter: SectorRecommendationAdapter
    private lateinit var partnerAdapter: SectorsPartnerAdapter
    private lateinit var stockAdapter: SectorsStockAdapter
    private lateinit var guideAdapter: SectorsGuideAdapter
    private lateinit var newsAdapter: SectorsNewAdapter
    private lateinit var storeAdapter: SectorsStoreAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_sector, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeSectorViewModel::class.java]

        //adapters initialization
        sectorsAdapter = SectorsAdapter(ClickListener {
            requireView().findNavController()
                .navigate(HomeSectorFragmentDirections.actionHomeSectorFragmentToSectorsChoicesFragment(
                    it.id!!,
                    it.name,
                    it.type))
        })
        recommendationAdapter = SectorRecommendationAdapter(ClickListener {
            onSectorRecommendationNavigation(it)
        })
        partnerAdapter = SectorsPartnerAdapter(ClickListener {
            // ToDo --> navigate to specific page based on the selected item type
            navigateToBroswerIntent(it.link)

        })
        stockAdapter = SectorsStockAdapter(ClickListener {
            // ToDo --> navigate to Stock Details page
            requireView().findNavController()
                .navigate(HomeSectorFragmentDirections.actionHomeSectorFragmentToLocalStockDetailsFragment(
                    it.id!!,
                    it.name,
                    it.type,
                ))
        })
        guideAdapter = SectorsGuideAdapter(ClickListener {
            requireView().findNavController()
                .navigate(HomeSectorFragmentDirections.actionHomeSectorFragmentToGuideCompaniesFragment(
                    it.id!!,
                    it.name,
                    "",
                ))
        })
        newsAdapter = SectorsNewAdapter(ClickListener {
            requireView().findNavController()
                .navigate(HomeSectorFragmentDirections.actionHomeSectorFragmentToNewsDetailsFragment(
                    it.id!!.toInt()))
        })
        storeAdapter = SectorsStoreAdapter(ClickListener {
            requireView().findNavController().navigate(
                HomeSectorFragmentDirections.actionHomeSectorFragmentToAdDetailsFragment(it.id!!)
            )
        })

        //assign each adapter to it's own recyclerView
        binding.sectorsRecyclerView.apply {
            adapter = sectorsAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }
        binding.recommendationRecyclerView.apply {
            adapter = recommendationAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }
        binding.partnersRecyclerView.apply {
            adapter = partnerAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }
        binding.stocksRecyclerView.apply {
            adapter = stockAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }
        binding.guidesRecyclerView.apply {
            adapter = guideAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }
        binding.newsRecyclerView.apply {
            adapter = newsAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }
        binding.storesRecyclerView.apply {
            adapter = storeAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        //navigate to homeServices fragment
        binding.serviceBtn.setOnClickListener {
            requireView().findNavController()
                .navigate(HomeSectorFragmentDirections.actionHomeSectorFragment2ToHomeServiceFragment2())
        }
        // Loading progressbar
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.line2.visibility = View.GONE
            } else {
                binding.loadingProgressbar.visibility = View.INVISIBLE
            }
        }

        //submitting all the recyclerAdapter lists to it value
        viewModel.homeSectorsData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.line2.visibility = View.VISIBLE
                sectorsAdapter.submitList(it.sectors)
                recommendationAdapter.submitList(it.sectorsRecomandtion)
                partnerAdapter.submitList(it.sectorsLogos)
                stockAdapter.submitList(it.sectorsStock)
                guideAdapter.submitList(it.sectorsGuide)
                newsAdapter.submitList(it.sectorsNews)
                storeAdapter.submitList(it.sectorsStore)
                binding.errorMessage.visibility = View.GONE
            } else {
                binding.errorMessage.visibility = View.VISIBLE
                binding.line2.visibility = View.GONE
                Log.i("list", "Have no data to load")
            }
        }

        return binding.root
    }

    private fun onSectorRecommendationNavigation(recommendation: SectorsRecomandtion) {
        when (recommendation.type) {
            "guide" -> requireView().findNavController()
                .navigate(HomeSectorFragmentDirections.actionHomeSectorFragmentToGuideCompaniesFragment(
                    recommendation.id!!.toLong(),
                    recommendation.name,
                    ""))
            "local" -> requireView().findNavController()
                .navigate(HomeSectorFragmentDirections.actionHomeSectorFragmentToLocalStockDetailsFragment(
                    recommendation.id!!.toLong(),
                    recommendation.name,
                    recommendation.type))
            "fodder" -> requireView().findNavController()
                .navigate(HomeSectorFragmentDirections.actionHomeSectorFragmentToLocalStockDetailsFragment(
                    recommendation.id!!.toLong(),
                    recommendation.name,
                    recommendation.type))
            "news" -> requireView().findNavController()
                .navigate(HomeSectorFragmentDirections.actionHomeSectorFragmentToNewsDetailsFragment(
                    recommendation.id!!.toInt()))
            "store" -> requireView().findNavController()
                .navigate(HomeSectorFragmentDirections.actionHomeSectorFragmentToAdDetailsFragment(
                    recommendation.id!!.toLong()))
            else -> Toast.makeText(requireContext(), "لم يتم تفعيل الخدمة بعد", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun navigateToBroswerIntent(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}