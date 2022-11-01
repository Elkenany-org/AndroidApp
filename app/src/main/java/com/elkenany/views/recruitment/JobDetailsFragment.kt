package com.elkenany.views.recruitment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.elkenany.R
import com.elkenany.databinding.FragmentJobDetailsBinding
import com.elkenany.entities.recruitment.JobDetailsData
import com.elkenany.viewmodels.JobDetailsViewModel
import com.elkenany.viewmodels.ViewModelFactory


class JobDetailsFragment : Fragment() {
    private lateinit var binding: FragmentJobDetailsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: JobDetailsViewModel
    private val args: JobDetailsFragmentArgs by navArgs()
    private var jobDetailsData: JobDetailsData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_details, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[JobDetailsViewModel::class.java]
        binding.applyNowBtn.setOnClickListener {
            if (jobDetailsData != null) {
                viewModel.applyForJob()
            }
        }
        viewModel.getJobDetailsData(args.id)
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    loadingProgressbar.visibility = View.VISIBLE
                    layout.visibility = View.GONE
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
                1 -> {
                    requireView().findNavController().navigate(
                        JobDetailsFragmentDirections.actionJobDetailsFragmentToApplyToJobFragment(
                            args.id.toLong(),
                            jobDetailsData!!.images,
                            jobDetailsData!!.title,
                            jobDetailsData!!.workHours,
                            jobDetailsData!!.companyName,
                            jobDetailsData!!.salary.toString(),
                            jobDetailsData!!.experience
                        )
                    )
                }
                200 -> {
                    binding.errorMessage.visibility = View.GONE
                    binding.layout.visibility = View.VISIBLE
                }
                401 -> {
//                    binding.errorMessage.text =
//                        "برجاء تسجيل الدخول أولا حتي تتمكن من معرفة تفاصيل الوظائف"
                    Toast.makeText(
                        requireContext(),
                        "برجاء تسجيل الدخول أولا حتي تتمكن من التقديم علي الوظيفة",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.errorMessage.visibility = View.GONE
//                    binding.layout.visibility = View.GONE
                }
                402 -> {
                    binding.errorMessage.text =
                        "برجاء التحويل الي الباقة المدفوعة لمعرفة تفاصيل أكثر"
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.layout.visibility = View.GONE
                }
                404 -> {
                    binding.errorMessage.text =
                        "لا توجد بيانات لهذا الإعلان"
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.layout.visibility = View.GONE
                }
                else -> {
                    binding.errorMessage.text =
                        "تعذر الحصول علي المعلومات"
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.layout.visibility = View.GONE
                }
            }
        }
        viewModel.responseData.observe(viewLifecycleOwner) { jobsData ->
            if (jobsData != null) {
                jobDetailsData = jobsData
                binding.data = jobsData
            }
        }

        return binding.root
    }
}