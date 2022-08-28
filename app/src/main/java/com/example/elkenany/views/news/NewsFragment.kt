package com.example.elkenany.views.news

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
    private var sectorType: String = ""
    private var sort: String? = null
    private val args: NewsFragmentArgs by navArgs()
    override fun onResume() {
        super.onResume()
        viewModel.getAllNewsData(sectorType, search, sort)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[NewViewModel::class.java]
        sectorType = try {
            args.sectorType.toString()
        } catch (e: Exception) {
            "poultry"
        }

//        viewModel.getAllNewsData(sectorType, search, sort)
        binding.filterLayout.layoutAnimation =
            AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation)
        binding.searchBar.addTextChangedListener {
            search = it.toString()
            viewModel.getAllNewsData(sectorType, search, sort)
        }
        binding.mostReadableBtn.setOnClickListener {
            sort = "1"
            binding.mostReadableBtn.setTextColor(requireContext().getColor(R.color.orange))
            binding.urgentBtn.setTextColor(requireContext().getColor(R.color.green))
            binding.latestNewsBtn.setTextColor(requireContext().getColor(R.color.green))
            viewModel.getAllNewsData(sectorType, search, sort)
        }
        binding.latestNewsBtn.setOnClickListener {
            sort = "2"
            binding.latestNewsBtn.setTextColor(requireContext().getColor(R.color.orange))
            binding.mostReadableBtn.setTextColor(requireContext().getColor(R.color.green))
            binding.urgentBtn.setTextColor(requireContext().getColor(R.color.green))
            viewModel.getAllNewsData(sectorType, search, sort)
        }
        binding.urgentBtn.setOnClickListener {
            sort = "3"
            binding.urgentBtn.setTextColor(requireContext().getColor(R.color.orange))
            binding.mostReadableBtn.setTextColor(requireContext().getColor(R.color.green))
            binding.latestNewsBtn.setTextColor(requireContext().getColor(R.color.green))
            viewModel.getAllNewsData(sectorType, search, sort)
        }
        newsDaumAdapter = NewsDaumAdapter(ClickListener {
            requireView().findNavController()
                .navigate(NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment(it.id!!.toInt()))
        })
        binding.newsRecyclerView.apply {
            adapter = newsDaumAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        newsSectionAdapter = NewsSectionAdapter(ClickListener {
            sectorType = it.type.toString()
            viewModel.getAllNewsData(sectorType, search, sort)
            newsDaumAdapter.submitList(listOf())
        })
        binding.sectorsRecyclerView.apply {
            adapter = newsSectionAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        viewModel.newsData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.filterLayout.visibility = View.VISIBLE
                newsSectionAdapter.submitList(it.sections)
                if (it.data.isNotEmpty()) {
                    binding.newsRecyclerView.visibility = View.VISIBLE
                    binding.errorMessage.visibility = View.GONE
                    //submitting lists to its own adapters
                    newsDaumAdapter.submitList(it.data)
                } else {
                    binding.newsRecyclerView.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.errorMessage.text = "لا توجد نتائج في محرك البحث"
                }
            } else {
                binding.newsRecyclerView.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
                binding.filterLayout.visibility = View.GONE
            }

        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.newsRecyclerView.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
                binding.filterLayout.visibility = View.GONE
            } else {
                binding.loadingProgressbar.visibility = View.GONE

            }
        }
        return binding.root
    }

}