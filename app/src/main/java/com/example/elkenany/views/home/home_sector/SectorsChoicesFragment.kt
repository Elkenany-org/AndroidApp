package com.example.elkenany.views.home.home_sector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentSectorsChoicesBinding

class SectorsChoicesFragment : Fragment() {

    private lateinit var binding: FragmentSectorsChoicesBinding
    private val args: SectorsChoicesFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sectors_choices, container, false)
        binding.titleBar.text = args.sectorName
        binding.stockBtn.setOnClickListener {
            view!!.findNavController().navigate(
                SectorsChoicesFragmentDirections.actionSectorsChoicesFragmentToLocalStockFragment(
                    args.sectorId,
                    args.sectorName,
                    args.sectorType
                )
            )
        }
        return binding.root
    }

}