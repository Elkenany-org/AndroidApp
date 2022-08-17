package com.example.elkenany.views.local_stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentLocalStockDetailsBinding
import com.example.elkenany.viewmodels.LocalStockDetailsViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.local_stock.adapter.LocalStockBannersAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockDetailsAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockLogosAdapter


class LocalStockDetailsFragment : Fragment() {
    private lateinit var binding: FragmentLocalStockDetailsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: LocalStockDetailsViewModel
    private val args: LocalStockDetailsFragmentArgs by navArgs()
    private lateinit var bannersAdapter: LocalStockBannersAdapter
    private lateinit var logosAdapter: LocalStockLogosAdapter
    private lateinit var localStockDetailsAdapter: LocalStockDetailsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_local_stock_details,
            container,
            false)
        viewModelFactory = ViewModelFactory()
        viewModel =
            ViewModelProvider(this, viewModelFactory)[LocalStockDetailsViewModel::class.java]
        binding.appBarTitle.text = args.sectorName
        bannersAdapter = LocalStockBannersAdapter(ClickListener { })
        binding.bannersRecyclerView.apply {
            adapter = bannersAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        logosAdapter = LocalStockLogosAdapter(ClickListener { })
        binding.logosRecyclerView.apply {
            adapter = logosAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        localStockDetailsAdapter = LocalStockDetailsAdapter(ClickListener { })
        binding.stockDataRecyclerView.adapter = localStockDetailsAdapter
        viewModel.getLocalStockDetailsData(args.id, "",args.sectorType.toString())
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.stockDataRecyclerView.visibility = View.GONE
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.foundDataLayout.visibility = View.GONE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        viewModel.localStockDetailsData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.errorMessage.visibility = View.GONE
                binding.foundDataLayout.visibility = View.VISIBLE
                binding.stockDataRecyclerView.visibility = View.VISIBLE
                logosAdapter.submitList(it.logos)
                bannersAdapter.submitList(it.banners)
                localStockDetailsAdapter.submitList(listOf(it.columns) + it.members)

            } else {
                binding.foundDataLayout.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
                binding.stockDataRecyclerView.visibility = View.GONE
            }
        }


        return binding.root
    }

}