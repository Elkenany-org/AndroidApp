package com.elkenany.views.guide_magazine

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
import com.elkenany.R
import com.elkenany.databinding.FragmentGuideMagazineDetailsBinding
import com.elkenany.viewmodels.GuideMagazineDetailsViewModel
import com.elkenany.viewmodels.ViewModelFactory


class GuideMagazineDetailsFragment : Fragment() {
    private lateinit var binding: FragmentGuideMagazineDetailsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: GuideMagazineDetailsViewModel
    private val args: GuideMagazineDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_guide_magazine_details,
            container,
            false)
        viewModelFactory = ViewModelFactory()
        viewModel =
            ViewModelProvider(this, viewModelFactory)[GuideMagazineDetailsViewModel::class.java]
        viewModel.getGuideDetailsData(args.id)

        //clickListeners
        binding.mail.setOnClickListener {
            emailThisEmail(binding.mail.text.toString())
        }
        binding.mail1.setOnClickListener {
            emailThisEmail(binding.mail1.text.toString())
        }
        binding.mail2.setOnClickListener {
            emailThisEmail(binding.mail2.text.toString())
        }
        binding.phone.setOnClickListener {
            callThisNumber(binding.phone.text.toString())
        }
        binding.phone1.setOnClickListener {
            callThisNumber(binding.phone1.text.toString())
        }
        binding.phone2.setOnClickListener {
            callThisNumber(binding.phone2.text.toString())
        }
        binding.phone3.setOnClickListener {
            callThisNumber(binding.phone3.text.toString())
        }
        binding.fax.setOnClickListener {
            callThisNumber(binding.fax.text.toString())
        }
        binding.socialFacebook.setOnClickListener {
            navigateToBroswerIntent(binding.socialFacebook.text.toString())
        }
        binding.socialWebsite.setOnClickListener {
            navigateToBroswerIntent(binding.socialWebsite.text.toString())
        }

        //viewModel observers
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.adsLayout.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        viewModel.magazineData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    errorMessage.visibility = View.GONE
                    adsLayout.visibility = View.VISIBLE
                    data = it
                    rate = it.rate.toFloat()
                    rateUsers = "تقييم من ${it.countRate} عميل"
                    if (it.phones.isEmpty() && it.emails.isEmpty() && it.mobiles.isEmpty() && it.faxs.isEmpty() && it.social.isEmpty() && it.addresses.isNullOrEmpty()) {
                        socialMediaCard.visibility = View.GONE
                    } else {
                        socialMediaCard.visibility = View.VISIBLE
                        it.social.map { social ->
                            if (social!!.socialName == "الموقع") {
                                website = social.socialLink
                            } else if (social.socialName == "فيس بوك") {
                                facebook = social.socialLink
                            }
                        }
                    }

                    if (it.gallary.isEmpty()) {
                        galleryLayout.visibility = View.GONE
                    } else {
                        galleryLayout.visibility = View.VISIBLE
                    }
                }
            } else {
                binding.apply {
                    errorMessage.visibility = View.VISIBLE
                    adsLayout.visibility = View.GONE
                }
            }
        }
        return binding.root
    }

    private fun callThisNumber(phone: String?) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phone")
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CALL_PHONE), 1
            )
        } else {
            startActivity(callIntent)
        }
    }

    private fun navigateToBroswerIntent(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun emailThisEmail(email: String?) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        emailIntent.type = "message/rfc822"
        startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"))
    }
//
//    private fun locateThisLocation(latid: String, longtid: String) {
//        val gmmIntentUri = Uri.parse("google.navigation:q=${latid},${longtid}")
//        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//        mapIntent.setPackage("com.google.android.apps.maps")
//        startActivity(mapIntent)
//    }
}