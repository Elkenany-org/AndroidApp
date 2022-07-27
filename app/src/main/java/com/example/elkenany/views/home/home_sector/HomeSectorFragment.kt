package com.example.elkenany.views.home.home_sector

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
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentHomeSectorBinding
import com.example.elkenany.viewmodels.HomeSectorViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.home.home_sector.adapter.*


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
            view!!.findNavController()
                .navigate(HomeSectorFragmentDirections.actionHomeSectorFragmentToSectorsChoicesFragment(
                    it.id!!,
                    it.name,
                    it.type))
        })
        recommendationAdapter = SectorRecommendationAdapter(ClickListener {
            Toast.makeText(context, it.name.toString(), Toast.LENGTH_LONG).show()
        })
        partnerAdapter = SectorsPartnerAdapter(ClickListener { })
        stockAdapter = SectorsStockAdapter(ClickListener { })
        guideAdapter = SectorsGuideAdapter(ClickListener { })
        newsAdapter = SectorsNewAdapter(ClickListener { })
        storeAdapter = SectorsStoreAdapter(ClickListener { })

        //assign each adapter to it's own recyclerView
        binding.sectorsRecyclerView.adapter = sectorsAdapter
        binding.recommendationRecyclerView.adapter = recommendationAdapter
        binding.partnersRecyclerView.adapter = partnerAdapter
        binding.stocksRecyclerView.adapter = stockAdapter
        binding.guidesRecyclerView.adapter = guideAdapter
        binding.newsRecyclerView.adapter = newsAdapter
        binding.storesRecyclerView.adapter = storeAdapter

        //navigate to homeServices fragment
        binding.serviceBtn.setOnClickListener {
            view!!.findNavController()
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

}