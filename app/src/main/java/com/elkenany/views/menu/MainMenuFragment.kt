package com.elkenany.views.menu

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
import com.elkenany.databinding.FragmentMainMenuBinding
import com.elkenany.utilities.GlobalUiFunctions.Companion.navigateToGooglePlay
import com.elkenany.utilities.GlobalUiFunctions.Companion.onsharingdata
import com.elkenany.viewmodels.MainMenuViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.home.HomeFragmentDirections


class MainMenuFragment : Fragment() {
    private lateinit var binding: FragmentMainMenuBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainMenuViewModel
    private lateinit var parentNavController: NavController
    private val shareLink =
        "\"تطبيق الكناني أول تطبيق خدمي في المجال البيطري والزراعي\"https://play.google.com/store/apps/details?id=com.elkenany"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_menu, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[MainMenuViewModel::class.java]
        binding.menuFunctions.apply {
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }
        parentNavController = requireParentFragment().requireParentFragment().findNavController()

        binding.signInBtn.setOnClickListener {
            parentNavController.navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
        }
        binding.signOutBtn.setOnClickListener {
            basicAlert(requireContext())

        }
        binding.dailyStockBtn.setOnClickListener {
            requireView().findNavController().navigate(R.id.localStockFragment)
        }
        binding.guideBtn.setOnClickListener {
            requireView().findNavController().navigate(R.id.guideFragment)
        }
        binding.storeBtn.setOnClickListener {
            requireView().findNavController().navigate(R.id.storeFragment)
        }
        binding.newsBtn.setOnClickListener {
            requireView().findNavController().navigate(R.id.newsFragment)
        }

        binding.contactUsBtn.setOnClickListener {
            requireView().findNavController().navigate(R.id.aboutFragment)
        }
        binding.shareBtn.setOnClickListener {
            onsharingdata(shareLink, requireActivity())
        }
        binding.rateBtn.setOnClickListener {
            // ToDo -> implement rateFunction here
            navigateToGooglePlay("com.elkenany", requireActivity())
        }

        binding.notificationBtn.setOnClickListener {
            requireView().findNavController()
                .navigate(MainMenuFragmentDirections.actionMainMenuFragmentToNotificationFragment2())
        }
        binding.magazineBtn.setOnClickListener {
            requireView().findNavController()
                .navigate(MainMenuFragmentDirections.actionMainMenuFragmentToGuideMagazineFragment())
        }
        binding.showsBtn.setOnClickListener {
            requireView().findNavController()
                .navigate(MainMenuFragmentDirections.actionMainMenuFragmentToShowsFragment())
        }
        viewModel.userAuth.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.image = it.image
                binding.name = it.name
                binding.email = it.email
                binding.signInBtn.visibility = View.GONE
                binding.signOutBtn.visibility = View.VISIBLE
            } else {
                binding.signInBtn.visibility = View.VISIBLE
                binding.signOutBtn.visibility = View.GONE
            }
        }
        viewModel.loggedOut.observe(viewLifecycleOwner) {
            if (it) {
                parentNavController.navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        }

        return binding.root
    }

    private fun basicAlert(context: Context) {

        val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom))

        with(builder)
        {
            setMessage("هل تريد تسجيل الخروج ؟ ")
            setPositiveButton("تسجيل خروج") { _, _ ->
                viewModel.signOutFromGoogle(context, requireActivity())
            }
            setNegativeButton("الغاء", null)
            show()
        }
    }


}