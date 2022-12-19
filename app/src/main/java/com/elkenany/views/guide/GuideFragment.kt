package com.elkenany.views.guide

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
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentGuideBinding
import com.elkenany.entities.guide.Sector
import com.elkenany.utilities.GlobalLogicFunctions
import com.elkenany.utilities.GlobalUiFunctions
import com.elkenany.utilities.GlobalUiFunctions.Companion.enableImageSlider
import com.elkenany.utilities.GlobalUiFunctions.Companion.navigateToBroswerIntent
import com.elkenany.utilities.SharedPrefrencesType
import com.elkenany.viewmodels.GuideViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.guide.adapter.GuideSubSectionAdapter
import com.elkenany.views.home.home_service.adapter.GeneralLogosAdapter
import com.elkenany.views.local_stock.adapter.LocalStockSectorsAdapter


class GuideFragment : Fragment() {
    private lateinit var binding: FragmentGuideBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: GuideViewModel
    private lateinit var logosAdapter: GeneralLogosAdapter
    private lateinit var sectorsAdapter: LocalStockSectorsAdapter
    private lateinit var subSection: GuideSubSectionAdapter
    private var sectorType: Int? = null
    private var search: String? = null
    override fun onResume() {
        super.onResume()
//        sectorType =
//            try {
//                GlobalLogicFunctions.retrieveSavedSharedPrefrences(
//                    requireActivity(),
//                    SharedPrefrencesType.guide)?.toInt()
//            } catch (e: Exception) {
//                null
//            }
        viewModel.getGuideData(sectorType, search)
    }

    override fun onPause() {
        super.onPause()
//        GlobalLogicFunctions.saveSharedPrefrences(requireActivity(),
//            SharedPrefrencesType.guide,
//            sectorType.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_guide, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[GuideViewModel::class.java]

        binding.searchBtn.setOnClickListener {
            viewModel.openCloseSearchBar()
        }
        binding.searchBar.addTextChangedListener {
            search = it.toString()
            viewModel.getGuideData(sectorType, search)
        }
        binding.bannersImageSlider.apply {
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        logosAdapter = GeneralLogosAdapter(ClickListener {
            when (it.type) {
                "internal" -> requireView().findNavController()
                    .navigate(GuideFragmentDirections.actionGuideFragmentToCompanyFragment(
                        it.companyId!!.toLong(),
                        it.companyName!!))
                else -> navigateToBroswerIntent(it.link, requireActivity())
            }
        })
        binding.logosRecyclerView.apply {
            adapter = logosAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        sectorsAdapter = LocalStockSectorsAdapter(ClickListener {
            sectorType = it.id?.toInt()
            viewModel.getGuideData(sectorType, search)
        })
        binding.sectorsRecyclerView.apply {
            adapter = sectorsAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        subSection = GuideSubSectionAdapter(ClickListener {
            requireView().findNavController()
                .navigate(GuideFragmentDirections.actionGuideFragmentToGuideCompaniesFragment(
                    sectorType!!.toLong(),
                    it.id!!,
                    it.name,
                    sectorType.toString()))
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
                it.sectors.map { sector ->
                    if (sector!!.selected == 1L) {
                        sectorType = sector.id!!.toInt()
                    }
                }.toList()
                enableImageSlider(it.banners, binding.bannersImageSlider, requireActivity())
                val sectosList =
                    it.sectors.map { sector ->
                        Sector(sector!!.id,
                            sector.name,
                            sector.type,
                            sector.selected)
                    }.toList()
                var defaultSector: Long? = null
                binding.filtersBtn.setOnClickListener { _ ->
                    it.sectors.map { sector ->
                        if (sector?.selected == 1L) {
                            defaultSector = sector.id
                        }
                    }
                    GlobalUiFunctions.openFilterDialog(requireActivity(),
                        inflater,
                        defaultSector, sectosList, null, null, null,
                        ClickListener { filterData ->
                            sectorType = filterData.section?.toInt()
                            viewModel.getGuideData(sectorType, search)
                        })
                }
                logosAdapter.submitList(it.logos)
                sectorsAdapter.submitList(it.sectors)
                subSection.submitList(it.subSections)

            } else {
                binding.guideListRecyclerView.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
            }
        }
        viewModel.openCloseSearch.observe(viewLifecycleOwner) {
            if (it) {
                binding.searchBarCard.layoutAnimation =
                    AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation)
                binding.searchBarCard.visibility = View.VISIBLE
            } else {
                binding.searchBarCard.visibility = View.GONE
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
}