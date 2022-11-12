package com.elkenany.views.recruitment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.elkenany.R
import com.elkenany.databinding.FragmentAddNewJobBinding
import com.elkenany.viewmodels.AddNewJobViewModel
import com.elkenany.viewmodels.ViewModelFactory


class AddNewJobFragment : Fragment() {
    private lateinit var binding: FragmentAddNewJobBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AddNewJobViewModel
    private lateinit var companiesAdapter: ArrayAdapter<String?>
    private lateinit var departmentsAdapter: ArrayAdapter<String?>
    private lateinit var workingHoursAdapter: ArrayAdapter<String?>

    private var companyTitle: String = "الشركة"
    private var departmentTitle: String = "القسم"
    private var workHoursTitle: String = "الدوام"
    private var name: String = ""
    private var departmentId: String = ""
    private var jobNaming: String = ""
    private var description: String = ""
    private var place: String = ""
    private var experience: String = ""
    private var workHours: String = ""
    private var salary: String = ""
    private var companyId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_job, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[AddNewJobViewModel::class.java]
        binding.companyAutoCompelete.setText(companyTitle)
        binding.departmentAutoCompelete.setText(departmentTitle)
        binding.workHoursCompelete.setText(workHoursTitle)
        viewModel.companiesListData.observe(viewLifecycleOwner) {
            if (it != null) {
                val companies = it.result?.map { company ->
                    company.name.toString()
                }?.toList()
                companiesAdapter = ArrayAdapter(requireContext(),
                    R.layout.array_adapter_item,
                    companies!!)
                binding.companyAutoCompelete.setAdapter(companiesAdapter)
                binding.companyAutoCompelete.setOnItemClickListener { adapterView, _, position, _ ->
                    Toast.makeText(requireContext(),
                        it.result[position].name,
                        Toast.LENGTH_SHORT).show()
                    companyId = it.result[position].id.toString()
                    binding.companyAutoCompelete.hint = adapterView.getItemAtPosition(position)
                        .toString()
                }
            }

        }
        viewModel.departmentList.observe(viewLifecycleOwner) {
            if (it != null) {
                val departments = it.categories?.map { categories ->
                    categories.name.toString()
                }?.toList()
                departmentsAdapter = ArrayAdapter(requireContext(),
                    R.layout.array_adapter_item,
                    departments!!)
                binding.departmentAutoCompelete.setAdapter(departmentsAdapter)
                binding.departmentAutoCompelete.setOnItemClickListener { adapterView, _, position, _ ->
                    Toast.makeText(requireContext(),
                        it.categories[position].name,
                        Toast.LENGTH_SHORT).show()
                    departmentId = it.categories[position].id.toString()
                    binding.companyAutoCompelete.hint = adapterView.getItemAtPosition(position)
                        .toString()
                }
            }

        }
        viewModel.workingHours.observe(viewLifecycleOwner) {
            if (it != null) {
                val workingHours = it.map { hours ->
                    hours.toString()
                }.toList()
                workingHoursAdapter = ArrayAdapter(requireContext(),
                    R.layout.array_adapter_item,
                    workingHours)
                binding.workHoursCompelete.setAdapter(workingHoursAdapter)
                binding.workHoursCompelete.setOnItemClickListener { adapterView, _, position, _ ->
                    Toast.makeText(requireContext(),
                        workingHours[position],
                        Toast.LENGTH_SHORT).show()
                    binding.companyAutoCompelete.hint = adapterView.getItemAtPosition(position)
                        .toString()
                }
            }

        }
        binding.addJobBtn.setOnClickListener {
            validateApplicable()
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    loadingProgressbar.visibility = View.VISIBLE
                    binding.addJobBtn.visibility = View.GONE
                }
            } else {
                binding.apply {
                    loadingProgressbar.visibility = View.GONE
                    binding.addJobBtn.visibility = View.VISIBLE
                }
            }
        }
        viewModel.exception.observe(viewLifecycleOwner) {
            when (it) {
                null -> {
                }
                200 -> {
                    requireView().findNavController().popBackStack()
                    Toast.makeText(requireContext(), "تم اضافة الوظيفة بنجاح", Toast.LENGTH_SHORT)
                        .show()

                }
                401 -> {
                    Toast.makeText(requireContext(),
                        "برجاء تسجيل الدخول أولا حتي تتمكن من إضافة وظائف",
                        Toast.LENGTH_SHORT).show()
                }
                402 -> {
                    Toast.makeText(requireContext(),
                        "برجاء التحويل الي الباقة المدفوعة لمعرفة تفاصيل أكثر",
                        Toast.LENGTH_SHORT).show()
                }
                404 -> {
                    Toast.makeText(requireContext(),
                        "لا توجد متقدمين للتوظيف بعد",
                        Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(),
                        "تعذر إضافة الوظيفة, برجاء المحاولة مرة أخري",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }

    private fun validateApplicable() {
        name = binding.companyAutoCompelete.text.toString().trim()
        jobNaming = binding.jobNameingEt.text.toString().trim()
        description = binding.jobDescEt.text.toString().trim()
        place = binding.placeEt.text.toString().trim()
        workHours = binding.workHoursCompelete.text.toString().trim()
        salary = binding.salaryEt.text.toString().trim()
        experience = binding.experienceTv.text.toString().trim()

        if (name.isEmpty() && departmentId.isEmpty() && jobNaming.isEmpty() && salary.isEmpty() && experience.isEmpty() && description.isEmpty() && place.isEmpty() && workHours.isEmpty()) {
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

        } else if (departmentId.isEmpty()) {
            binding.departmentAutoCompelete.apply {
                requestFocus()
                error = "برجاء ادخال القسم"
            }

        } else if (jobNaming.isEmpty()) {
            binding.jobNameingEt.apply {
                requestFocus()
                error = "برجاء ادخال المسمي الوظيفي"
            }
        } else if (salary.isEmpty()) {
            binding.salaryEt.apply {
                requestFocus()
                error = "برجاء ادخال الراتب"
            }
        } else if (experience.isEmpty()) {
            binding.experienceTv.apply {
                requestFocus()
                error = "برجاء ادخال الخبرة"
            }
        } else if (description.isEmpty()) {
            binding.jobDescEt.apply {
                requestFocus()
                error = "برجاء ارفاق الوصف الوظيفي"
            }

        } else if (place.isEmpty()) {
            binding.placeEt.apply {
                requestFocus()
                error = "برجاء ارفاق العنوان"
            }
        } else if (workHours.isEmpty()) {
            binding.workHoursCompelete.apply {
                requestFocus()
                error = "برجاء ادخال الدوام"
            }
        } else {
            viewModel.addNewJob(jobNaming,
                description,
                salary,
                place,
                experience,
                departmentId.toLong(),
                companyId.toLong(),
                workHours)
        }
    }

}