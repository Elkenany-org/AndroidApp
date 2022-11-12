package com.elkenany.views.recruitment

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
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentMyFavoriteJobsBinding
import com.elkenany.viewmodels.MyFavoriteJobsViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.recruitment.adapter.FavoriteJobsAdapter


class MyFavoriteJobsFragment : Fragment() {
    private lateinit var binding: FragmentMyFavoriteJobsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MyFavoriteJobsViewModel
    private lateinit var jobsAdapter: FavoriteJobsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_favorite_jobs, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[MyFavoriteJobsViewModel::class.java]
        viewModel.getFavoritJobsListData()
        jobsAdapter = FavoriteJobsAdapter(ClickListener {
            requireView().findNavController()
                .navigate(
                    MyFavoriteJobsFragmentDirections.actionMyFavoriteJobsFragmentToJobDetailsFragment(
                        it.id!!.toInt()
                    )
                )
        },
            ClickListener {
                viewModel.addToFavorite(it.id!!.toInt())
            })
        binding.jobsListRecyclerView.adapter = jobsAdapter

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    loadingProgressbar.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                }
            } else {
                binding.apply {
                    loadingProgressbar.visibility = View.GONE
                }
            }
        }
        viewModel.exception.observe(viewLifecycleOwner) {
            Log.i("exceptionValue", it.toString())
            when (it) {
                200 -> {
                    binding.errorMessage.visibility = View.GONE
                }
                202 -> {
                    Toast.makeText(requireContext(), "تم الإزالة من المفضلة", Toast.LENGTH_SHORT)
                        .show()
                    viewModel.getFavoritJobsListData()
                }
                401 -> {
                    binding.errorMessage.text =
                        "برجاء تسجيل الدخول أولا حتي تتمكن من معرفة تفاصيل الوظائف"
                    binding.errorMessage.visibility = View.VISIBLE
                }
                402 -> {
                    binding.errorMessage.text =
                        "برجاء التحويل الي الباقة المدفوعة لمعرفة تفاصيل أكثر"
                    binding.errorMessage.visibility = View.VISIBLE
                }
                404 -> {
                    binding.errorMessage.text =
                        "لا توجد أعلانات توظيف بعد"
                    binding.errorMessage.visibility = View.VISIBLE
                }
                else -> {
                    binding.errorMessage.text =
                        "تعذر الحصول علي المعلومات"
                    binding.errorMessage.visibility = View.VISIBLE
                }
            }
        }
        viewModel.responseData.observe(viewLifecycleOwner) { jobsData ->
            if (jobsData != null) {
                jobsAdapter.submitList(jobsData.jobs)
            }
        }
        return binding.root
    }
}