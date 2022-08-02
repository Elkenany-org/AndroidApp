package com.example.elkenany.views.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentGuideCompaniesBinding
import com.example.elkenany.viewmodels.GuideCompaniesViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.guide.adapter.CompaniesAdapter


class GuideCompaniesFragment : Fragment() {
    private lateinit var binding: FragmentGuideCompaniesBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: GuideCompaniesViewModel
    private var search: String? = ""
    private lateinit var companiesAdapter: CompaniesAdapter
    private val args: GuideCompaniesFragmentArgs by navArgs()

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
        viewModel.getCompaniesGuideData(args.id, search)
        binding.searchBar.addTextChangedListener {
            search = it.toString()
            viewModel.getCompaniesGuideData(args.id, search)
        }
        companiesAdapter = CompaniesAdapter(ClickListener { })
        binding.companyListRecyclerView.adapter = companiesAdapter
        viewModel.companiesDataData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.companyListRecyclerView.visibility = View.VISIBLE
                binding.errorMessage.visibility = View.GONE
                //submitting lists to its own adapters
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

}