package com.elkenany.views.store

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
import com.elkenany.databinding.FragmentStoreBinding
import com.elkenany.entities.guide.Sector
import com.elkenany.utilities.GlobalUiFunctions
import com.elkenany.viewmodels.StoreViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.local_stock.adapter.LocalStockSectorsAdapter
import com.elkenany.views.store.adapter.AdsStoreAdapter


class StoreFragment : Fragment() {

    private lateinit var binding: FragmentStoreBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: StoreViewModel
    private lateinit var sectorsAdapter: LocalStockSectorsAdapter
    private lateinit var adsStoreAdapter: AdsStoreAdapter
    private var sectorType: Long? = null
    private var search: String? = null

    override fun onResume() {
        super.onResume()
        viewModel.getAllAdsStoreData(sectorType, search)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[StoreViewModel::class.java]
        binding.searchBar.addTextChangedListener {
            search = it.toString()
            adsStoreAdapter.submitList(listOf())
            viewModel.getAllAdsStoreData(sectorType, search)
        }
        binding.searchBtn.setOnClickListener {
            viewModel.openCloseSearchBar()
        }

        sectorsAdapter = LocalStockSectorsAdapter(ClickListener {
            sectorType = it.id
            adsStoreAdapter.submitList(listOf())
            viewModel.getAllAdsStoreData(sectorType, search)
        })
        binding.sectorsRecyclerView.apply {
            adapter = sectorsAdapter
//            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        adsStoreAdapter = AdsStoreAdapter(ClickListener {
            requireView().findNavController()
                .navigate(StoreFragmentDirections.actionStoreFragmentToAdDetailsFragment(it.id!!))
        })
        binding.storeRecyclerView.apply {
            adapter = adsStoreAdapter
        }

        viewModel.adsStoreData.observe(viewLifecycleOwner) {
            if (it != null) {
                sectorsAdapter.submitList(it.sectors)
                if (it.data.isNotEmpty()) {
                    binding.storeRecyclerView.visibility = View.VISIBLE
                    binding.storeRecyclerView.scrollToPosition(0)
                    binding.errorMessage.visibility = View.GONE
                    //submitting lists to its own adapters
                    adsStoreAdapter.submitList(it.data)
                    val sectosList =
                        it.sectors.map { sector ->
                            Sector(
                                sector!!.id,
                                sector.name,
                                sector.type,
                                sector.selected
                            )
                        }.toList()
                    binding.filtersBtn.setOnClickListener {
                        GlobalUiFunctions.openFilterDialog(requireActivity(),
                            inflater,
                            sectosList,
                            null,
                            null,
                            null,
                            ClickListener { filterData ->
                                viewModel.getAllAdsStoreData(filterData.section!!.toLong(), search)
                            })
                    }

                } else {
                    binding.storeRecyclerView.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.errorMessage.text = "لا توجد نتائج في محرك البحث"
                    Log.i("null", it.toString())
                }
            } else {
                binding.storeRecyclerView.visibility = View.GONE
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
                binding.storeRecyclerView.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        return binding.root
    }

}