package com.elkenany.views.guide

import android.os.Bundle
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
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentCompanyBinding
import com.elkenany.utilities.GlobalUiFunctions.Companion.callThisNumber
import com.elkenany.utilities.GlobalUiFunctions.Companion.emailThisEmail
import com.elkenany.utilities.GlobalUiFunctions.Companion.locateThisLocation
import com.elkenany.utilities.GlobalUiFunctions.Companion.openPopUpImage
import com.elkenany.viewmodels.CompanyViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.guide.adapter.CompanyLocalStockAdapter
import com.elkenany.views.guide.adapter.GalleryAdapter


class CompanyFragment : Fragment() {
    private lateinit var binding: FragmentCompanyBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewmodel: CompanyViewModel
    private lateinit var companyLocalStockAdapter: CompanyLocalStockAdapter
    private lateinit var companyFodderStockAdapter: CompanyLocalStockAdapter
    private lateinit var galleryAdapter: GalleryAdapter
    private val args: CompanyFragmentArgs by navArgs()
    private lateinit var latid: String
    private lateinit var longtid: String
//    override fun onResume() {
//        super.onResume()
//    }

    override fun onStart() {
        super.onStart()
        viewmodel.getCompaniesGuideData(args.companyId)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_company, container, false)
        viewModelFactory = ViewModelFactory()
        viewmodel = ViewModelProvider(this, viewModelFactory)[CompanyViewModel::class.java]
        binding.appBarTitle.text = args.companyName
        binding.location.setOnClickListener {
            locateThisLocation(latid, longtid, requireActivity())
        }
        binding.apply {
            phone.setOnClickListener {
                callThisNumber(binding.phone.text.toString(), requireContext(), requireActivity())
            }
            phone1.setOnClickListener {
                callThisNumber(binding.phone1.text.toString(), requireContext(), requireActivity())
            }
            phone2.setOnClickListener {
                callThisNumber(binding.phone2.text.toString(), requireContext(), requireActivity())
            }
            phone3.setOnClickListener {
                callThisNumber(binding.phone3.text.toString(), requireContext(), requireActivity())
            }

            mail.setOnClickListener {
                emailThisEmail(binding.mail.text.toString(), requireActivity())
            }
            fax.setOnClickListener {
                callThisNumber(binding.fax.text.toString(), requireContext(), requireActivity())
            }
        }

        companyLocalStockAdapter = CompanyLocalStockAdapter(ClickListener {
            navigateToStockPage(
                it.id!!,
                it.name.toString(),
                "local"
            )
        })
        binding.companyLocalStocksRecyclerview.adapter = companyLocalStockAdapter
        companyFodderStockAdapter = CompanyLocalStockAdapter(ClickListener {
            navigateToStockPage(
                it.id!!,
                it.name.toString(),
                "fodder"
            )
        })
        binding.companyFodderStocksRecyclerview.adapter = companyFodderStockAdapter
        viewmodel.getCompaniesGuideData(args.companyId)
        viewmodel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.adsLayout.visibility = View.GONE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        binding.ratingBar.setOnRatingBarChangeListener { rating, _, _ ->
            viewmodel.rateThisCompany(rating.rating.toLong(), args.companyId)
            Log.i("rating", rating.rating.toString() + args.companyId.toString())
        }
        galleryAdapter = GalleryAdapter(ClickListener {
            openPopUpImage(it.image, requireActivity(), layoutInflater)
        })
        binding.companyGallerysRecyclerview.adapter = galleryAdapter
        viewmodel.exception.observe(viewLifecycleOwner) {
            when (it) {
                200 -> Toast.makeText(requireContext(), "تم التقييم بنجاح", Toast.LENGTH_SHORT)
                    .show()
                401 -> Toast.makeText(requireContext(),
                    "برجاء تسجيل الدخول أولا حتي تتمكن من تقييم الشركة",
                    Toast.LENGTH_SHORT).show()

                else -> Toast.makeText(requireContext(),
                    "تعذر تقييم الشركة",
                    Toast.LENGTH_SHORT).show()
            }
        }
        viewmodel.companyData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.localstock.isEmpty() && it.fodderstock.isEmpty()) {
                    binding.cardView4.visibility = View.GONE
                } else {
                    binding.cardView4.visibility = View.VISIBLE
                    if (it.localstock.isEmpty()) {
                        binding.companyLocalStocksRecyclerview.visibility = View.GONE
                    } else {
                        binding.companyLocalStocksRecyclerview.visibility = View.VISIBLE
                        companyLocalStockAdapter.submitList(it.localstock)
                    }
                    if (it.fodderstock.isEmpty()) {
                        binding.companyFodderStocksRecyclerview.visibility = View.GONE
                    } else {
                        binding.companyFodderStocksRecyclerview.visibility = View.VISIBLE
                        companyFodderStockAdapter.submitList(it.fodderstock)
                    }
                }
                if (it.gallary.isEmpty()) {
                    binding.cardView5.visibility = View.GONE
                } else {
                    binding.cardView5.visibility = View.VISIBLE
                    galleryAdapter.submitList(it.gallary)
                }
                binding.errorMessage.visibility = View.GONE
                binding.adsLayout.visibility = View.VISIBLE
                binding.apply {
                    rateUsers = "( ${it.countRate.toString()} )"
                    data = it
                    latid = it.latitude!!
                    longtid = it.longitude!!
                }
            } else {
                binding.adsLayout.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
            }
        }
        return binding.root
    }

    private fun navigateToStockPage(id: Long, name: String, type: String) {
        requireView().findNavController()
            .navigate(
                CompanyFragmentDirections.actionCompanyFragmentToLocalStockDetailsFragment(
                    id,
                    name,
                    type
                )
            )
    }
}