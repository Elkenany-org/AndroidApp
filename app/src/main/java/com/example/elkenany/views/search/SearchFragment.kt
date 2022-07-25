package com.example.elkenany.views.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentSearchBinding
import com.example.elkenany.viewmodels.SearchViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.search.adapter.SearchResultsAdapter


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SearchViewModel
    private lateinit var resultsAdapter: SearchResultsAdapter
    private var search: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]

        binding.searchBar.addTextChangedListener {
            if (it.isNullOrEmpty()) {
            } else {
                search = it.toString()
                viewModel.getAllSearchData(search)
            }

        }

        resultsAdapter = SearchResultsAdapter(ClickListener { })
        binding.searchResultsRecyclerView.adapter = resultsAdapter

        viewModel.searchData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.result.isNotEmpty()) {
                    binding.searchResultsRecyclerView.visibility = View.VISIBLE
                    binding.errorMessage.visibility = View.GONE
                    //submitting lists to its own adapters
                    resultsAdapter.submitList(it.result)

                } else {
                    binding.searchResultsRecyclerView.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.errorMessage.text = "لا توجد نتائج في محرك البحث"
                }
            } else {
                binding.searchResultsRecyclerView.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
            }

        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.searchResultsRecyclerView.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        return binding.root
    }

}