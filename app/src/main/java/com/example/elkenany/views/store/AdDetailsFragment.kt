package com.example.elkenany.views.store

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentAdDetailsBinding
import com.example.elkenany.viewmodels.AdDetailsViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.store.adapter.AdsImagesAdapter


class AdDetailsFragment : Fragment() {

    private lateinit var binding: FragmentAdDetailsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AdDetailsViewModel
    private lateinit var adsImagesAdapter: AdsImagesAdapter
    private val args: AdDetailsFragmentArgs by navArgs()
    override fun onResume() {
        super.onResume()
        viewModel.getAdDetailsData(args.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ad_details, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[AdDetailsViewModel::class.java]
//        viewModel.getAdDetailsData(args.id)
        binding.chattingBtn.setOnClickListener {
//            viewModel.startChat(args.id)
            callThisNumber(binding.adPhone.text.toString())
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.adsLayout.visibility = View.GONE
                binding.loadingProgressbar.visibility = View.VISIBLE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        adsImagesAdapter = AdsImagesAdapter(ClickListener {
            binding.clickable = false
            binding.imagePopUpLayout.layoutAnimation =
                AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation)
            binding.imagePopUpLayout.visibility = View.VISIBLE
            binding.scrollView.setScrollingEnabled(false)
            binding.image = it.image
        })
        binding.imageButton.setOnClickListener {
            binding.imagePopUpLayout.visibility = View.GONE
            binding.scrollView.setScrollingEnabled(true)
            binding.clickable = true
        }
        binding.moreImagesRecyclerview.adapter = adsImagesAdapter
        binding.adPhone.setOnClickListener { callThisNumber(binding.adPhone.text.toString()) }
        viewModel.adDetailsData.observe(viewLifecycleOwner)
        {
            if (it != null) {
                binding.apply {
                    adsLayout.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                    data = it
                    adsImagesAdapter.submitList(it.images)
                }

            } else {
                binding.apply {
                    adsLayout.visibility = View.GONE
                    errorMessage.visibility = View.VISIBLE
                }
            }
        }
        viewModel.startChatData.observe(viewLifecycleOwner)
        {
            if (it != null) {
                requireView().findNavController()
                    .navigate(AdDetailsFragmentDirections.actionAdDetailsFragmentToMessagesFragment(
                        it.id!!,
                        null))
            } else {
                binding.chattingBtn.text = "كلم البائع"
            }
        }
        viewModel.exception.observe(viewLifecycleOwner)
        {
            if (it == 401) {
                Toast.makeText(requireContext(), "برجاء تسجيل الدخول أولا", Toast.LENGTH_SHORT)
                    .show()
            } else if (it == 100) {
                binding.chattingBtn.text = "برجاء الانتظار"
            } else {
                binding.chattingBtn.text = "كلم البائع"
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

}