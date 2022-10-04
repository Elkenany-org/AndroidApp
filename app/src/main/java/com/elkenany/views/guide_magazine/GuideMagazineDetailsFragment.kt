package com.elkenany.views.guide_magazine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                    if (it.phones.isEmpty() && it.emails.isEmpty() && it.mobiles.isEmpty() && it.faxs.isEmpty() && it.social.isEmpty()) {
                        socialMediaCard.visibility = View.GONE
                    } else {
                        socialMediaCard.visibility = View.VISIBLE
                    }
                    it.social.map { social ->
                        if (social!!.socialName == "الموقع") {
                            website = social.socialLink
                        } else if (social.socialName == "فيس بوك") {
                            facebook = social.socialLink
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