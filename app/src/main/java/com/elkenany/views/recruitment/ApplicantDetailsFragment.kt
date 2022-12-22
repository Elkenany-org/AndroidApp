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
import com.elkenany.databinding.FragmentApplicantDetailsBinding
import com.elkenany.utilities.GlobalUiFunctions.Companion.callThisNumber
import com.elkenany.utilities.GlobalUiFunctions.Companion.emailThisEmail
import com.elkenany.utilities.GlobalUiFunctions.Companion.navigateToBroswerIntent
import com.elkenany.viewmodels.ApplicantDetailsViewModel
import com.elkenany.viewmodels.ViewModelFactory


class ApplicantDetailsFragment : Fragment() {
    private lateinit var binding: FragmentApplicantDetailsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ApplicantDetailsViewModel
    private var cv: String? = ""
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
        binding.acceptedBtn.setOnClickListener {
            viewModel.addQualifiedApplicants(
                "1",
                args.id.toString()
            )
        }
        binding.rejectedBtn.setOnClickListener {
            viewModel.addQualifiedApplicants(
                "0",
                args.id.toString()
            )
        }
        binding.consideringBtn.setOnClickListener {
            viewModel.addQualifiedApplicants(
                null,
                args.id.toString()
            )
        }
        binding.phoneTv.setOnClickListener {
            callThisNumber(
                binding.phoneTv.text.toString().trim(),
                requireContext(),
                requireActivity()
            )
        }
        binding.emailTv.setOnClickListener {
            emailThisEmail(
                binding.emailTv.text.toString().trim(),
                requireActivity()
            )
        }
        binding.attachCvBtn.setOnClickListener {
            navigateToBroswerIntent(cv, requireActivity())
        }
        viewModel.getApplicationData(args.id)
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
                null -> {

                }
                200 -> {
                }
                201 -> {
                    Toast.makeText(
                        requireContext(),
                        "تم التحديد بنجاح", Toast.LENGTH_SHORT
                    ).show()
                    requireView().findNavController().popBackStack()

                }
                401 -> {
                    binding.errorMessage.text =
                        "برجاء تسجيل الدخول أولا حتي تتمكن من إضافة وظائف"
                    binding.errorMessage.visibility = View.VISIBLE

                }
                402 -> {
                    Toast.makeText(
                        requireContext(),
                        "برجاء التحويل الي الباقة المدفوعة لمعرفة تفاصيل أكثر", Toast.LENGTH_SHORT
                    ).show()

                }
                404 -> {

                    Toast.makeText(
                        requireContext(),
                        "لا توجد متقدمين للتوظيف بعد", Toast.LENGTH_SHORT
                    ).show()


                }
                else -> {
                    Toast.makeText(
                        requireContext(),
                        "تعذر تحديد أختيارك", Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }

        viewModel.responseData.observe(viewLifecycleOwner) { jobsData ->
            if (jobsData != null) {
                binding.apply {
                    layout.visibility = View.VISIBLE
                    data = jobsData.application
                    cv = jobsData.application!!.cv!!
                }

            }
        }
        return binding.root
    }

}