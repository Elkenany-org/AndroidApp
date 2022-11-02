package com.elkenany.views.recruitment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.elkenany.R
import com.elkenany.databinding.FragmentApplySuccessfullyBinding


class ApplySuccessfullyFragment : Fragment() {
    private lateinit var binding: FragmentApplySuccessfullyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_apply_successfully,
            container,
            false)
        binding.exploreMoreBtn.setOnClickListener {
            requireView().findNavController()
                .navigate(ApplySuccessfullyFragmentDirections.actionApplySuccessfullyFragmentToJobsFragment())
        }
        return binding.root
    }
}