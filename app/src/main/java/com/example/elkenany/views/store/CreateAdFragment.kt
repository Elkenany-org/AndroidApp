package com.example.elkenany.views.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentCreateAdBinding
import com.example.elkenany.entities.stock_data.LocalStockSector
import com.example.elkenany.viewmodels.CreateAdViewModel
import com.example.elkenany.viewmodels.ViewModelFactory

class CreateAdFragment : Fragment() {
    private lateinit var binding: FragmentCreateAdBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CreateAdViewModel
    private lateinit var sectroList: List<LocalStockSector>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var sectorId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_ad, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[CreateAdViewModel::class.java]
        binding.sectorAutoCompelete.apply {
            hint = "القطاعات"
            setHintTextColor(ContextCompat.getColorStateList(requireContext(), R.color.orange))
        }
        sectroList = listOf(
            LocalStockSector(1, "الداجني", "poultry", null),
            LocalStockSector(2, "الحيواني", "animal", null),
            LocalStockSector(3, "الزراعي", "farm", null),
            LocalStockSector(4, "السمكي", "fish", null),
            LocalStockSector(5, "الخيول", "horses", null),
        )
        val sectorNames = sectroList.map { list -> list.name }.toList()
        adapter = ArrayAdapter<String>(requireContext(), R.layout.sector_array_adapter, sectorNames)
        binding.sectorAutoCompelete.setAdapter(adapter)
        binding.sectorAutoCompelete.setOnItemClickListener { adapterView, _, position, _ ->
            sectorId = sectroList[position].id.toString()
            binding.sectorAutoCompelete.hint = adapterView.getItemAtPosition(position)
                .toString()
        }
        return binding.root
    }

}