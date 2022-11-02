package com.elkenany.views.recruitment

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
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
import com.elkenany.databinding.FragmentApplyJobBinding
import com.elkenany.viewmodels.ApplyToJobViewModel
import com.elkenany.viewmodels.ViewModelFactory
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody


@Suppress("DEPRECATION")
class ApplyToJobFragment : Fragment() {
    private lateinit var binding: FragmentApplyJobBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ApplyToJobViewModel
    private val args: ApplyToJobFragmentArgs by navArgs()

    private var fileToUpload: MultipartBody.Part? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_apply_job, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[ApplyToJobViewModel::class.java]
        binding.apply {
            jobImage = args.jobImage
            companyName = args.companyName
            jobType = args.jobWorkHours
            jobTitle = args.jobTitle
            experience = args.experience
            expectedSalary = args.salary
        }
        binding.attachCvBtn.setOnClickListener {
            getCvFromStorage()
        }
        binding.applyNowBtn.setOnClickListener {
            validateApplicable()
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    loadingProgressbar.visibility = View.VISIBLE
                    binding.applyNowBtn.visibility = View.GONE
                }
            } else {
                binding.apply {
                    loadingProgressbar.visibility = View.GONE
                    binding.applyNowBtn.visibility = View.VISIBLE
                }
            }
        }
        viewModel.exception.observe(viewLifecycleOwner) {
            Log.i("exceptionValue", it.toString())
            when (it) {
                200 -> {
                    requireView().findNavController()
                        .navigate(ApplyToJobFragmentDirections.actionApplyToJobFragmentToApplySuccessfullyFragment())
//                    Toast.makeText(requireContext(), "تم التقديم بنجاح", Toast.LENGTH_SHORT).show()
                }
                401 -> {
                    Toast.makeText(
                        requireContext(),
                        "برجاء تسجيل الدخول أولا حتي تتمكن من معرفة تفاصيل الوظائف",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                402 -> {
                    Toast.makeText(
                        requireContext(),
                        "برجاء التحويل الي الباقة المدفوعة لمعرفة تفاصيل أكثر",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                404 -> {
                    Toast.makeText(
                        requireContext(),
                        "لا توجد أعلانات توظيف بعد",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "فشل في عملية التقديم", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        return binding.root
    }

    @SuppressLint("IntentReset")
    private fun getCvFromStorage() {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "application/pdf"
        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(pdfIntent, 12)
    }

    private fun validateApplicable() {
        val name = binding.nameTv.text.toString().trim()
        val email = binding.emailTv.text.toString().trim()
        val phone = binding.phoneTv.text.toString().trim()
        val salary = binding.salary.text.toString().trim()
        val experience = binding.expereinceTv.text.toString().trim()
        val noticePeriod = binding.noticePeriodTv.text.toString().trim()
        if (name.isEmpty() && email.isEmpty() && phone.isEmpty() && salary.isEmpty() && experience.isEmpty() && noticePeriod.isEmpty() && fileToUpload == null) {
            Toast.makeText(
                requireContext(),
                "برجاء ادخال جميع البيانات الخاصة بكم",
                Toast.LENGTH_SHORT
            ).show()
        } else if (name.isEmpty()) {
            binding.nameTv.apply {
                requestFocus()
                error("برجاء ادخال الاسم")
            }

        } else if (email.isEmpty()) {
            binding.emailTv.apply {
                requestFocus()
                error("برجاء ادخال الإيميل")
            }

        } else if (phone.isEmpty()) {
            binding.phoneTv.apply {
                requestFocus()
                error("برجاء ادخال رقم الهاتف")
            }
        } else if (salary.isEmpty()) {
            binding.salary.apply {
                requestFocus()
                error("برجاء ادخال الراتب")
            }
        } else if (experience.isEmpty()) {
            binding.expereinceTv.apply {
                requestFocus()
                error("برجاء ادخال الخبرة")
            }
        } else if (noticePeriod.isEmpty()) {
            binding.noticePeriodTv.apply {
                requestFocus()
                error("برجاء ادخال فترة الإشعار")
            }
        } else if (fileToUpload == null) {
            binding.attachCvBtn.apply {
                requestFocus()
                error("برجاء ارفاق السيرة الذاتية")
            }
        } else {
            viewModel.applyToJob(
                "developer",
                experience,
                args.jobId.toInt(),
                salary,
                "no infos",
                fileToUpload!!,
                name,
                phone,
                noticePeriod
            )
        }
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("SetTextI18n", "Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            12 -> if (resultCode == RESULT_OK) {
                val uri = data?.data!!
                var pdfName: String? = null
                //Create a file object using file path
                if (uri.toString().startsWith("content://")) {
                    var myCursor: Cursor? = null
                    try {
                        // Setting the PDF to the TextView
                        myCursor =
                            requireActivity().applicationContext!!.contentResolver.query(
                                uri,
                                null,
                                null,
                                null,
                                null
                            )
                        if (myCursor != null && myCursor.moveToFirst()) {
                            pdfName =
                                myCursor.getString(myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            binding.attachCvBtn.text = pdfName
                        }
                    } finally {
                        myCursor?.close()
                    }
                }
                val file = requireActivity().contentResolver.openInputStream(uri)
                Log.i("filePdf", file.toString())
                val fileBytes = file!!.readBytes()
                val requestBody: RequestBody =
                    RequestBody.create(MediaType.parse("application/pdf"), fileBytes)
                fileToUpload =
                    MultipartBody.Part.createFormData("cv_link", pdfName, requestBody)
            }
        }
    }
}