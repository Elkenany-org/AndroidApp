package com.elkenany.views.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentNotificationBinding
import com.elkenany.entities.home_data.Nots
import com.elkenany.viewmodels.NotificationViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.home.HomeFragmentDirections
import com.elkenany.views.notification.adapter.NotificationListAdapter


class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: NotificationViewModel
    private lateinit var notificationAdapter: NotificationListAdapter

    override fun onResume() {
        super.onResume()
        viewModel.onGettingNotificationData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[NotificationViewModel::class.java]
//        viewModel.onGettingNotificationData()
        notificationAdapter = NotificationListAdapter(ClickListener {
            onNotificationClickedToNavigate(it, it.title!!)
        })
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
                if (it.notifications.isNotEmpty()) {
                    binding.notificationRecyclerView.visibility = View.VISIBLE
                    binding.loginRequiredMessage.visibility = View.GONE
                    binding.errorMessage.visibility = View.GONE
                    notificationAdapter.submitList(it.notifications)
                } else {
                    binding.notificationRecyclerView.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.errorMessage.text = "لا توجد لديك إشعارات جديدة"
                }

            } else {
                binding.notificationRecyclerView.visibility = View.GONE
                binding.errorMessage.setText(R.string.failed_to_receive_data_msg)
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

    private fun onNotificationClickedToNavigate(it: Nots, title: String) {
        when (it.keyName) {
            "news" -> {
                requireView().findNavController().navigate(
                    NotificationFragmentDirections.actionNotificationFragmentToNewsDetailsFragment(
                        it.keyId!!.toInt(),
                    )
                )
            }
            "companies" -> {
                requireView().findNavController().navigate(
                    NotificationFragmentDirections.actionNotificationFragmentToCompanyFragment(
                        it.keyId!!.toLong(),
                        title
                    )
                )
            }
            "showes" -> requireView().findNavController()
                .navigate(NotificationFragmentDirections.actionNotificationFragmentToShowsDetailsFragment(
                    it.keyId!!,
                    it.keyName
                ))
            "magazines" -> requireView().findNavController()
                .navigate(NotificationFragmentDirections.actionNotificationFragmentToGuideMagazineDetailsFragment(
                    it.keyId!!))
            else -> Toast.makeText(requireContext(), "حدوث خطأ في عملية العرض", Toast.LENGTH_SHORT)
                .show()
        }
    }
}