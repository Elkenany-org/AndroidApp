package com.example.elkenany.views.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentGuideBinding
import com.example.elkenany.entities.stock_data.LocalStockBanner
import com.example.elkenany.viewmodels.GuideViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.guide.adapter.GuideSubSectionAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockBannersAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockLogosAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockSectorsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GuideFragment : Fragment() {
    private lateinit var binding: FragmentGuideBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: GuideViewModel
    private lateinit var bannersAdapter: LocalStockBannersAdapter
    private lateinit var logosAdapter: LocalStockLogosAdapter
    private lateinit var sectorsAdapter: LocalStockSectorsAdapter
    private lateinit var subSection: GuideSubSectionAdapter
    private lateinit var sectorType: String
    private var search: String? = null
    private val args: GuideFragmentArgs by navArgs()
    override fun onResume() {
        super.onResume()
        viewModel.getGuideData(sectorType, search)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_guide, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[GuideViewModel::class.java]
        sectorType = try {
            args.sectorType.toString()
        } catch (e: Exception) {
            "poultry"
        }

        binding.searchBar.addTextChangedListener {
            search = it.toString()
            viewModel.getGuideData(sectorType, search)
        }
        bannersAdapter = LocalStockBannersAdapter(ClickListener { })
        binding.bannersRecyclerView.apply {
            adapter = bannersAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        logosAdapter = LocalStockLogosAdapter(ClickListener { })
        binding.logosRecyclerView.apply {
            adapter = logosAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        sectorsAdapter = LocalStockSectorsAdapter(ClickListener {
            sectorType = it.type.toString()
            viewModel.getGuideData(sectorType, search)
        })
        binding.sectorsRecyclerView.apply {
            adapter = sectorsAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        subSection = GuideSubSectionAdapter(ClickListener {
            requireView().findNavController()
                .navigate(GuideFragmentDirections.actionGuideFragmentToGuideCompaniesFragment(it.id!!,
                    it.name,
                    sectorType))
        })
        binding.guideListRecyclerView.apply {
            adapter = subSection
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }
        viewModel.guideData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.guideListRecyclerView.visibility = View.VISIBLE
                binding.guideListRecyclerView.scrollToPosition(0)
                binding.errorMessage.visibility = View.GONE
                //submitting lists to its own adapters
                bannersAdapter.submitList(it.banners)
                scrollRecyclerView(it.banners)
                logosAdapter.submitList(it.logos)
                sectorsAdapter.submitList(it.sectors)
                subSection.submitList(it.subSections)

            } else {
                binding.guideListRecyclerView.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.guideListRecyclerView.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        return binding.root
    }
    private fun scrollRecyclerView(banners: List<LocalStockBanner?>) {
        CoroutineScope(Dispatchers.Main).launch {
            var counter = 0
            while (counter < banners.size) {
                delay(3000L).apply {
                    binding.bannersRecyclerView.smoothScrollToPosition(counter)
                }
                if (counter == banners.size - 1) {
                    counter = 0
                } else {
                    counter += 1
                }
            }
        }
    }

}