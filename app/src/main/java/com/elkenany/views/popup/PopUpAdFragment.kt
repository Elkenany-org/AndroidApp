package com.elkenany.views.popup

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.elkenany.R
import com.elkenany.databinding.FragmentPopUpAdBinding
import com.elkenany.viewmodels.PopUpAdViewModel
import com.elkenany.viewmodels.ViewModelFactory

class PopUpAdFragment : Fragment() {
    private lateinit var binding: FragmentPopUpAdBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: PopUpAdViewModel
    private lateinit var uri: Uri
    private lateinit var mediaController: MediaController

    private val args: PopUpAdFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pop_up_ad, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[PopUpAdViewModel::class.java]
        binding.clickable = false

        uri = Uri.parse(args.link)
        mediaController = MediaController(requireContext())

        mediaController.setAnchorView(binding.videoView)
        binding.videoView.setVideoURI(uri)
        binding.videoView.requestFocus()
        binding.videoView.start()

        binding.skipBtn.setOnClickListener {
            requireView().findNavController().popBackStack()
        }

        viewModel.counter.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.skipBtn.text = "تخطي $it"
                if (it == 0) {
                    binding.skipBtn.text = "تخطي"
                }
                binding.clickable = it == 0
            }
        }
        return binding.root
    }
}