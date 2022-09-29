package com.elkenany.views.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentShowsBinding
import com.elkenany.viewmodels.ShowsViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.local_stock.adapter.LocalStockBannersAdapter
import com.elkenany.views.local_stock.adapter.LocalStockLogosAdapter
import com.elkenany.views.local_stock.adapter.LocalStockSectorsAdapter

class ShowsFragment : Fragment() {
    private lateinit var binding: FragmentShowsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ShowsViewModel
    private lateinit var bannersAdapter: LocalStockBannersAdapter
    private lateinit var logosAdapter: LocalStockLogosAdapter
    private lateinit var sectorsAdapter: LocalStockSectorsAdapter

    private var sectorType: String = "poultry"
    private var search: String? = null
    private var sort: Long? = 0
    private var cityId: Long? = 0
    private var countryId: Long? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shows, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[ShowsViewModel::class.java]
        viewModel.getAllAdsStoreData(sectorType, search, sort, cityId, countryId)
        bannersAdapter = LocalStockBannersAdapter(ClickListener { })
        binding.bannersRecyclerView.adapter = bannersAdapter

        logosAdapter = LocalStockLogosAdapter(ClickListener { })
        binding.logosRecyclerView.adapter = logosAdapter

        sectorsAdapter = LocalStockSectorsAdapter(ClickListener {
            sectorType = it.type.toString()
            viewModel.getAllAdsStoreData(sectorType, search, sort, cityId, countryId)
        })
        binding.sectorsRecyclerView.adapter = sectorsAdapter

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
        viewModel.showsStoreData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    showsListRecyclerView.visibility = View.GONE
                }
                bannersAdapter.submitList(it.banners)
                logosAdapter.submitList(it.logos)
                sectorsAdapter.submitList(it.sectors)
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