package com.elkenany.views.recruitment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentMyAppliedJobsBinding
import com.elkenany.viewmodels.MyAppliedJobsViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.home.HomeFragmentDirections
import com.elkenany.views.recruitment.adapter.MyJobsAdapter


class MyAppliedJobsFragment : Fragment() {
    private lateinit var binding: FragmentMyAppliedJobsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MyAppliedJobsViewModel
    private lateinit var myJobsAdapter: MyJobsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_applied_jobs, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[MyAppliedJobsViewModel::class.java]
        viewModel.getMyJobsData()
        myJobsAdapter = MyJobsAdapter(ClickListener { })
        binding.jobsListRecyclerView.adapter = myJobsAdapter
        binding.loginBtn.setOnClickListener {
            requireParentFragment().requireParentFragment().findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
        }
        binding.addJobBtn.setOnClickListener {
            viewModel.navigateToAddJob()
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    loadingProgressbar.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                    jobsListRecyclerView.visibility = View.GONE
                    binding.loginBtn.visibility = View.GONE
                }
            } else {
                binding.apply {
                    loadingProgressbar.visibility = View.GONE
                }
            }
        }
        viewModel.exception.observe(viewLifecycleOwner) {
            Log.i("exceptionValue", it.toString())
            when (it) {
                null -> {

                }
                200 -> {
                    binding.errorMessage.visibility = View.GONE
                    binding.loginBtn.visibility = View.GONE
                    binding.addJobBtn.visibility = View.VISIBLE
                }
                201 -> {
                    requireView().findNavController()
                        .navigate(MyAppliedJobsFragmentDirections.actionMyAppliedJobsFragmentToAddNewJobFragment())
                    viewModel.onDoneNavigating()
                }
                401 -> {
                    binding.errorMessage.text =
                        "برجاء تسجيل الدخول أولا حتي تتمكن من إضافة وظائف"
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.loginBtn.visibility = View.VISIBLE
                    binding.addJobBtn.visibility = View.GONE
                }
                402 -> {
                    binding.errorMessage.text =
                        "برجاء التحويل الي الباقة المدفوعة لمعرفة تفاصيل أكثر"
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.loginBtn.visibility = View.GONE
                    binding.addJobBtn.visibility = View.GONE
                }
                404 -> {
                    binding.errorMessage.text =
                        "لا توجد أعلانات توظيف بعد"
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.loginBtn.visibility = View.GONE
                    binding.addJobBtn.visibility = View.GONE
                }
                else -> {
                    binding.errorMessage.text =
                        "تعذر الحصول علي المعلومات"
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.loginBtn.visibility = View.GONE
                    binding.addJobBtn.visibility = View.GONE
                }
            }
        }
        viewModel.responseData.observe(viewLifecycleOwner) { jobsData ->
            if (jobsData != null) {
                binding.jobsListRecyclerView.visibility = View.VISIBLE
                myJobsAdapter.submitList(jobsData.jobs)
            } else {
                binding.jobsListRecyclerView.visibility = View.GONE
            }
        }
        return binding.root
    }
}