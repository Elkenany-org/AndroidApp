package com.example.elkenany.views.local_stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentLocalStockBinding
import com.example.elkenany.viewmodels.LocalStockViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.local_stock.adapter.LocalStockBannersAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockLogosAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockSectorsAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockSubSectionsAdapter

class LocalStockFragment : Fragment() {
    private lateinit var binding: FragmentLocalStockBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: LocalStockViewModel
    private lateinit var bannersAdapter: LocalStockBannersAdapter
    private lateinit var logosAdapter: LocalStockLogosAdapter
    private lateinit var sectorsAdapter: LocalStockSectorsAdapter
    private lateinit var subSection: LocalStockSubSectionsAdapter
    private var sectorType: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_local_stock, container, false)
        sectorType = try {
            val args: LocalStockFragmentArgs by navArgs()
            args.sectorType.toString()
        } catch (e: Exception) {
            "poultry"
        }
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[LocalStockViewModel::class.java]
        viewModel.getHomeStockData(sectorType!!)
        bannersAdapter = LocalStockBannersAdapter(ClickListener { })
        binding.bannersRecyclerView.adapter = bannersAdapter

        logosAdapter = LocalStockLogosAdapter(ClickListener { })
        binding.logosRecyclerView.adapter = logosAdapter

        sectorsAdapter = LocalStockSectorsAdapter(ClickListener {
            viewModel.getHomeStockData(it.type.toString())
        })
        binding.sectorsRecyclerView.adapter = sectorsAdapter

        subSection = LocalStockSubSectionsAdapter(ClickListener { })
        binding.stockListRecyclerView.adapter = subSection
        viewModel.homeStockData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.stockPageLayout.visibility = View.VISIBLE
                binding.errorMessage.visibility = View.GONE
                //submitting lists to its own adapters
                bannersAdapter.submitList(it.banners)
                logosAdapter.submitList(it.logos)
                sectorsAdapter.submitList(it.sectors)
                subSection.submitList(it.subSections + it.fodSections)

            } else {
                binding.stockPageLayout.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.stockPageLayout.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        return binding.root
    }

}