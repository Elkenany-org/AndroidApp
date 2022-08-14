package com.example.elkenany.views.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentNotificationBinding
import com.example.elkenany.viewmodels.NotificationViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.home.HomeFragmentDirections
import com.example.elkenany.views.notification.adapter.NotificationListAdapter


class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: NotificationViewModel
    private lateinit var notificationAdapter: NotificationListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[NotificationViewModel::class.java]
        viewModel.onGettingNotificationData()
        notificationAdapter = NotificationListAdapter(ClickListener { })
        binding.notificationRecyclerView.apply {
            adapter = notificationAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }
        binding.signInBtn.setOnClickListener {
            requireParentFragment().requireParentFragment().findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
        }
        viewModel.notificationList.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.notificationRecyclerView.visibility = View.VISIBLE
                binding.loginRequiredMessage.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
                notificationAdapter.submitList(it.notifications)
            } else {
                binding.notificationRecyclerView.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
            }
        }
        viewModel.authorized.observe(viewLifecycleOwner) {
            if (it) {
                binding.loginRequiredMessage.visibility = View.GONE
                binding.signInBtn.visibility = View.GONE
            } else {
                binding.loginRequiredMessage.visibility = View.VISIBLE
                binding.signInBtn.visibility = View.VISIBLE
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.notificationRecyclerView.visibility = View.GONE
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.loginRequiredMessage.visibility = View.GONE
                binding.signInBtn.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        return binding.root
    }
}