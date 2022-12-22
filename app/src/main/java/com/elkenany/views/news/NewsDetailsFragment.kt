package com.elkenany.views.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentNewsDetailsBinding
import com.elkenany.viewmodels.NewsDetailsViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.news.adapter.NewsDaumAdapter


class NewsDetailsFragment : Fragment() {
    private lateinit var binding: FragmentNewsDetailsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: NewsDetailsViewModel
    private lateinit var moreNewsAdapter: NewsDaumAdapter
    private val args: NewsDetailsFragmentArgs by navArgs()
//    override fun onResume() {
//        super.onResume()
//        viewModel.getAllNewsData(args.id)
//    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsDetailsViewModel::class.java]
        viewModel.getAllNewsData(args.id)
        binding.webView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
        }

        moreNewsAdapter = NewsDaumAdapter(ClickListener {
//            viewModel.getAllNewsData(it.id!!.toInt())
            requireView().findNavController()
                .navigate(NewsDetailsFragmentDirections.actionNewsDetailsFragmentSelf(it.id!!.toInt()))
        })
        binding.moreNewsRecyclerView.apply {
            adapter = moreNewsAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.newsLayout.visibility = View.GONE
                binding.loadingProgressbar.visibility = View.VISIBLE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        viewModel.newsDetails.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.errorMessage.visibility = View.GONE
                binding.newsLayout.visibility = View.VISIBLE
                binding.newsDate = it.createdAt.toString()
                binding.mainTitle = it.title.toString()
                binding.newsImage = it.image
                binding.webView.loadData("<html dir=\"rtl\"><style>body{font-size: 14pt;color:green;} p{text-align: justify;}</style><body><div>${it.desc!!}</div></body></html>",
                    "text/html",
                    "UTF-8")
                if (it.news.isNotEmpty()) {
                    binding.moreNewsTv.visibility = View.VISIBLE
                    moreNewsAdapter.submitList(it.news)
                    binding.moreNewsRecyclerView.visibility = View.VISIBLE
                    binding.noMoreNewsMessage.visibility = View.GONE
                } else {
                    binding.moreNewsTv.visibility = View.GONE
                    binding.moreNewsRecyclerView.visibility = View.GONE
                    binding.noMoreNewsMessage.visibility = View.VISIBLE
                }
            } else {
                binding.newsLayout.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
            }
        }
        return binding.root
    }

}