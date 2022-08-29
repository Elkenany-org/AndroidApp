package com.example.elkenany.views.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentMainMenuBinding
import com.example.elkenany.viewmodels.MainMenuViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.home.HomeFragmentDirections

const val SHARE_LINK = "https://play.google.com/store/apps/details?id=com.Elkenany"

class MainMenuFragment : Fragment() {
    private lateinit var binding: FragmentMainMenuBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainMenuViewModel
    private lateinit var parentNavController: NavController
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
            viewModel.signOutFromGoogle(requireContext())

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


        // TODO --> impelement it in phase two
        binding.showsBtn.visibility = View.GONE
        // TODO --> impelement it in phase two
        binding.magazineBtn.visibility = View.GONE


        binding.chatBtn.setOnClickListener {
            requireView().findNavController().navigate(R.id.chatsFragment)
        }
        binding.aboutBtn.setOnClickListener {
            requireView().findNavController().navigate(R.id.aboutFragment)
        }
        binding.shareBtn.setOnClickListener {
            shareSuccess()
        }
        binding.rateBtn.setOnClickListener {
            // ToDo -> implement rateFunction here
            Toast.makeText(requireContext(), "لم يتم تفعيل هذه الخدمة بعد", Toast.LENGTH_SHORT)
                .show()
        }

        binding.notificationBtn.setOnClickListener {
            requireView().findNavController()
                .navigate(MainMenuFragmentDirections.actionMainMenuFragmentToNotificationFragment2())
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

    private fun onsharingdata(): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT,
                "تطبيق الكناني أول تطبيق خدمي في المجال البيطري والزراعي\n$SHARE_LINK")
        }
        return shareIntent
    }

    private fun shareSuccess() {
        startActivity(onsharingdata())
    }
}