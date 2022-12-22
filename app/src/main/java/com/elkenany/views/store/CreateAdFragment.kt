package com.elkenany.views.store

//import java.io.File
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
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.elkenany.R
import com.elkenany.databinding.FragmentCreateAdBinding
import com.elkenany.entities.stock_data.LocalStockSector
import com.elkenany.viewmodels.CreateAdViewModel
import com.elkenany.viewmodels.ViewModelFactory
import java.io.ByteArrayOutputStream
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Suppress("DEPRECATION")
class CreateAdFragment : Fragment() {
    private lateinit var binding: FragmentCreateAdBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CreateAdViewModel
    private lateinit var sectorList: List<LocalStockSector>
    private lateinit var adapter: ArrayAdapter<String>
    private var adId: Long? = null
    private var sectorId: String? = null
    private lateinit var title: String
    private lateinit var description: String
    private lateinit var phone: String
    private lateinit var price: String
    private lateinit var address: String
    private val myArrayUri = ArrayList<String>()
    private val myOldImage = ArrayList<String>()
    private val args: CreateAdFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_ad, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[CreateAdViewModel::class.java]
        adId = try {
            args.id!!.toLong()
        } catch (e: Exception) {
            null
        }
        if (adId != null) {
            binding.addAdBtn.visibility = View.GONE
            binding.editAdBtn.visibility = View.VISIBLE
            viewModel.getAdDetailsData(adId!!.toLong())
        } else {
            binding.addAdBtn.visibility = View.VISIBLE
            binding.editAdBtn.visibility = View.GONE
        }
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
        binding.editAdBtn.setOnClickListener {
            title = binding.adTitleInput.text.toString().trim()
            description = binding.descriptionInput.text.toString().trim()
            phone = binding.phoneInput.text.toString().trim()
            price = binding.priceInput.text.toString().trim()
            address = binding.addressInput.text.toString().trim()
            if (title.isEmpty() && description.isEmpty() && phone.isEmpty() && price.isEmpty() && address.isEmpty() && sectorId.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "برجاء ادخال بيانات الاعلان كاملة",
                    Toast.LENGTH_SHORT
                ).show()
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
                viewModel.editAd(
                    adId!!.toLong(),
                    title,
                    description,
                    phone,
                    price,
                    sectorId!!.toLong(),
                    address,
                    myOldImage.toString(),
                    myArrayUri.toString()
                )
            }

        }
        viewModel.adDetailsData.observe(viewLifecycleOwner)
        {
            if (it != null) {
                binding.apply {
                    adTitleInput.setText(it.title.toString().trim())
                    descriptionInput.setText(it.desc.toString().trim())
                    addressInput.setText(it.address.toString().trim())
                    phoneInput.setText(it.phone.toString().trim())
                    priceInput.setText(it.salary.toString().trim())
                }
            }
        }
        binding.addAdBtn.setOnClickListener {
            title = binding.adTitleInput.text.toString().trim()
            description = binding.descriptionInput.text.toString().trim()
            phone = binding.phoneInput.text.toString().trim()
            price = binding.priceInput.text.toString().trim()
            address = binding.addressInput.text.toString().trim()
            if (title.isEmpty() && description.isEmpty() && phone.isEmpty() && price.isEmpty() && address.isEmpty() && sectorId.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "برجاء ادخال بيانات الاعلان كاملة",
                    Toast.LENGTH_SHORT
                ).show()
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
                viewModel.createNewAd(
                    title,
                    description,
                    phone,
                    price,
                    sectorId!!.toLong(),
                    address,
                    myArrayUri.toString()
                )
            }

        }
        viewModel.exception.observe(viewLifecycleOwner) {
            when (it) {
                200 -> {
                    Toast.makeText(
                        requireContext(),
                        "تمت العملية بنجاح",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    requireView().findNavController().popBackStack()
                }
                402 -> Toast.makeText(
                    requireContext(),
                    "لقد تخطيت الحد الأقصي للأعلانات , برجاء التحويل للباقة المدفوعة",
                    Toast.LENGTH_SHORT
                )
                    .show()
                else -> Toast.makeText(
                    requireContext(),
                    "حدث خطأ في اتمام العملية",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        viewModel.loading.observe(viewLifecycleOwner)
        {
            if (it) {
                binding.loadingProgressbar1.visibility = View.VISIBLE
                binding.addAdBtn.visibility = View.GONE
                binding.editAdBtn.visibility = View.GONE
            } else if (!it) {
                binding.loadingProgressbar1.visibility = View.GONE
                if (adId != null) {
                    binding.addAdBtn.visibility = View.GONE
                    binding.editAdBtn.visibility = View.VISIBLE
                } else {
                    binding.addAdBtn.visibility = View.VISIBLE
                    binding.editAdBtn.visibility = View.GONE
                }
            }
        }

        return binding.root
    }


    @SuppressLint("IntentReset")
    private fun getImageFromStorage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }


    @SuppressLint("SetTextI18n")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            if (data?.clipData != null) {
                binding.imageIndicator.apply {
                    visibility = View.VISIBLE
                    text = " تم إضافة${data.clipData!!.itemCount} صورة "
                }
                var i = 0
                val cout = data.clipData!!.itemCount
                binding.pickImageBtn.apply {
                    binding.pickImageBtn.apply {
                        foreground.setVisible(false, true)
                        setImageURI(data.clipData!!.getItemAt(i).uri)
                    }
                }
                while (i <= cout - 1) {
                    // adding imageuri in array
                    val imageurl = data.clipData!!.getItemAt(i).uri
                    val inputStream = requireContext().contentResolver.openInputStream(imageurl)
                    val selectedImage = BitmapFactory.decodeStream(inputStream)
                    val encodedImage: String = encodeImage(selectedImage)
                    myArrayUri.add("\"data:image/*;base64,$encodedImage\"")
                    i++
                }
            } else {
                // show this if no image is selected
                Toast.makeText(requireContext(), "You haven't picked Image", Toast.LENGTH_LONG)
                    .show()
            }
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