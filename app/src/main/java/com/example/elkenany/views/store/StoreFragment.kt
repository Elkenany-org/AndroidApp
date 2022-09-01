package com.example.elkenany.views.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentStoreBinding
import com.example.elkenany.viewmodels.StoreViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.local_stock.adapter.LocalStockSectorsAdapter
import com.example.elkenany.views.store.adapter.AdsStoreAdapter


class StoreFragment : Fragment() {

    private lateinit var binding: FragmentStoreBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: StoreViewModel
    private lateinit var sectorsAdapter: LocalStockSectorsAdapter
    private lateinit var adsStoreAdapter: AdsStoreAdapter
    private var sectorType: String = ""
    private val args: StoreFragmentArgs by navArgs()
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
        sectorType = try {
            args.sectorType.toString()
        } catch (e: Exception) {
            "poultry"
        }
//        viewModel.getAllAdsStoreData(sectorType, search)

        binding.searchBar.addTextChangedListener {
            search = it.toString()
            adsStoreAdapter.submitList(listOf())
            viewModel.getAllAdsStoreData(sectorType, search)
        }

        sectorsAdapter = LocalStockSectorsAdapter(ClickListener {
            sectorType = it.type.toString()
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

                } else {
                    binding.storeRecyclerView.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.errorMessage.text = "لا توجد نتائج في محرك البحث"
                }
            } else {
                binding.storeRecyclerView.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
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