package com.elkenany.views.guide_magazine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentGuideMagazineDetailsBinding
import com.elkenany.utilities.GlobalUiFunctions
import com.elkenany.utilities.GlobalUiFunctions.Companion.callThisNumber
import com.elkenany.utilities.GlobalUiFunctions.Companion.emailThisEmail
import com.elkenany.utilities.GlobalUiFunctions.Companion.navigateToBroswerIntent
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
            emailThisEmail(binding.mail.text.toString(), requireActivity())
        }
        binding.mail1.setOnClickListener {
            emailThisEmail(binding.mail1.text.toString(), requireActivity())
        }
        binding.mail2.setOnClickListener {
            emailThisEmail(binding.mail2.text.toString(), requireActivity())
        }
        binding.phone.setOnClickListener {
            callThisNumber(binding.phone.text.toString(), requireContext(), requireActivity())
        }
        binding.phone1.setOnClickListener {
            callThisNumber(binding.phone1.text.toString(), requireContext(), requireActivity())
        }
        binding.phone2.setOnClickListener {
            callThisNumber(binding.phone2.text.toString(), requireContext(), requireActivity())
        }
        binding.phone3.setOnClickListener {
            callThisNumber(binding.phone3.text.toString(), requireContext(), requireActivity())
        }
        binding.fax.setOnClickListener {
            callThisNumber(binding.fax.text.toString(), requireContext(), requireActivity())
        }
        binding.socialFacebook.setOnClickListener {
            navigateToBroswerIntent(binding.socialFacebook.text.toString(), requireActivity())
        }
        binding.socialWebsite.setOnClickListener {
            navigateToBroswerIntent(binding.socialWebsite.text.toString(), requireActivity())
        }
        binding.rateBtn.setOnClickListener {
            GlobalUiFunctions.openRatingDialog(requireActivity(),
                inflater,
                viewLifecycleOwner,
                ClickListener {
                    viewModel.rateThisCompany(it, args.id)
                    viewModel.onDoneRating()
                },
                viewModel.processing)
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
        viewModel.exception.observe(viewLifecycleOwner) {
            when (it) {
                null -> {}
                200 -> Toast.makeText(requireContext(), "تم التقييم بنجاح", Toast.LENGTH_SHORT)
                    .show()
                401 -> Toast.makeText(requireContext(),
                    "برجاء تسجيل الدخول أولا حتي تتمكن من تقييم المجلة",
                    Toast.LENGTH_SHORT).show()

                else -> Toast.makeText(requireContext(),
                    "تعذر تقييم المجلة",
                    Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.magazineData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    errorMessage.visibility = View.GONE
                    adsLayout.visibility = View.VISIBLE
                    data = it
                    ratingBar.rating = it.rate.toFloat()
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

}