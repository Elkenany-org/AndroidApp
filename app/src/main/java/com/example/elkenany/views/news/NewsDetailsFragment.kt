package com.example.elkenany.views.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentNewsDetailsBinding
import com.example.elkenany.viewmodels.NewsDetailsViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.news.adapter.NewsDaumAdapter


class NewsDetailsFragment : Fragment() {
    private lateinit var binding: FragmentNewsDetailsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: NewsDetailsViewModel
    private lateinit var moreNewsAdapter: NewsDaumAdapter
    private val args: NewsDetailsFragmentArgs by navArgs()

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
        binding.newsDate = args.id.toString()
        binding.mainTitle = args.id.toString()
        binding.newsImage = args.id.toString()
        binding.webView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
        }

        moreNewsAdapter = NewsDaumAdapter(ClickListener {
            viewModel.getAllNewsData(it.id!!.toInt())
        })
        binding.moreNewsRecyclerView.adapter = moreNewsAdapter
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
                binding.webView.loadData("<html><body><div>${it.desc!!}</div></body></html>",
                    "text/html",
                    "UTF-8")
                if (it.news.isNotEmpty()) {
                    moreNewsAdapter.submitList(it.news)
                    binding.moreNewsRecyclerView.visibility = View.VISIBLE
                    binding.noMoreNewsMessage.visibility = View.GONE
                } else {
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