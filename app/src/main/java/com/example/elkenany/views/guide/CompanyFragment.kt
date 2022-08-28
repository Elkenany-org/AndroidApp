package com.example.elkenany.views.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentCompanyBinding
import com.example.elkenany.viewmodels.CompanyViewModel
import com.example.elkenany.viewmodels.ViewModelFactory


class CompanyFragment : Fragment() {
    private lateinit var binding: FragmentCompanyBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewmodel: CompanyViewModel
    private val args: CompanyFragmentArgs by navArgs()
    override fun onResume() {
        super.onResume()
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
        viewmodel.getCompaniesGuideData(args.companyId)
        viewmodel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.adsLayout.visibility = View.GONE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        viewmodel.companyData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.errorMessage.visibility = View.GONE
                binding.adsLayout.visibility = View.VISIBLE
                binding.apply {
                    ratingBar.rating = it.rate!!.toFloat()
                    rateUsers = "( ${it.countRate.toString()} )"
                    data = it
                }
            } else {
                binding.adsLayout.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
            }
        }
        return binding.root
    }

}