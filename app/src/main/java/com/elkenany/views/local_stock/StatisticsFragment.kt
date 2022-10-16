package com.elkenany.views.local_stock

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
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentStatisticsBinding
import com.elkenany.viewmodels.StatisticsViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.local_stock.adapter.StatisticsFodderAdapter
import com.elkenany.views.local_stock.adapter.StatisticsLocalAdapter
import java.text.SimpleDateFormat
import java.util.*


class StatisticsFragment : Fragment() {
    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: StatisticsViewModel
    private val args: StatisticsFragmentArgs by navArgs()
    private val myCalendar: Calendar = Calendar.getInstance()

    private lateinit var myFormat: String
    private lateinit var dateFormat: SimpleDateFormat
    private var dataFrom: String? = null
    private var dataTo: String? = null

    private var itemId: Long? = null
    private var companyId: Long? = null
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var statiscsAdapter: StatisticsLocalAdapter
    private lateinit var fodderAdapter: StatisticsFodderAdapter
    override fun onResume() {
        super.onResume()
        if (args.type == "local") {
            binding.companyAutoCompelete.hint = "الصنف"
            viewModel.getLocalStockDetailsData(args.id, args.type, dataFrom, dataTo, itemId)
        } else {
            binding.companyAutoCompelete.hint = "الشركات"
            viewModel.getFodderStockDetailsData(dataFrom, dataTo, args.id, companyId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[StatisticsViewModel::class.java]
        myFormat = "YYYY-MM-d"
        dateFormat = SimpleDateFormat(myFormat, Locale.US)
//        initializeCalender()
        Log.i("statistics args", args.toString())
        binding.dateFromTv.setOnClickListener {
            updateLabel(it)
        }
        binding.dateToTv.setOnClickListener {
            updateLabel(it)
        }

        statiscsAdapter = StatisticsLocalAdapter(ClickListener { })
        binding.localRecyclerView.adapter = statiscsAdapter

        fodderAdapter = StatisticsFodderAdapter(ClickListener { })
        binding.fodderRecyclerView.adapter = fodderAdapter
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    localRecyclerView.visibility = View.INVISIBLE
                    fodderRecyclerView.visibility = View.INVISIBLE
                    loadingProgressbar.visibility = View.VISIBLE
                    companyBtn.isClickable = false
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
        viewModel.statisticsLocalData.observe(viewLifecycleOwner)
        {
            if (it != null) {
                binding.apply {
                    companyBtn.isClickable = true
                    localRecyclerView.visibility = View.VISIBLE
                    statiscsAdapter.submitList(it.localChangesMembers)
                    fodderRecyclerView.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                }
                val productList = it.listMembers.map { list -> list!!.name }.toList()
                adapter = ArrayAdapter<String>(requireContext(),
                    R.layout.array_adapter_item,
                    productList)
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
                    localRecyclerView.visibility = View.GONE
                    errorMessage.visibility = View.VISIBLE
                }
            }
        }
        viewModel.statisticsFodderData.observe(viewLifecycleOwner)
        {
            if (it != null) {
                binding.apply {
                    companyBtn.isClickable = true
                    localRecyclerView.visibility = View.GONE
                    fodderAdapter.submitList(it.changesMembers)
                    fodderRecyclerView.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                }
                val companiesList = it.listMembers.map { list -> list!!.name }.toList()
                adapter = ArrayAdapter<String>(requireContext(),
                    R.layout.array_adapter_item,
                    companiesList)
                binding.companyAutoCompelete.setAdapter(adapter)
                binding.companyAutoCompelete.setOnItemClickListener { adapterView, _, position, _ ->
                    Log.i("statisticsData", it.listMembers[position]!!.id.toString())
                    companyId = it.listMembers[position]!!.id
                    binding.companyAutoCompelete.hint = adapterView.getItemAtPosition(position)
                        .toString()
                    viewModel.getFodderStockDetailsData(dataFrom, dataTo, args.id, companyId)
                }


            } else {
                binding.apply {
                    companyBtn.isClickable = true
                    localRecyclerView.visibility = View.GONE
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
                Log.i("dataFormant", dateFormat.format(myCalendar.time))
                when (view.id) {
                    R.id.date_from_tv -> {
                        dataFrom = dateFormat.format(myCalendar.time)
                        binding.dateFromTv.text = dataFrom
                        if (!dataTo.isNullOrEmpty()) {
                            if (args.type == "local") {
                                viewModel.getLocalStockDetailsData(args.id,
                                    args.type,
                                    dataFrom,
                                    dataTo,
                                    itemId)
                            } else if (args.type == "fodder") {
                                viewModel.getFodderStockDetailsData(dataFrom,
                                    dataTo,
                                    args.id,
                                    companyId)
                            }

                        }
                    }
                    R.id.date_to_tv -> {
                        dataTo = dateFormat.format(myCalendar.time)
                        binding.dateToTv.text = dataTo
                        if (!dataFrom.isNullOrEmpty()) {
                            if (args.type == "local") {
                                viewModel.getLocalStockDetailsData(args.id,
                                    args.type,
                                    dataFrom,
                                    dataTo,
                                    itemId)
                            } else if (args.type == "fodder") {
                                viewModel.getFodderStockDetailsData(dataFrom,
                                    dataTo,
                                    args.id,
                                    companyId)
                            }
                        }
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

//    private fun initializeCalender() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            myCalendar.set(Calendar.YEAR, LocalDateTime.now().year)
//            myCalendar.set(Calendar.MONTH, LocalDateTime.now().monthValue)
//            myCalendar.set(Calendar.DAY_OF_MONTH, LocalDateTime.now().dayOfMonth)
//            dataFrom = dateFormat.format(myCalendar.time)
//            dataTo = dateFormat.format(myCalendar.time)
//            binding.dateFromTv.text = dataFrom
//            binding.dateToTv.text = dataTo
//            viewModel.getLocalStockDetailsData(args.id,
//                args.type,
//                dataFrom,
//                dataTo,
//                itemId)
//        }
//    }
}