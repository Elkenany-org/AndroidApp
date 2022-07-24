package com.example.elkenany.views.news

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
import com.example.elkenany.databinding.FragmentNewsBinding
import com.example.elkenany.viewmodels.NewViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.news.adapter.NewsDaumAdapter
import com.example.elkenany.views.news.adapter.NewsSectionAdapter


class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var newsDaumAdapter: NewsDaumAdapter
    private lateinit var newsSectionAdapter: NewsSectionAdapter
    private lateinit var viewModel: NewViewModel
    private var search: String? = null
    private val args: NewsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[NewViewModel::class.java]


        viewModel.getAllNewsData(args.sectorType.toString(), search)

        binding.searchBar.addTextChangedListener {
            search = it.toString()
            viewModel.getAllNewsData(args.sectorType.toString(), search)
        }

        newsDaumAdapter = NewsDaumAdapter(ClickListener { })
        binding.newsRecyclerView.adapter = newsDaumAdapter

        newsSectionAdapter = NewsSectionAdapter(ClickListener {
            viewModel.getAllNewsData(it.type.toString(), search)
        })
        binding.sectionsRecyclerView.adapter = newsSectionAdapter

        viewModel.newsData.observe(viewLifecycleOwner) {
            if (it!!.data.isNotEmpty()) {
                binding.newsLayout.visibility = View.VISIBLE
                binding.errorMessage.visibility = View.GONE
                //submitting lists to its own adapters
                newsDaumAdapter.submitList(it.data)
                newsSectionAdapter.submitList(it.sections)

            } else {
                binding.newsLayout.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.newsLayout.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        return binding.root
    }
}