package com.elkenany.views.about

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
import com.elkenany.databinding.FragmentSponsersBinding
import com.elkenany.utilities.GlobalUiFunctions
import com.elkenany.viewmodels.SponsersViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.about.adapter.SponsersAdapter


class SponsersFragment : Fragment() {
    private lateinit var binding: FragmentSponsersBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SponsersViewModel
    private lateinit var sponserAdapter: SponsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sponsers, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[SponsersViewModel::class.java]
        sponserAdapter = SponsersAdapter(ClickListener {
            when (it.type) {
                "internal" -> requireView().findNavController()
                    .navigate(SponsersFragmentDirections.actionSponsersFragmentToCompanyFragment(
                        it.companyId!!.toLong(),
                        it.companyName!!))
                else -> GlobalUiFunctions.navigateToBroswerIntent(it.link, requireActivity())
            }
        })
        binding.sponsersRecyclerView.adapter = sponserAdapter
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    loadingProgressbar.visibility = View.VISIBLE
                    sponsersRecyclerView.visibility = View.GONE
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
        viewModel.responseData.observe(viewLifecycleOwner) { sponsersListData ->
            if (sponsersListData != null) {
                binding.sponsersRecyclerView.visibility = View.VISIBLE
                sponserAdapter.submitList(sponsersListData.logos)
            }
        }
        return binding.root
    }

}