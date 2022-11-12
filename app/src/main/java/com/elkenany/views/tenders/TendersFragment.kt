package com.elkenany.views.tenders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentTendersBinding
import com.elkenany.viewmodels.TendersViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.tenders.adapters.TendersSubSectionsAdapter

class TendersFragment : Fragment() {
    private lateinit var binding: FragmentTendersBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: TendersViewModel
    private lateinit var tenderAdapter: TendersSubSectionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tenders, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[TendersViewModel::class.java]
        tenderAdapter = TendersSubSectionsAdapter(ClickListener {
            requireView().findNavController()
                .navigate(
                    TendersFragmentDirections.actionTendersFragmentToTendersSubSectionsFragment(
                        it.id!!.toLong()
                    )
                )
        })
        binding.tendersSectionsRecyclerView.adapter = tenderAdapter

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
                null -> {}
                200 -> {
                    binding.errorMessage.visibility = View.GONE
                }
                401 -> {
                    Toast.makeText(
                        requireContext(),
                        "برجاء تسجيل الدخول أولا حتي تتمكن من معرفة تفاصيل المناقصات",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                402 -> {
                    binding.errorMessage.text =
                        "برجاء التحويل الي الباقة المدفوعة لمعرفة تفاصيل أكثر"
                    binding.errorMessage.visibility = View.VISIBLE
                }
                404 -> {
                    binding.errorMessage.text =
                        "لا توجد أعلانات مناقصات بعد"
                    binding.errorMessage.visibility = View.VISIBLE
                }
                else -> {
                    binding.errorMessage.text =
                        "تعذر الحصول علي المعلومات"
                    binding.errorMessage.visibility = View.VISIBLE
                }
            }
        }
        viewModel.responseData.observe(viewLifecycleOwner) { tendersData ->
            if (tendersData != null) {
                tenderAdapter.submitList(tendersData.sections)
            }
        }

        return binding.root
    }

}