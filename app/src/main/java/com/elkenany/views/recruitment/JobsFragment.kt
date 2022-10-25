package com.elkenany.views.recruitment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentJobsBinding
import com.elkenany.utilities.GlobalUiFunctions.Companion.enableImageSlider
import com.elkenany.viewmodels.JobsViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.recruitment.adapter.JobCategoriesAdapter
import com.elkenany.views.recruitment.adapter.JobsListAdapter

class JobsFragment : Fragment() {
    private lateinit var binding: FragmentJobsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: JobsViewModel
    private lateinit var jobCategoriesAdapter: JobCategoriesAdapter
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
        jobCategoriesAdapter = JobCategoriesAdapter(ClickListener { })
        binding.sectorsRecyclerView.adapter = jobCategoriesAdapter
        jobListAdapter = JobsListAdapter(ClickListener {
            requireView().findNavController()
                .navigate(JobsFragmentDirections.actionJobsFragmentToJobDetailsFragment(it.id!!.toInt()))
        }, ClickListener {
            viewModel.addToFavorite(it.id!!.toInt())
        })
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
                201 -> {
                    Toast.makeText(requireContext(), "تمت الإضافة الي المفضلة", Toast.LENGTH_SHORT)
                        .show()
                }
                200 -> {
                    binding.errorMessage.visibility = View.GONE
                }
                401 -> {
                    Toast.makeText(requireContext(), "برجاء تسجيل الدخول أولا حتي تتمكن من معرفة تفاصيل الوظائف\"", Toast.LENGTH_SHORT)
                        .show()
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
                enableImageSlider(jobsData.banners, binding.bannersImageSlider, requireActivity())
                jobCategoriesAdapter.submitList(jobsData.categories)
                jobListAdapter.submitList(jobsData.jobs)
            }
        }
        return binding.root
    }
}