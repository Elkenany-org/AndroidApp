package com.elkenany.views.local_stock

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
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentLocalStockBinding
import com.elkenany.utilities.GlobalUiFunctions.Companion.enableImageSlider
import com.elkenany.utilities.GlobalUiFunctions.Companion.navigateToBroswerIntent
import com.elkenany.viewmodels.LocalStockViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.local_stock.adapter.LocalStockLogosAdapter
import com.elkenany.views.local_stock.adapter.LocalStockSectorsAdapter
import com.elkenany.views.local_stock.adapter.LocalStockSubSectionsAdapter


class LocalStockFragment : Fragment() {
    private lateinit var binding: FragmentLocalStockBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: LocalStockViewModel
    private lateinit var logosAdapter: LocalStockLogosAdapter
    private lateinit var sectorsAdapter: LocalStockSectorsAdapter
    private lateinit var subSection: LocalStockSubSectionsAdapter
    private var sectorType: String? = null
    private var search: String? = null
//    override fun onResume() {
//        super.onResume()
//        viewModel.getHomeStockData(sectorType!!, search)
//    }

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
        binding.bannersImageSlider.apply {
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        binding.searchBar.addTextChangedListener {
            search = it.toString()
            viewModel.getHomeStockData(sectorType!!, search)
        }
        logosAdapter = LocalStockLogosAdapter(ClickListener {
            navigateToBroswerIntent(it.link, requireActivity())
        })
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
                it.sectors.map { sector ->
                    if (sector!!.selected == 1L) {
                        binding.sectorsRecyclerView.smoothScrollToPosition(sector.id!!.toInt())
                    }
                }
                binding.apply {
                    searchBar.visibility = View.VISIBLE
                }
                val list = it.fodSections + it.subSections
                if (list.isEmpty()) {
                    binding.stockListRecyclerView.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                } else {
                    binding.stockListRecyclerView.visibility = View.VISIBLE
                    binding.stockListRecyclerView.scrollToPosition(0)
                    binding.errorMessage.visibility = View.GONE
                }
                binding.errorMessage.visibility = View.GONE
                enableImageSlider(it.banners, binding.bannersImageSlider, requireActivity())
                logosAdapter.submitList(it.logos)
                sectorsAdapter.submitList(it.sectors)
                subSection.submitList(list)

            } else {
                binding.apply {
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
                }
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        return binding.root
    }


}