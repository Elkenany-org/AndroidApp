package com.elkenany.views.ships

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentShipsBinding
import com.elkenany.viewmodels.ShipsViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.local_stock.adapter.LocalStockBannersAdapter
import com.elkenany.views.local_stock.adapter.LocalStockLogosAdapter
import com.elkenany.views.ships.adapter.ShipsAdapter
import java.text.SimpleDateFormat
import java.util.*

class ShipsFragment : Fragment() {
    private lateinit var binding: FragmentShipsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ShipsViewModel
    private lateinit var shipsAdapter: ShipsAdapter
    private lateinit var bannerAdapter: LocalStockBannersAdapter
    private lateinit var logoAdapter: LocalStockLogosAdapter
    private val myCalendar: Calendar = Calendar.getInstance()
    private var date: String? = null

    @SuppressLint("PrivateResource")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ships, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[ShipsViewModel::class.java]
        viewModel.getAllSearchData(date)
        bannerAdapter = LocalStockBannersAdapter(ClickListener { })
        binding.bannersRecyclerView.adapter = bannerAdapter
        logoAdapter = LocalStockLogosAdapter(ClickListener { })
        binding.logosRecyclerView.adapter = logoAdapter
        shipsAdapter = ShipsAdapter(ClickListener { })
        binding.shipsDataRecyclerView.adapter = shipsAdapter
        val date =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel()
            }
        binding.calenderBtn.setOnClickListener {
            DatePickerDialog(
                this.requireActivity(),
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.statisticsBtn.setOnClickListener {
            requireView().findNavController()
                .navigate(ShipsFragmentDirections.actionShipsFragmentToShipsStatisticsFragment())
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.errorMessage.visibility = View.GONE
                binding.recyclerViewLayout.visibility = View.GONE
                binding.loadingProgressbar.visibility = View.VISIBLE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        viewModel.exception.observe(viewLifecycleOwner) {
            when (it) {
                200 -> {
                    binding.errorMessage.visibility = View.GONE
                }
                404 -> {
                    binding.apply {
                        errorMessage.visibility = View.VISIBLE
                        errorMessage.text = "عفوا لا توجد بيانات"
                    }
                }
                else -> {
                    binding.apply {
                        errorMessage.visibility = View.VISIBLE
                        errorMessage.text =
                            androidx.compose.ui.R.string.default_error_message.toString()
                    }

                }
            }
        }

        viewModel.shipsData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    recyclerViewLayout.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                    bannerAdapter.submitList(it.banners)
                    logoAdapter.submitList(it.logos)
                    shipsAdapter.submitList(it.ships)
                    Log.i("shipsData", it.ships.toString())
                }
            } else {
                binding.apply {
                    recyclerViewLayout.visibility = View.GONE
                }
            }
        }
        return binding.root
    }

    @SuppressLint("NewApi", "WeekBasedYear")
    private fun updateLabel() {
        val myFormat = "YYYY-MM-d"
        date = SimpleDateFormat(myFormat, Locale.US).format(myCalendar.time)
        viewModel.getAllSearchData(date)
    }
}