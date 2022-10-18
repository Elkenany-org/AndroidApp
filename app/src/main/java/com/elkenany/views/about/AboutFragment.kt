package com.elkenany.views.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentAboutBinding
import com.elkenany.utilities.GlobalUiFunctions.Companion.callThisNumber
import com.elkenany.utilities.GlobalUiFunctions.Companion.emailThisEmail
import com.elkenany.utilities.GlobalUiFunctions.Companion.locateThisLocation
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
            callThisNumber(it.phones[0]!!.phone, requireContext(), requireActivity())
        }, ClickListener {
            // handeling email function
            emailThisEmail(it.emails[0]!!.email, requireActivity())
        }, ClickListener {
            // handeling location function
            locateThisLocation(it.latitude!!, it.longitude!!, requireActivity())
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

}