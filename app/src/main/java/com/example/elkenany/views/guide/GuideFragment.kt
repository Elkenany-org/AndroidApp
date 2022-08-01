package com.example.elkenany.views.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentGuideBinding
import com.example.elkenany.viewmodels.GuideViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.guide.adapter.GuideSubSectionAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockBannersAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockLogosAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockSectorsAdapter


class GuideFragment : Fragment() {
    private lateinit var binding: FragmentGuideBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: GuideViewModel
    private lateinit var bannersAdapter: LocalStockBannersAdapter
    private lateinit var logosAdapter: LocalStockLogosAdapter
    private lateinit var sectorsAdapter: LocalStockSectorsAdapter
    private lateinit var subSection: GuideSubSectionAdapter
    private var search: String? = null
    private val args: GuideFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_guide, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[GuideViewModel::class.java]

        viewModel.getGuideData(args.sectorType.toString(), search)

//        binding.searchBar.addTextChangedListener {
//            search = it.toString()
//            viewModel.getAllNewsData(args.sectorType.toString(), search)
//        }

        bannersAdapter = LocalStockBannersAdapter(ClickListener { })
        binding.bannersRecyclerView.adapter = bannersAdapter

        logosAdapter = LocalStockLogosAdapter(ClickListener { })
        binding.logosRecyclerView.adapter = logosAdapter

        sectorsAdapter = LocalStockSectorsAdapter(ClickListener {
            viewModel.getGuideData(it.type.toString(), search)
        })
        binding.sectorsRecyclerView.adapter = sectorsAdapter

        subSection = GuideSubSectionAdapter(ClickListener {
            view!!.findNavController()
                .navigate(GuideFragmentDirections.actionGuideFragmentToGuideCompaniesFragment(it.id!!,
                    it.name,
                    args.sectorType))
        })
        binding.guideListRecyclerView.adapter = subSection
        viewModel.guideData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.guidePageLayout.visibility = View.VISIBLE
                binding.errorMessage.visibility = View.GONE
                //submitting lists to its own adapters
                bannersAdapter.submitList(it.banners)
                logosAdapter.submitList(it.logos)
                sectorsAdapter.submitList(it.sectors)
                subSection.submitList(it.subSections)

            } else {
                binding.guidePageLayout.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.guidePageLayout.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        return binding.root
    }

}