package com.example.elkenany.views.guide

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    private lateinit var latid: String
    private lateinit var longtid: String
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
        binding.location.setOnClickListener {
            locateThisLocation(latid, longtid)
        }
        binding.phone.setOnClickListener {
            callThisNumber(binding.phone.text.toString())
        }
        binding.mail.setOnClickListener {
            emailThisEmail(binding.mail.text.toString())
        }
        binding.fax.setOnClickListener {
            callThisNumber(binding.fax.text.toString())
        }
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

    private fun callThisNumber(phone: String?) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phone")
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.CALL_PHONE), 1)
        } else {
            startActivity(callIntent)
        }

    }

    private fun emailThisEmail(email: String?) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "plain/text"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email)
        startActivity(emailIntent)
    }

    private fun locateThisLocation(latid: String, longtid: String) {
        val gmmIntentUri = Uri.parse("google.navigation:q=${latid},${longtid}");
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent)
    }
}