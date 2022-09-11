package com.example.elkenany.views.guide

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
import androidx.navigation.fragment.navArgs
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentGuideCompaniesBinding
import com.example.elkenany.entities.stock_data.LocalStockBanner
import com.example.elkenany.viewmodels.GuideCompaniesViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.guide.adapter.CompaniesAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockBannersAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockLogosAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GuideCompaniesFragment : Fragment() {
    private lateinit var binding: FragmentGuideCompaniesBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: GuideCompaniesViewModel
    private var search: String? = ""
    private lateinit var bannersAdapter: LocalStockBannersAdapter
    private lateinit var logosAdapter: LocalStockLogosAdapter
    private lateinit var companiesAdapter: CompaniesAdapter
    private val args: GuideCompaniesFragmentArgs by navArgs()
    override fun onResume() {
        super.onResume()
        viewModel.getCompaniesGuideData(args.id, search)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_guide_companies, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[GuideCompaniesViewModel::class.java]
        binding.appBarTitle.text = args.name
//        viewModel.getCompaniesGuideData(args.id, search)
        binding.searchBar.addTextChangedListener {
            search = it.toString()
            viewModel.getCompaniesGuideData(args.id, search)
        }
        bannersAdapter = LocalStockBannersAdapter(ClickListener {
            navigateToBroswerIntent(it.link)
        })
        binding.bannersRecyclerView.adapter = bannersAdapter
        logosAdapter = LocalStockLogosAdapter(ClickListener {
            navigateToBroswerIntent(it.link)
        })
        binding.logosRecyclerView.adapter = logosAdapter
        companiesAdapter = CompaniesAdapter(ClickListener {
            requireView().findNavController().navigate(
                GuideCompaniesFragmentDirections.actionGuideCompaniesFragmentToCompanyFragment(it.id!!,
                    it.name!!)
            )
        })
        binding.companyListRecyclerView.apply {
            adapter = companiesAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }
        viewModel.companiesDataData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.companyListRecyclerView.visibility = View.VISIBLE
                binding.errorMessage.visibility = View.GONE
                //submitting lists to its own adapters
                bannersAdapter.submitList(it.banners)
                scrollRecyclerView(it.banners)
                logosAdapter.submitList(it.logos)
                companiesAdapter.submitList(it.compsort + it.data)

            } else {
                binding.companyListRecyclerView.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.companyListRecyclerView.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
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