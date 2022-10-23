package com.elkenany.views.recruitment.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentJobsBinding
import com.elkenany.utilities.GlobalUiFunctions.Companion.enableImageSlider
import com.elkenany.viewmodels.JobsViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.recruitment.JobsListAdapter

class JobsFragment : Fragment() {
    private lateinit var binding: FragmentJobsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: JobsViewModel
    private lateinit var jobListAdapter: JobsListAdapter
    private var sort: Int? = null
    private var category: String? = null
    private var search: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_jobs, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this)[JobsViewModel::class.java]
        // Get all jobs on the first initilaization of the view
        viewModel.getHomeStockData(sort, category, search)

        binding.searchBar.addTextChangedListener {
            search = it.toString()
            viewModel.getHomeStockData(sort, category, search)
        }
        jobListAdapter = JobsListAdapter(ClickListener { }, ClickListener { })
        binding.jobsListRecyclerView.adapter = jobListAdapter
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    loadingProgressbar.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                }
            } else {
                binding.apply {
                    loadingProgressbar.visibility = View.GONE
                }
            }
        }
        viewModel.exception.observe(viewLifecycleOwner) {
            when (it) {
                200 -> {
                    binding.errorMessage.visibility = View.GONE
                }
                401 -> {
                    binding.errorMessage.text =
                        "برجاء تسجيل الدخول أولا حتي تتمكن من معرفة تفاصيل الوظائف"
                    binding.errorMessage.visibility = View.VISIBLE
                }
                402 -> {
                    binding.errorMessage.text =
                        "برجاء التحويل الي الباقة المدفوعة لمعرفة تفاصيل أكثر"
                    binding.errorMessage.visibility = View.VISIBLE
                }
                404 -> {
                    binding.errorMessage.text =
                        "لا توجد أعلانات توظيف بعد"
                    binding.errorMessage.visibility = View.VISIBLE
                }
                else -> {
                    binding.errorMessage.text =
                        "تعذر الحصول علي المعلومات"
                    binding.errorMessage.visibility = View.VISIBLE
                }
            }
        }
        viewModel.responseData.observe(viewLifecycleOwner) { jobsData ->
            if (jobsData != null) {
                enableImageSlider(listOf(), binding.bannersImageSlider, requireActivity())
                jobListAdapter.submitList(jobsData.jobs)
            }
        }
        return binding.root
    }
}