package com.example.elkenany.views.store

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentCreateAdBinding
import com.example.elkenany.entities.stock_data.LocalStockSector
import com.example.elkenany.viewmodels.CreateAdViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Suppress("DEPRECATION")
class CreateAdFragment : Fragment() {
    private lateinit var binding: FragmentCreateAdBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CreateAdViewModel
    private lateinit var sectorList: List<LocalStockSector>
    private lateinit var adapter: ArrayAdapter<String>
    private var sectorId: String? = null
    private lateinit var title: String
    private lateinit var description: String
    private lateinit var phone: String
    private lateinit var price: String
    private lateinit var address: String
    private lateinit var imageFile: File
    private lateinit var fileResult: String
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
        sectorList = listOf(
            LocalStockSector(1, "الداجني", "poultry", null),
            LocalStockSector(2, "الحيواني", "animal", null),
            LocalStockSector(3, "الزراعي", "farm", null),
            LocalStockSector(4, "السمكي", "fish", null),
            LocalStockSector(5, "الخيول", "horses", null),
        )
        val sectorNames = sectorList.map { list -> list.name }.toList()
        adapter = ArrayAdapter<String>(requireContext(), R.layout.sector_array_adapter, sectorNames)
        binding.sectorAutoCompelete.setAdapter(adapter)
        binding.sectorAutoCompelete.setOnItemClickListener { adapterView, _, position, _ ->
            sectorId = sectorList[position].id.toString()
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
            if (title.isEmpty() && description.isEmpty() && phone.isEmpty() && price.isEmpty() && address.isEmpty() && sectorId.isNullOrEmpty()) {
                Toast.makeText(requireContext(),
                    "برجاء ادخال بيانات الاعلان كاملة",
                    Toast.LENGTH_SHORT).show()
            } else if (title.isEmpty()) {
                binding.adTitleInput.apply {
                    error = "برجاء ادخال أسم الأعلان"
                    requestFocus()
                }
            } else if (description.isEmpty()) {
                binding.descriptionInput.apply {
                    error = "برجاء ادخال وصف الأعلان"
                    requestFocus()
                }
            } else if (phone.isEmpty()) {
                binding.phoneInput.apply {
                    error = "برجاء ادخال رقم الهاتف"
                    requestFocus()
                }
            } else if (price.isEmpty()) {
                binding.priceInput.apply {
                    error = "برجاء ادخال السعر"
                    requestFocus()
                }
            } else if (address.isEmpty()) {
                binding.addressInput.apply {
                    error = "برجاء ادخال العنوان"
                    requestFocus()
                }
            } else if (sectorId.isNullOrEmpty()) {
                binding.sectorBtn.apply {
                    requestFocus()
                }
                Toast.makeText(requireContext(), "برجاء تحديد القطاع", Toast.LENGTH_SHORT).show()
            } else {
                binding.imageIndicator.visibility = View.GONE
                viewModel.createNewAd(title,
                    description,
                    phone,
                    price,
                    sectorId!!.toLong(),
                    address,
                    fileResult)
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
                    Toast.makeText(requireContext(), "تم إنشاء الإعلان بنجاح", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(requireContext(), "حدث خطأ في إنشاء الإعلان", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        return binding.root
    }


    @SuppressLint("IntentReset")
    private fun getImageFromStorage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            binding.imageIndicator.visibility = View.VISIBLE
            val uri = data?.data!!
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val selectedImage = BitmapFactory.decodeStream(inputStream)
            val encodedImage: String = encodeImage(selectedImage)
            fileResult = "data:image/png;base64,$encodedImage"
            Log.i("base64", encodedImage)
//            val uri1 = Uri.parse(uri.toString())
//            val file = File(uri1.toString())
//            val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
//            val image = MultipartBody.Part.createFormData("part", file.name, reqFile)
//            Log.i("ImageFIle", "$reqFile")
        } else {
            binding.imageIndicator.visibility = View.GONE
        }
    }

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.getEncoder().encodeToString(b)
    }
}