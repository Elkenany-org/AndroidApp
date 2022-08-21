package com.example.elkenany.views.local_stock

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
import androidx.recyclerview.widget.GridLayoutManager
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
    private var search: String? = null
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
        viewModel.getHomeStockData(sectorType!!, search)
        bannersAdapter = LocalStockBannersAdapter(ClickListener { })
        binding.bannersRecyclerView.apply {
            adapter = bannersAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        binding.searchBar.addTextChangedListener {
            search = it.toString()
            viewModel.getHomeStockData(sectorType!!, search)
        }
        logosAdapter = LocalStockLogosAdapter(ClickListener { })
        binding.logosRecyclerView.apply {
            adapter = logosAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        sectorsAdapter = LocalStockSectorsAdapter(ClickListener {
            sectorType = it.type.toString()
            viewModel.getHomeStockData(sectorType!!, search)
        })
        binding.sectorsRecyclerView.apply {
            adapter = sectorsAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        subSection = LocalStockSubSectionsAdapter(ClickListener {
            requireView().findNavController()
                .navigate(LocalStockFragmentDirections.actionLocalStockFragmentToLocalStockDetailsFragment(
                    it.id!!,
                    it.name,
                    it.type
                ))
        })
        binding.stockListRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = subSection
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }
        viewModel.homeStockData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    changeViewBtn.visibility = View.VISIBLE
                    searchBar.visibility = View.VISIBLE
                }
                if (it.subSections.isEmpty() && it.fodSections.isEmpty()) {
                    binding.stockPageLayout.visibility = View.GONE
                } else {
                    binding.stockListRecyclerView.visibility = View.VISIBLE
                }
                binding.errorMessage.visibility = View.GONE
                //submitting lists to its own adapters
                bannersAdapter.submitList(it.banners)
                logosAdapter.submitList(it.logos)
                sectorsAdapter.submitList(it.sectors)
                subSection.submitList(it.subSections + it.fodSections)

            } else {
                binding.apply {
                    changeViewBtn.visibility = View.GONE
                    stockListRecyclerView.visibility = View.GONE
                    errorMessage.visibility = View.VISIBLE
                }

            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    loadingProgressbar.visibility = View.VISIBLE
                    stockListRecyclerView.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                    changeViewBtn.visibility = View.GONE
                }
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        return binding.root
    }

}