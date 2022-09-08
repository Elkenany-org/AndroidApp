package com.example.elkenany.views.store

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentMyAdsListBinding
import com.example.elkenany.entities.stock_data.LocalStockSector
import com.example.elkenany.viewmodels.MyAdsListViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.store.adapter.MyAdsAdapter

class MyAdsListFragment : Fragment() {
    private lateinit var binding: FragmentMyAdsListBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MyAdsListViewModel
    private lateinit var myAdsAdapter: MyAdsAdapter

    //    private lateinit var localStockSectorAdapter: MyAdsSectorsAdapter
    private lateinit var sectorList: List<LocalStockSector>
    private var sectorType: String = "poultry"

    override fun onResume() {
        super.onResume()
        binding.sectorAutoCompelete.hint = "الداجني"
        viewModel.getAllNewsData(sectorType)
    }

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
            viewModel.getAllNewsData(sectorType)
        }

        binding.addAdBtn.setOnClickListener {
            viewModel.navigateToCreateAdFragment()
        }

        myAdsAdapter = MyAdsAdapter(ClickListener {
            requireView().findNavController()
                .navigate(MyAdsListFragmentDirections.actionMyAdsListFragmentToAdDetailsFragment(it.id!!))
        }, ClickListener {
            viewModel.deleteAdFromDataBase(it.id)
            viewModel.getAllNewsData(sectorType)
        })
        binding.adsListRecyclerView.adapter = myAdsAdapter

        viewModel.exception.observe(viewLifecycleOwner) {
            Log.i("exception", it.toString())
            if (it == 404) {
                binding.apply {
                    errorMessage.text = "لا يوجد لديك إعلانات"
                    errorMessage.visibility = View.VISIBLE
                }
            } else if (it == 200) {
                binding.apply {
                    errorMessage.visibility = View.GONE
                }
            } else if (it == 401) {
                binding.apply {
                    errorMessage.text = "يرجي تسجيل الدخول أولا"
                    errorMessage.visibility = View.VISIBLE
                }
            } else if (it == 100) {
                Toast.makeText(requireContext(), "تم حذف الاعلان بنجاح", Toast.LENGTH_SHORT).show()
            } else if (it == 104) {
                Toast.makeText(requireContext(), "تعذر حذف الاعلان", Toast.LENGTH_SHORT).show()
            } else {
                binding.apply {
                    errorMessage.text = "تعذر الحصول علي معلومات"
                    errorMessage.visibility = View.VISIBLE
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
                        .navigate(MyAdsListFragmentDirections.actionMyAdsListFragmentToCreateAdFragment())
                    viewModel.onDoneNavigating()
                } else if (it == false) {
                    Toast.makeText(requireContext(), "برجاء تسجيل الدخول أولا", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
//                Log.i("null", "null")
            }
        }
        return binding.root
    }

}


//        localStockSectorAdapter = MyAdsSectorsAdapter(ClickListener {
//            sectorType = it.type.toString()
//            viewModel.getAllNewsData(sectorType)
//        })
//        binding.sectorsRecyclerView.adapter = localStockSectorAdapter
//        localStockSectorAdapter.submitList(sectorList)