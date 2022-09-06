package com.example.elkenany.views.store

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentCreateAdBinding
import com.example.elkenany.entities.stock_data.LocalStockSector
import com.example.elkenany.viewmodels.CreateAdViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import java.io.File


class CreateAdFragment : Fragment() {
    private lateinit var binding: FragmentCreateAdBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CreateAdViewModel
    private lateinit var sectroList: List<LocalStockSector>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var sectorId: String
    private lateinit var title: String
    private lateinit var description: String
    private lateinit var phone: String
    private lateinit var price: String
    private lateinit var address: String
    private lateinit var imageFile: File
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
        binding.pickImageBtn.setOnClickListener {
            getImageFromStorage()
        }
        binding.addAdBtn.setOnClickListener {
            title = binding.adTitleInput.text.toString().trim()
            description = binding.descriptionInput.text.toString().trim()
            phone = binding.phoneInput.text.toString().trim()
            price = binding.priceInput.text.toString().trim()
            address = binding.addressInput.text.toString().trim()
            if (title.isEmpty() && description.isEmpty() && phone.isEmpty() && price.isEmpty() && address.isEmpty() && !imageFile.exists()) {
                Toast.makeText(requireContext(),
                    "برجاء ادخال بيانات الاعلان كاملة",
                    Toast.LENGTH_SHORT).show()
            } else {
                viewModel.createNewAd(title,
                    description,
                    phone,
                    price,
                    sectorId.toLong(),
                    address,
                    imageFile)
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingProgressbar1.visibility = View.VISIBLE
                binding.addAdBtn.visibility = View.GONE
            } else {
                binding.loadingProgressbar1.visibility = View.GONE
                binding.addAdBtn.visibility = View.VISIBLE
            }
        }

        viewModel.createdAd.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it) {
                    Toast.makeText(requireContext(), "done", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "fail", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }

    @Suppress("DEPRECATION")
    @SuppressLint("IntentReset")
    private fun getImageFromStorage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }


    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            binding.imageIndicator.visibility = View.VISIBLE
            val image = data?.data!!
            imageFile = image.path?.let { File(it) }!!
        }else{
            binding.imageIndicator.visibility = View.VISIBLE
        }
    }
}