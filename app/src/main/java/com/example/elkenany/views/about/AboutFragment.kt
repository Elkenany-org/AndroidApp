package com.example.elkenany.views.about

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentAboutBinding
import com.example.elkenany.viewmodels.AboutViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.about.adapter.OfficesAdapter


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
        officeAdapter = OfficesAdapter(ClickListener { })
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
        viewModel.about.observe(viewLifecycleOwner) {
            if (it != null) {
                @Suppress("DEPRECATION")
                val aboutDataEncoded = Html.fromHtml(it.about).toString()
                binding.apply {
                    aboutCard.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                    aboutTv.text = aboutDataEncoded
                }
            } else {
                binding.apply {
                    errorMessage.visibility = View.VISIBLE
                    aboutCard.visibility = View.GONE
                }
            }
        }

        return binding.root
    }

}