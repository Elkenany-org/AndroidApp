package com.elkenany.views.recruitment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.elkenany.R
import com.elkenany.databinding.FragmentApplicantDetailsBinding
import com.elkenany.viewmodels.ApplicantDetailsViewModel
import com.elkenany.viewmodels.ViewModelFactory


class ApplicantDetailsFragment : Fragment() {
    private lateinit var binding: FragmentApplicantDetailsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ApplicantDetailsViewModel
    private val args: ApplicantDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_applicant_details, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[ApplicantDetailsViewModel::class.java]
        viewModel.getApplicationData(args.id)
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
                binding.data = jobsData.application
            }
        }
        return binding.root
    }

}