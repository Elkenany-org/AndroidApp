package com.elkenany.views.recruitment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elkenany.R
import com.elkenany.databinding.FragmentAddNewJobBinding
import com.elkenany.viewmodels.AddNewJobViewModel
import com.elkenany.viewmodels.ViewModelFactory


class AddNewJobFragment : Fragment() {
    private lateinit var binding: FragmentAddNewJobBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AddNewJobViewModel

    private var name: String = ""
    private var department: String = ""
    private var jobNaming: String = ""
    private var jobType: String = ""
    private var description: String = ""
    private var place: String = ""
    private var workHours: String = ""
    private var salary: String = ""
    private var noticePeriod: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_job, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[AddNewJobViewModel::class.java]

        binding.addJobBtn.setOnClickListener {

        }

        return binding.root
    }

    private fun validateApplicable() {
        name = binding.companyName.text.toString().trim()
        department = binding.departmentEt.text.toString().trim()
        jobNaming = binding.jobNameingEt.text.toString().trim()
        jobType = binding.jobTypeEt.text.toString().trim()
        description = binding.jobDescEt.text.toString().trim()
        place = binding.placeEt.text.toString().trim()
        workHours = binding.workingTimeEt.text.toString().trim()
        salary = binding.salaryEt.text.toString().trim()
        noticePeriod = binding.noticePeriodTv.text.toString().trim()
        if (name.isEmpty() && department.isEmpty() && jobNaming.isEmpty() && salary.isEmpty() && jobType.isEmpty() && noticePeriod.isEmpty() && description.isEmpty() && place.isEmpty() && workHours.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "برجاء ادخال جميع البيانات الخاصة بكم",
                Toast.LENGTH_SHORT
            ).show()
        } else if (name.isEmpty()) {
            binding.jobNameingEt.apply {
                requestFocus()
                error("برجاء ادخال الاسم")
            }

        } else if (department.isEmpty()) {
            binding.departmentEt.apply {
                requestFocus()
                error("برجاء ادخال الإيميل")
            }

        } else if (jobNaming.isEmpty()) {
            binding.jobNameingEt.apply {
                requestFocus()
                error("برجاء ادخال رقم الهاتف")
            }
        } else if (salary.isEmpty()) {
            binding.salaryEt.apply {
                requestFocus()
                error("برجاء ادخال الراتب")
            }
        } else if (jobType.isEmpty()) {
            binding.jobTypeEt.apply {
                requestFocus()
                error("برجاء ادخال الخبرة")
            }
        } else if (noticePeriod.isEmpty()) {
            binding.noticePeriodTv.apply {
                requestFocus()
                error("برجاء ادخال فترة الإشعار")
            }
        } else if (description.isEmpty()) {
            binding.jobDescEt.apply {
                requestFocus()
                error("برجاء ارفاق السيرة الذاتية")
            }

        } else if (place.isEmpty()) {
            binding.placeEt.apply {
                requestFocus()
                error("برجاء ارفاق السيرة الذاتية")
            }
        } else if (workHours.isEmpty()) {
            binding.workingTimeEt.apply {
                requestFocus()
                error("برجاء ارفاق السيرة الذاتية")
            }
        } else {

        }
    }

}