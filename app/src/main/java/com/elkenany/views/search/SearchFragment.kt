package com.elkenany.views.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentSearchBinding
import com.elkenany.entities.search.Result
import com.elkenany.viewmodels.SearchViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.search.adapter.SearchResultsAdapter


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
            search = it.toString()
            viewModel.getAllSearchData(search)
        }

        resultsAdapter = SearchResultsAdapter(ClickListener {
            onSearchResultNavigation(it)
        })
        binding.searchResultsRecyclerView.apply {
            adapter = resultsAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

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

    private fun onSearchResultNavigation(result: Result) {
        when (result.type) {
            "companies" -> requireView().findNavController()
                .navigate(SearchFragmentDirections.actionSearchFragmentToCompanyFragment(result.id,
                    result.name))
            "guide_sub_sections" -> requireView().findNavController()
                .navigate(SearchFragmentDirections.actionSearchFragmentToGuideCompaniesFragment(
                    result.id,
                    result.name,
                    ""))
            "news" -> requireView().findNavController()
                .navigate(SearchFragmentDirections.actionSearchFragmentToNewsDetailsFragment(result.id.toInt()))
            "stores" -> requireView().findNavController()
                .navigate(SearchFragmentDirections.actionSearchFragmentToAdDetailsFragment(result.id))
            "local_stock_sub" -> requireView().findNavController()
                .navigate(SearchFragmentDirections.actionSearchFragmentToLocalStockDetailsFragment(
                    result.id,
                    "",
                    "local"))
            "fodder_stock_sub" -> requireView().findNavController()
                .navigate(SearchFragmentDirections.actionSearchFragmentToLocalStockDetailsFragment(
                    result.id,
                    "",
                    "fodder"))
            else -> Toast.makeText(requireContext(), "حدوث خطأ في عملية العرض", Toast.LENGTH_SHORT)
                .show()

        }
    }

}