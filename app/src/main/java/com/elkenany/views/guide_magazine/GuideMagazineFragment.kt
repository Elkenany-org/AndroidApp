package com.elkenany.views.guide_magazine

import android.content.Intent
import android.net.Uri
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
import com.elkenany.entities.stock_data.LocalStockBanner
import com.elkenany.viewmodels.GuideMagazineViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.guide_magazine.adapter.GuideMagazineAdapter
import com.elkenany.views.local_stock.adapter.LocalStockBannersAdapter
import com.elkenany.views.local_stock.adapter.LocalStockLogosAdapter
import com.elkenany.views.local_stock.adapter.LocalStockSectorsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GuideMagazineFragment : Fragment() {
    private lateinit var binding: FragmentGuideMagazineBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: GuideMagazineViewModel
    private lateinit var sectorsAdapter: LocalStockSectorsAdapter
    private lateinit var bannersAdapter: LocalStockBannersAdapter
    private lateinit var logosAdapter: LocalStockLogosAdapter
    private lateinit var magazineAdapter: GuideMagazineAdapter
    private var sectorType: String = "poultry"
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
        bannersAdapter = LocalStockBannersAdapter(ClickListener {
            navigateToBroswerIntent(it.link)
        })
        binding.bannersRecyclerView.apply {
            adapter = bannersAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        binding.searchBar.addTextChangedListener {
            search = it.toString()
            viewModel.getGuideData(sectorType, sort, cityId, search)
        }
        logosAdapter = LocalStockLogosAdapter(ClickListener {
            navigateToBroswerIntent(it.link)
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
                .navigate(GuideMagazineFragmentDirections.actionGuideMagazineFragmentToGuideMagazineDetailsFragment(
                    it.id!!))
        })
        binding.magazineListRecyclerView.adapter = magazineAdapter


        viewModel.magazineData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    magazineListRecyclerView.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                    bannersAdapter.submitList(it.banners)
                    logosAdapter.submitList(it.logos)
                    sectorsAdapter.submitList(it.sectors)
                    magazineAdapter.submitList(it.data)
                    magazineListRecyclerView.smoothScrollToPosition(0)
                    bannersRecyclerView.smoothScrollToPosition(0)
                    scrollRecyclerView(it.banners)
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

    private fun navigateToBroswerIntent(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }


}