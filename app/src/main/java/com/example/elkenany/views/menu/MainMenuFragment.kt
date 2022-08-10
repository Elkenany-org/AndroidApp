package com.example.elkenany.views.menu

import android.os.Bundle
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
import com.example.elkenany.R
import com.example.elkenany.api.retrofit_configs.GoogleAuth_Config.Companion.gso
import com.example.elkenany.databinding.FragmentMainMenuBinding
import com.example.elkenany.viewmodels.MainMenuViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.home.HomeFragmentDirections
import com.google.android.gms.auth.api.signin.GoogleSignIn


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
            val account = GoogleSignIn.getLastSignedInAccount(requireContext())
            if (account != null) {
                val gsc = GoogleSignIn.getClient(requireContext(), gso)
                gsc.signOut()
            }
            parentNavController.navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
        }
        binding.dailyStockBtn.setOnClickListener {
            requireView().findNavController().navigate(R.id.localStockFragment)
        }
        binding.newsBtn.setOnClickListener {
            requireView().findNavController().navigate(R.id.newsFragment)
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

        return binding.root
    }
}