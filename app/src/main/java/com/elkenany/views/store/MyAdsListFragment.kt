package com.elkenany.views.store

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentMyAdsListBinding
import com.elkenany.entities.stock_data.LocalStockSector
import com.elkenany.entities.store.MyAdsDaum
import com.elkenany.viewmodels.MyAdsListViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.store.adapter.MyAdsAdapter

class MyAdsListFragment : Fragment() {
    private lateinit var binding: FragmentMyAdsListBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MyAdsListViewModel
    private lateinit var myAdsAdapter: MyAdsAdapter

    private lateinit var sectorList: List<LocalStockSector>
    private var sectorType: String = "poultry"
    private var sectorName: String? = null

    override fun onResume() {
        super.onResume()
        binding.sectorAutoCompelete.hint = "الداجني"
        sectorName = "الداجني"
        viewModel.getAllNewsData(sectorType)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_ads_list, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[MyAdsListViewModel::class.java]
        sectorList = listOf(
            LocalStockSector(1, "الداجني", "poultry", 0),
            LocalStockSector(2, "الحيواني", "animal", 0),
            LocalStockSector(3, "الزراعي", "farm", 0),
            LocalStockSector(4, "السمكي", "fish", 0),
            LocalStockSector(5, "الخيول", "horses", 0),
        )

        val sectorNames = sectorList.map { list -> list.name }.toList()
        val adapter =
            ArrayAdapter<String>(requireContext(), R.layout.sector_array_adapter, sectorNames)
        binding.sectorAutoCompelete.setAdapter(adapter)
        binding.sectorAutoCompelete.setOnItemClickListener { adapterView, _, position, _ ->
            binding.sectorAutoCompelete.hint = adapterView.getItemAtPosition(position).toString()
            sectorType = sectorList[position].type.toString()
            sectorName = sectorList[position].name.toString()
            viewModel.getAllNewsData(sectorType)
        }

        binding.addAdBtn.setOnClickListener {
            viewModel.navigateToCreateAdFragment()
        }

        myAdsAdapter = MyAdsAdapter(ClickListener {
            requireView().findNavController()
                .navigate(MyAdsListFragmentDirections.actionMyAdsListFragmentToAdDetailsFragment(it.id!!))
        }, ClickListener {
            basicAlert(requireContext(), it)
        }, ClickListener {
            requireView().findNavController()
                .navigate(MyAdsListFragmentDirections.actionMyAdsListFragmentToCreateAdFragment(it.id.toString()))
        })
        binding.adsListRecyclerView.adapter = myAdsAdapter

        viewModel.exception.observe(viewLifecycleOwner) {
            when (it) {
                404 -> {
                    binding.apply {
                        errorMessage.text =
                            "لا يوجد لديك إعلانات في القطاع $sectorName"
                        errorMessage.visibility = View.VISIBLE
                    }
                }
                200 -> {
                    binding.apply {
                        errorMessage.visibility = View.GONE
                    }
                }
                401 -> {
                    binding.apply {
                        errorMessage.text = "يرجي تسجيل الدخول أولا"
                        errorMessage.visibility = View.VISIBLE
                    }
                }
                100 -> {
                    Toast.makeText(requireContext(), "تم حذف الاعلان بنجاح", Toast.LENGTH_SHORT)
                        .show()
                }
                104 -> {
                    Toast.makeText(requireContext(), "تعذر حذف الاعلان", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    binding.apply {
                        errorMessage.text = "تعذر الحصول علي معلومات"
                        errorMessage.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewModel.myAdsList.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.apply {
                    adsListRecyclerView.visibility = View.GONE
                }
            } else {
                myAdsAdapter.submitList(it.data)
                binding.apply {
                    adsListRecyclerView.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                }
            }
        }
        viewModel.googeToNavigate.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it == true) {
                    requireView().findNavController()
                        .navigate(
                            MyAdsListFragmentDirections.actionMyAdsListFragmentToCreateAdFragment(
                                null
                            )
                        )
                    viewModel.onDoneNavigating()
                } else if (it == false) {
                    Toast.makeText(requireContext(), "برجاء تسجيل الدخول أولا", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        return binding.root
    }

    private fun basicAlert(context: Context, ad: MyAdsDaum) {

        val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom))

        with(builder)
        {
            setMessage("هل تريد حذف إعلان ' ${ad.title} ' ؟ ")
            setPositiveButton("نعم إحذف الإعلان") { _, _ ->
                viewModel.deleteAdFromDataBase(ad.id)
                viewModel.getAllNewsData(sectorType)
            }
            setNegativeButton("الغاء", null)
            show()
        }
    }
}
