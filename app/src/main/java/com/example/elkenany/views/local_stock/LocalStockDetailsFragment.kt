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
import com.example.elkenany.databinding.FragmentLocalStockDetailsBinding
import com.example.elkenany.viewmodels.LocalStockDetailsViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.local_stock.adapter.LocalStockBannersAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockLogosAdapter


class LocalStockDetailsFragment : Fragment() {
    private lateinit var binding: FragmentLocalStockDetailsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: LocalStockDetailsViewModel
    private val args: LocalStockDetailsFragmentArgs by navArgs()

    private lateinit var bannersAdapter: LocalStockBannersAdapter
    private lateinit var logosAdapter: LocalStockLogosAdapter
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
        binding.bannersRecyclerView.adapter = bannersAdapter

        logosAdapter = LocalStockLogosAdapter(ClickListener { })
        binding.logosRecyclerView.adapter = logosAdapter

        viewModel.getLocalStockDetailsData(args.id, args.sectorType!!)
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
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
                logosAdapter.submitList(it.logos)
                bannersAdapter.submitList(it.banners)
            } else {
                binding.foundDataLayout.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
            }
        }


        return binding.root
    }

}