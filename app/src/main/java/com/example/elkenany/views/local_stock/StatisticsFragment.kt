package com.example.elkenany.views.local_stock

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
import androidx.navigation.fragment.navArgs
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentStatisticsBinding
import com.example.elkenany.viewmodels.StatisticsViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.local_stock.adapter.StatisticsAdapter
import java.text.SimpleDateFormat
import java.util.*


class StatisticsFragment : Fragment() {
    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: StatisticsViewModel
    private val args: StatisticsFragmentArgs by navArgs()
    private val myCalendar: Calendar = Calendar.getInstance()

    private var dataFrom: String = ""
    private var dataTo: String = ""

    private var itemId: Long? = null
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var statiscsAdapter: StatisticsAdapter
    override fun onResume() {
        super.onResume()
        viewModel.getLocalStockDetailsData(args.id, args.type, dataFrom, dataTo, itemId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[StatisticsViewModel::class.java]
        Log.i("statistics args", args.toString())


        binding.dateFromTv.setOnClickListener {
            updateLabel(it)
        }
        binding.dateToTv.setOnClickListener {
            updateLabel(it)
        }

        statiscsAdapter = StatisticsAdapter(ClickListener { })
        binding.sectorsRecyclerView.adapter = statiscsAdapter
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    sectorsRecyclerView.visibility = View.INVISIBLE
                    loadingProgressbar.visibility = View.VISIBLE
                    companyBtn.isClickable = false
                }
            } else {
                binding.apply {
                    loadingProgressbar.visibility = View.GONE
                }
            }
        }

        viewModel.statisticsData.observe(viewLifecycleOwner)
        {
            if (it != null) {
                binding.apply {
                    companyBtn.isClickable = true
                    sectorsRecyclerView.visibility = View.VISIBLE
                    statiscsAdapter.submitList(it.changesMembers)
                }
                val list: MutableList<String> = mutableListOf()
                for (i in it.listMembers) {
                    list.add(i!!.name.toString())
                }

                adapter = ArrayAdapter<String>(requireContext(),
                    R.layout.array_adapter_item,
                    list)
                binding.companyAutoCompelete.setAdapter(adapter)
                binding.companyAutoCompelete.setOnItemClickListener { adapterView, _, position, _ ->
                    Log.i("statisticsData", it.listMembers[position]!!.id.toString())
                    itemId = it.listMembers[position]!!.id
                    binding.companyAutoCompelete.hint = adapterView.getItemAtPosition(position)
                        .toString()
                    viewModel.getLocalStockDetailsData(args.id,
                        args.type,
                        dataFrom,
                        dataTo,
                        itemId)
                }


            } else {
                binding.apply {
                    companyBtn.isClickable = true
                    sectorsRecyclerView.visibility = View.GONE
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
                        dataFrom = dateFormat.format(myCalendar.time)
                        binding.dateFromTv.text = dataFrom
                        viewModel.getLocalStockDetailsData(args.id,
                            args.type,
                            dataFrom,
                            dataTo,
                            itemId)
                    }
                    R.id.date_to_tv -> {
                        dataTo = dateFormat.format(myCalendar.time)
                        binding.dateToTv.text = dataTo
                        viewModel.getLocalStockDetailsData(args.id,
                            args.type,
                            dataFrom,
                            dataTo,
                            itemId)
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