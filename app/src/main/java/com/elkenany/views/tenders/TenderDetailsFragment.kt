package com.elkenany.views.tenders

import android.annotation.SuppressLint
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
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentTenderDetailsBinding
import com.elkenany.viewmodels.TendersDetailsViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.tenders.adapters.MoreTendersAdapter

class TenderDetailsFragment : Fragment() {
    private lateinit var binding: FragmentTenderDetailsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewmodel: TendersDetailsViewModel
    private lateinit var moreTendersAdapter: MoreTendersAdapter
    private val args: TenderDetailsFragmentArgs by navArgs()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tender_details, container, false)
        viewModelFactory = ViewModelFactory()
        viewmodel = ViewModelProvider(this, viewModelFactory)[TendersDetailsViewModel::class.java]

        moreTendersAdapter = MoreTendersAdapter(ClickListener {
            requireView().findNavController()
                .navigate(TenderDetailsFragmentDirections.actionTenderDetailsFragmentSelf(it.id!!))
        })
        binding.moreNewsRecyclerView.adapter = moreTendersAdapter
        binding.webView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
        }
        viewmodel.getAllTendersDetailsData(args.tenderId)
        viewmodel.loading.observe(viewLifecycleOwner) {
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
        viewmodel.exception.observe(viewLifecycleOwner) {
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
        viewmodel.responseData.observe(viewLifecycleOwner) { tendersData ->
            if (tendersData != null) {
                moreTendersAdapter.submitList(tendersData.tenders)
                binding.data = tendersData
                binding.newsImage = tendersData.image
                binding.newsDate = tendersData.createdAt
                binding.mainTitle = tendersData.title
                binding.webView.loadData(
                    "<html dir=\"rtl\"><style>body{font-size: 14pt;color:green;} p{text-align: justify;}</style><body><div>${tendersData.desc!!}</div></body></html>",
                    "text/html",
                    "UTF-8"
                )
            }
        }
        return binding.root
    }

}