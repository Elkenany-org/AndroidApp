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
import androidx.navigation.fragment.navArgs
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentApplicantsBinding
import com.elkenany.viewmodels.ApplicantsViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.recruitment.adapter.ApplicantsAdapter

class ApplicantsFragment : Fragment() {
    private lateinit var binding: FragmentApplicantsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ApplicantsViewModel
    private lateinit var applicantsAdapter: ApplicantsAdapter
    private val args: ApplicantsFragmentArgs by navArgs()

    private var jobId: Long? = null
    private var select: Long? = null
    private var search: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_applicants, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[ApplicantsViewModel::class.java]
        jobId = args.id
        viewModel.getApplicantsListData(jobId, select, search)

        applicantsAdapter = ApplicantsAdapter(ClickListener {
            requireView().findNavController()
                .navigate(ApplicantsFragmentDirections.actionApplicantsFragmentToApplicantDetailsFragment(
                    it.id!!.toLong()))
        })
        binding.jobsListRecyclerView.adapter = applicantsAdapter
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    loadingProgressbar.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                    jobsListRecyclerView.visibility = View.GONE
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

                }
                401 -> {
                    binding.errorMessage.text =
                        "برجاء تسجيل الدخول أولا حتي تتمكن من إضافة وظائف"
                    binding.errorMessage.visibility = View.VISIBLE

                }
                402 -> {
                    binding.errorMessage.text =
                        "برجاء التحويل الي الباقة المدفوعة لمعرفة تفاصيل أكثر"
                    binding.errorMessage.visibility = View.VISIBLE

                }
                404 -> {
                    binding.errorMessage.text =
                        "لا توجد متقدمين للتوظيف بعد"
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
                binding.jobsListRecyclerView.visibility = View.VISIBLE
                applicantsAdapter.submitList(jobsData.applicants)
            } else {
                binding.jobsListRecyclerView.visibility = View.GONE
            }
        }
        return binding.root
    }

}