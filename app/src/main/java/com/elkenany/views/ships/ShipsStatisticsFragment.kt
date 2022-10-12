package com.elkenany.views.ships

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentShipsStatisticsBinding
import com.elkenany.viewmodels.ShipsStatisticsViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.ships.adapter.StatisticsAdapter
import java.text.SimpleDateFormat
import java.util.*

class ShipsStatisticsFragment : Fragment() {
    private lateinit var binding: FragmentShipsStatisticsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ShipsStatisticsViewModel
    private lateinit var statistcsAdapter: StatisticsAdapter
    private lateinit var countryAdapter: ArrayAdapter<String>
    private lateinit var productAdapter: ArrayAdapter<String>
    private val myCalendar: Calendar = Calendar.getInstance()
    private var type: String? = null
    private var from: String? = null
    private var to: String? = null
    private var country: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_ships_statistics, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[ShipsStatisticsViewModel::class.java]
        viewModel.getAllStatisticsData(type, from, to, country)

        statistcsAdapter = StatisticsAdapter(ClickListener { })
        binding.statisticsRecyclerView.adapter = statistcsAdapter

        binding.dateFromTv.setOnClickListener {
            updateLabel(it)
        }
        binding.dateToTv.setOnClickListener {
            updateLabel(it)
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    statisticsRecyclerView.visibility = View.INVISIBLE
                    loadingProgressbar.visibility = View.VISIBLE
                    productBtn.isClickable = false
                    countryBtn.isClickable = false
                    errorMessage.visibility = View.GONE
                }
            } else {
                binding.apply {
                    loadingProgressbar.visibility = View.GONE
                }
            }
        }
        viewModel.exception.observe(viewLifecycleOwner) {
            when (it) {
                200 -> {
                    binding.errorMessage.visibility = View.GONE
                }
                401 -> {
                    binding.errorMessage.text =
                        "برجاء تسجيل الدخول أولا حتي تتمكن من معرفة تفاصيل البورصة"
                    binding.errorMessage.visibility = View.VISIBLE
                }
                402 -> {
                    binding.errorMessage.text =
                        "برجاء التحويل الي الباقة المدفوعة لمعرفة تفاصيل أكثر"
                    binding.errorMessage.visibility = View.VISIBLE
                }
                else -> {
                    binding.errorMessage.text =
                        "تعذر الحصول علي المعلومات"
                    binding.errorMessage.visibility = View.VISIBLE
                }
            }
        }
        viewModel.shipsData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    countryBtn.isClickable = true
                    productBtn.isClickable = true
                    statisticsRecyclerView.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                }
                statistcsAdapter.submitList(it.ships)
                val countryList = it.countries.map { list -> list!!.country }.toList()
                countryAdapter = ArrayAdapter<String>(requireContext(),
                    R.layout.array_adapter_item,
                    countryList)
                binding.countryAutoCompelete.setAdapter(countryAdapter)
                binding.countryAutoCompelete.setOnItemClickListener { adapterView, _, position, _ ->
                    country = it.countries[position]!!.country
                    binding.countryAutoCompelete.hint = adapterView.getItemAtPosition(position)
                        .toString()
                    viewModel.getAllStatisticsData(type, from, to, country)
                }
                val productList = it.products.map { list -> list!!.name }.toList()
                productAdapter = ArrayAdapter<String>(requireContext(),
                    R.layout.array_adapter_item,
                    productList)
                binding.productAutoCompelete.setAdapter(productAdapter)
                binding.productAutoCompelete.setOnItemClickListener { adapterView, _, position, _ ->
                    val itemId = it.products[position]!!.id
                    type = itemId.toString()
                    binding.productAutoCompelete.hint = adapterView.getItemAtPosition(position)
                        .toString()
                    viewModel.getAllStatisticsData(type, from, to, country)
                }


            } else {
                binding.apply {
                    countryBtn.isClickable = true
                    productBtn.isClickable = true
                    errorMessage.visibility = View.VISIBLE
                }
            }
        }

        return binding.root
    }

    @SuppressLint("NewApi", "WeekBasedYear")
    private fun updateLabel(view: View) {
        val date =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                val myFormat = "YYYY-MM-d"
                val dateFormat = SimpleDateFormat(myFormat, Locale.US)
                Log.i("dataFormant", dateFormat.format(myCalendar.time))
                when (view.id) {
                    R.id.date_from_tv -> {
                        from = dateFormat.format(myCalendar.time)
                        binding.dateFromTv.text = from
                        viewModel.getAllStatisticsData(type, from, to, country)
                    }
                    R.id.date_to_tv -> {
                        to = dateFormat.format(myCalendar.time)
                        binding.dateToTv.text = to
                        viewModel.getAllStatisticsData(type, from, to, country)
                    }
                    else -> Log.i("nocomment", "no comment")
                }

            }
        DatePickerDialog(this.requireActivity(),
            date,
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)).show()

    }
}