package com.elkenany.views.guide_magazine

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
import com.elkenany.databinding.FragmentGuideMagazineBinding
import com.elkenany.entities.guide.Sector
import com.elkenany.utilities.GlobalUiFunctions
import com.elkenany.utilities.GlobalUiFunctions.Companion.enableImageSlider
import com.elkenany.viewmodels.GuideMagazineViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.guide_magazine.adapter.GuideMagazineAdapter
import com.elkenany.views.home.home_service.HomeServiceFragmentDirections
import com.elkenany.views.home.home_service.adapter.GeneralLogosAdapter
import com.elkenany.views.local_stock.adapter.LocalStockSectorsAdapter


class GuideMagazineFragment : Fragment() {
    private lateinit var binding: FragmentGuideMagazineBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: GuideMagazineViewModel
    private lateinit var sectorsAdapter: LocalStockSectorsAdapter
    private lateinit var logosAdapter: GeneralLogosAdapter
    private lateinit var magazineAdapter: GuideMagazineAdapter
    private var sectorType: String? = null
    private var sort: Long? = 2
    private var cityId: Long? = null
    private var search: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_guide_magazine, container, false)

        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[GuideMagazineViewModel::class.java]
        viewModel.getGuideData(sectorType, sort, cityId, search)
        binding.bannersImageSlider.apply {
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        binding.searchBar.addTextChangedListener {
            search = it.toString()
            viewModel.getGuideData(sectorType, sort, cityId, search)
        }
        binding.searchBtn.setOnClickListener {
            viewModel.openCloseSearchBar()
        }
        logosAdapter = GeneralLogosAdapter(ClickListener {
            when (it.type) {
                "internal" -> requireView().findNavController()
                    .navigate(GuideMagazineFragmentDirections.actionGuideMagazineFragmentToCompanyFragment(
                        it.companyId!!.toLong(),
                        it.companyName!!))
                else -> GlobalUiFunctions.navigateToBroswerIntent(it.link, requireActivity())
            }
        })
        binding.logosRecyclerView.apply {
            adapter = logosAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        sectorsAdapter = LocalStockSectorsAdapter(ClickListener {
            sectorType = it.type.toString()
            viewModel.getGuideData(sectorType, sort, cityId, search)
        })
        binding.sectorsRecyclerView.apply {
            adapter = sectorsAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }
        magazineAdapter = GuideMagazineAdapter(ClickListener {
            requireView().findNavController()
                .navigate(
                    GuideMagazineFragmentDirections.actionGuideMagazineFragmentToGuideMagazineDetailsFragment(
                        it.id!!
                    )
                )
        })
        binding.magazineListRecyclerView.adapter = magazineAdapter

        viewModel.openCloseSearch.observe(viewLifecycleOwner) {
            if (it) {
                binding.searchBarCard.layoutAnimation =
                    AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation)
                binding.searchBarCard.visibility = View.VISIBLE
            } else {
                binding.searchBarCard.visibility = View.GONE
            }
        }
        viewModel.magazineData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    magazineListRecyclerView.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                    logosAdapter.submitList(it.logos)
                    sectorsAdapter.submitList(it.sectors)
                    magazineAdapter.submitList(it.data)
                    magazineListRecyclerView.smoothScrollToPosition(0)
                    enableImageSlider(it.banners, binding.bannersImageSlider, requireActivity())
                    binding.filtersBtn.setOnClickListener { _ ->
                        val sectosList =
                            it.sectors.map { sector ->
                                Sector(
                                    sector!!.id,
                                    sector.name,
                                    sector.type,
                                    sector.selected
                                )
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
                                defaultSector,
                                sectosList,
                                null,
                                null,
                                null,
                                ClickListener { filterData ->
                                    viewModel.getGuideData(
                                        filterData.section,
                                        filterData.sort?.toLong(),
                                        filterData.city?.toLong(),
                                        search
                                    )
                                })
                        }
                    }
                }
            } else {
                binding.apply {
                    magazineListRecyclerView.visibility = View.GONE
                    errorMessage.visibility = View.VISIBLE
                }
            }

        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    loadingProgressbar.visibility = View.VISIBLE
                    magazineListRecyclerView.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                }
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        return binding.root
    }

}