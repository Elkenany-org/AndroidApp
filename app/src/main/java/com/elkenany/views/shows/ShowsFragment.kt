package com.elkenany.views.shows

import android.os.Bundle
import android.util.Log
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
import com.elkenany.databinding.FragmentShowsBinding
import com.elkenany.entities.guide.Sector
import com.elkenany.utilities.GlobalLogicFunctions
import com.elkenany.utilities.GlobalUiFunctions
import com.elkenany.utilities.GlobalUiFunctions.Companion.enableImageSlider
import com.elkenany.utilities.SharedPrefrencesType
import com.elkenany.viewmodels.ShowsViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.home.home_service.adapter.GeneralLogosAdapter
import com.elkenany.views.local_stock.adapter.LocalStockSectorsAdapter
import com.elkenany.views.shows.adapter.ShowsAdapter

class ShowsFragment : Fragment() {
    private lateinit var binding: FragmentShowsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ShowsViewModel
    private lateinit var logosAdapter: GeneralLogosAdapter
    private lateinit var sectorsAdapter: LocalStockSectorsAdapter
    private lateinit var showsAdapter: ShowsAdapter

    private var sectorType: String? = null
    private var search: String? = null
    private var sort: Long? = null
    private var cityId: Long? = null
    private var countryId: Long? = null

    override fun onPause() {
        super.onPause()
        GlobalLogicFunctions.saveSharedPrefrences(requireActivity(),
            SharedPrefrencesType.shows,
            sectorType)
    }

    override fun onResume() {
        super.onResume()
        sectorType = try {
            GlobalLogicFunctions.retrieveSavedSharedPrefrences(requireActivity(),
                SharedPrefrencesType.shows)
        } catch (e: Exception) {
            null
        }
        viewModel.getAllAdsStoreData(sectorType, search, sort, cityId, countryId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shows, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[ShowsViewModel::class.java]
        binding.searchBar.addTextChangedListener {
            search = it.toString()
            viewModel.getAllAdsStoreData(sectorType, search, sort, cityId, countryId)
        }
        binding.searchBtn.setOnClickListener {
            viewModel.openCloseSearchBar()
        }
        logosAdapter = GeneralLogosAdapter(ClickListener {
            when (it.type) {
                "internal" -> requireView().findNavController()
                    .navigate(ShowsFragmentDirections.actionShowsFragmentToCompanyFragment(
                        it.companyId!!.toLong(),
                        it.companyName!!))
                else -> GlobalUiFunctions.navigateToBroswerIntent(it.link, requireActivity())
            }
        })
        binding.logosRecyclerView.adapter = logosAdapter

        sectorsAdapter = LocalStockSectorsAdapter(ClickListener {
            sectorType = it.type.toString()
            viewModel.getAllAdsStoreData(sectorType, search, sort, cityId, countryId)
        })
        binding.sectorsRecyclerView.adapter = sectorsAdapter

        showsAdapter = ShowsAdapter(ClickListener {
            requireView().findNavController()
                .navigate(
                    ShowsFragmentDirections.actionShowsFragmentToShowsDetailsFragment(
                        it.id!!.toLong(),
                        it.name!!
                    )
                )
        })
        binding.showsListRecyclerView.apply {
            adapter = showsAdapter
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    showsListRecyclerView.visibility = View.GONE
                    loadingProgressbar.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                }
            } else {
                binding.apply {
                    loadingProgressbar.visibility = View.GONE
                }
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
        viewModel.showsStoreData.observe(viewLifecycleOwner) {

            if (it != null) {
                Log.i("showsListData", it.data.toString())
                binding.apply {
                    showsListRecyclerView.visibility = View.VISIBLE
                }
//                bannersAdapter.submitList(it.banners)
                enableImageSlider(it.banners, binding.bannersImageSlider, requireActivity())
                logosAdapter.submitList(it.logos)
                sectorsAdapter.submitList(it.sectors)
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
                            sectorType = filterData.section
                            viewModel.getAllAdsStoreData(
                                sectorType,
                                search,
                                filterData.sort?.toLong(),
                                filterData.city?.toLong(),
                                filterData.country?.toLong()
                            )
                        })
                }
                showsAdapter.submitList(it.data)
            } else {
                binding.apply {
                    loadingProgressbar.visibility = View.VISIBLE
                    stockPageLayout.visibility = View.GONE
                }
            }
        }

        return binding.root
    }


}