package com.elkenany.views.shows

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentShowsDetailsBinding
import com.elkenany.viewmodels.ShowsDetailsViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.shows.adapter.ShowsImageAdapter

class ShowsDetailsFragment : Fragment() {
    private lateinit var binding: FragmentShowsDetailsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewmodel: ShowsDetailsViewModel
    private lateinit var showsImageAdapter: ShowsImageAdapter
    private val args: ShowsDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_shows_details,
            container,
            false)
        viewModelFactory = ViewModelFactory()
        viewmodel = ViewModelProvider(this, viewModelFactory)[ShowsDetailsViewModel::class.java]
        viewmodel.getShowDetailsData(args.id)
        binding.appBarTitle.text = args.name
        showsImageAdapter = ShowsImageAdapter(ClickListener { })
        binding.moreImagesRecyclerview.adapter = showsImageAdapter

        //viewModel observers
        viewmodel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    scrollView.visibility = View.GONE
                    loadingProgressbar.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                }
            } else {
                binding.apply {
                    loadingProgressbar.visibility = View.GONE
                }
            }
        }

        viewmodel.showsDetails.observe(viewLifecycleOwner) {
            Log.i("ShowsDetailsData", args.id.toString() + it.toString())
            if (it != null) {
                binding.apply {
                    scrollView.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                    showsImageAdapter.submitList(it.images)
                    data = it
                    intrestedPeople = "المشاركون ${it.viewCount}"
                }
            } else {
                binding.apply {
                    scrollView.visibility = View.GONE
                    errorMessage.visibility = View.VISIBLE
                }
            }
        }


        return binding.root
    }

}