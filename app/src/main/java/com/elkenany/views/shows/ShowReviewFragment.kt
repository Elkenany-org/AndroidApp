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
import com.elkenany.databinding.FragmentShowReviewBinding
import com.elkenany.viewmodels.ShowReviewViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.shows.adapter.ReviewersAdapter

class ShowReviewFragment : Fragment() {
    private lateinit var binding: FragmentShowReviewBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewmodel: ShowReviewViewModel
    private lateinit var reviewersAdapter: ReviewersAdapter
    private val args: ShowReviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_show_review, container, false)
        viewModelFactory = ViewModelFactory()
        viewmodel = ViewModelProvider(this, viewModelFactory)[ShowReviewViewModel::class.java]
        viewmodel.getShowDetailsData(args.showId)
        reviewersAdapter = ReviewersAdapter(ClickListener { })
        binding.reviewrsListRecyclerView.adapter = reviewersAdapter
        viewmodel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    reviewrsListRecyclerView.visibility = View.GONE
                    loadingProgressbar.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                }
            } else {
                binding.apply {
                    loadingProgressbar.visibility = View.GONE
                }
            }
        }

        viewmodel.reviewerDetails.observe(viewLifecycleOwner) {
            Log.i("ShowsDetailsData", args.showId.toString() + it.toString())
            if (it != null) {
                binding.apply {
                    reviewrsListRecyclerView.visibility = View.VISIBLE
                    reviewersAdapter.submitList(it.review)
                    errorMessage.visibility = View.GONE
                    data = it
                    if (it.review.isEmpty()) {
                        errorMessage.visibility = View.VISIBLE
                        errorMessage.text = "عفوا لا توجد تقييمات"
                    }
                }
            } else {
                binding.apply {
                    reviewrsListRecyclerView.visibility = View.GONE
                    errorMessage.visibility = View.VISIBLE
                    errorMessage.text = R.string.failed_to_receive_data_msg.toString()
                }
            }
        }
        return binding.root
    }

}