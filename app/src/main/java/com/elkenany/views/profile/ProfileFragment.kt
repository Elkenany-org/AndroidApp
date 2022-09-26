package com.elkenany.views.profile

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.elkenany.R
import com.elkenany.databinding.FragmentProfileBinding
import com.elkenany.viewmodels.ProfileViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.home.HomeFragmentDirections


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ProfileViewModel
    private lateinit var parentNavController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        parentNavController = requireParentFragment().requireParentFragment().findNavController()
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]
        binding.profileLayout.apply {
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }
        binding.signInBtn.setOnClickListener {
            viewModel.signIn()
        }
        binding.signOutBtn.setOnClickListener {
            basicAlert(requireContext(), requireActivity())
        }
        binding.adsBtn.setOnClickListener {
            requireView().findNavController()
                .navigate(ProfileFragmentDirections.actionProfileFragmentToMyAdsListFragment())
        }
        viewModel.userData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.profileLayout.visibility = View.VISIBLE
                binding.signInBtn.visibility = View.GONE
                binding.nameText.text = it.name
                binding.emailText.text = it.email
                binding.url = it.image
            } else {
                binding.profileLayout.visibility = View.GONE
                binding.signInBtn.visibility = View.VISIBLE

            }
        }
        viewModel.loggedOut.observe(viewLifecycleOwner) {
            if (it) {
                parentNavController.navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        }

        return binding.root
    }

    private fun basicAlert(context: Context, activity: Activity) {

        val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom))

        with(builder)
        {
            setMessage("هل تريد تسجيل الخروج ؟ ")
            setPositiveButton("تسجيل خروج") { _, _ ->
                viewModel.signOutFromGoogle(context, activity)
            }
            setNegativeButton("الغاء", null)
            show()
        }
    }
}