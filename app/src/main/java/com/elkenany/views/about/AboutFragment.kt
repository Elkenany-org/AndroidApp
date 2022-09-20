package com.elkenany.views.about

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
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentAboutBinding
import com.elkenany.viewmodels.AboutViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.about.adapter.OfficesAdapter


class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AboutViewModel
    private lateinit var officeAdapter: OfficesAdapter

    override fun onResume() {
        super.onResume()
        viewModel.onGettingFromBackEnd()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[AboutViewModel::class.java]
        officeAdapter = OfficesAdapter(ClickListener {
            // handeling call function
            callThisNumber(it.phones[0]!!.phone)
        }, ClickListener {
            // handeling email function
            emailThisEmail(it.emails[0]!!.email)
        }, ClickListener {
            // handeling location function
            locateThisLocation(it.latitude!!, it.longitude!!)
        })
        binding.branchesRecyclerView.adapter = officeAdapter
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    loadingProgressbar.visibility = View.VISIBLE
                    aboutCard.visibility = View.GONE
                    branchesRecyclerView.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                }
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        viewModel.contactUs.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    aboutCard.visibility = View.VISIBLE
                    aboutTv.text =
                        getString(R.string.about_company_data)
                    branchesRecyclerView.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                    officeAdapter.submitList(it.offices)
                }
            } else {
                binding.apply {
                    errorMessage.visibility = View.VISIBLE
                    branchesRecyclerView.visibility = View.GONE
                }
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