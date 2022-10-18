package com.elkenany.views.store

import android.app.Dialog
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
import com.elkenany.databinding.FragmentAdDetailsBinding
import com.elkenany.databinding.ImageDialogItemBinding
import com.elkenany.utilities.GlobalUiFunctions.Companion.callThisNumber
import com.elkenany.viewmodels.AdDetailsViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.store.adapter.AdsImagesAdapter


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
            callThisNumber(binding.adPhone.text.toString(), requireContext(), requireActivity())
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
            openImageDialog(it.image.toString())
        })
        binding.imageButton.setOnClickListener {
            binding.imagePopUpLayout.visibility = View.GONE
            binding.scrollView.setScrollingEnabled(true)
        }
        binding.moreImagesRecyclerview.adapter = adsImagesAdapter
        binding.adPhone.setOnClickListener {
            callThisNumber(binding.adPhone.text.toString(),
                requireContext(),
                requireActivity())
        }
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
                    .navigate(
                        AdDetailsFragmentDirections.actionAdDetailsFragmentToMessagesFragment(
                            it.id!!,
                            null
                        )
                    )
            } else {
                binding.chattingBtn.text = "كلم البائع"
            }
        }
        viewModel.exception.observe(viewLifecycleOwner)
        {
            when (it) {
                401 -> {
                    Toast.makeText(requireContext(), "برجاء تسجيل الدخول أولا", Toast.LENGTH_SHORT)
                        .show()
                }
                100 -> {
                    binding.chattingBtn.text = "برجاء الانتظار"
                }
                else -> {
                    binding.chattingBtn.text = "كلم البائع"
                }
            }
        }
        return binding.root
    }

    private fun openImageDialog(image: String) {
        val dialogBinding = ImageDialogItemBinding.inflate(layoutInflater)
        dialogBinding.image = image
        val dialog = Dialog(requireActivity())
        dialog.setCancelable(true)
        Log.i("imageUrl", dialogBinding.image.toString())
        dialog.setContentView(dialogBinding.root)
        dialog.show()

    }
}