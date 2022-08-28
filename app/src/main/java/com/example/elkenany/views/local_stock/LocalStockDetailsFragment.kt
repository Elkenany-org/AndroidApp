package com.example.elkenany.views.local_stock

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentLocalStockDetailsBinding
import com.example.elkenany.viewmodels.LocalStockDetailsViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.local_stock.adapter.LocalStockBannersAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockDetailsAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockLogosAdapter
import java.text.SimpleDateFormat
import java.util.*


class LocalStockDetailsFragment : Fragment() {
    private lateinit var binding: FragmentLocalStockDetailsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: LocalStockDetailsViewModel
    private val args: LocalStockDetailsFragmentArgs by navArgs()
    private lateinit var bannersAdapter: LocalStockBannersAdapter
    private lateinit var logosAdapter: LocalStockLogosAdapter
    private lateinit var localStockDetailsAdapter: LocalStockDetailsAdapter
    private val myCalendar: Calendar = Calendar.getInstance()
    override fun onResume() {
        super.onResume()
        viewModel.getLocalStockDetailsData(args.id, "", args.sectorType.toString())
    }

    //    private lateinit var arrayAdapter: ArrayAdapter<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_local_stock_details,
            container,
            false)
        viewModelFactory = ViewModelFactory()
        viewModel =
            ViewModelProvider(this, viewModelFactory)[LocalStockDetailsViewModel::class.java]
        binding.appBarTitle.text = args.sectorName
        bannersAdapter = LocalStockBannersAdapter(ClickListener { })
        binding.bannersRecyclerView.apply {
            adapter = bannersAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        val date =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel()
            }
        binding.calenderBtn.setOnClickListener {
            DatePickerDialog(this.requireActivity(),
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.statisticsBtn.setOnClickListener {
            requireView().findNavController()
                .navigate(LocalStockDetailsFragmentDirections.actionLocalStockDetailsFragmentToStatisticsFragment(
                    args.id,
                    args.sectorType))
        }

        logosAdapter = LocalStockLogosAdapter(ClickListener {})
        binding.logosRecyclerView.apply {
            adapter = logosAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        localStockDetailsAdapter = LocalStockDetailsAdapter(ClickListener {})
        binding.stockDataRecyclerView.adapter = localStockDetailsAdapter
//        viewModel.getLocalStockDetailsData(args.id, "", args.sectorType.toString())
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.errorMessage.visibility = View.GONE
                binding.stockDataRecyclerView.visibility = View.GONE
                binding.loadingProgressbar.visibility = View.VISIBLE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        viewModel.localStockDetailsData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.errorMessage.visibility = View.GONE
                binding.foundDataLayout.visibility = View.VISIBLE
                binding.stockDataRecyclerView.visibility = View.VISIBLE
                logosAdapter.submitList(it.logos)
                bannersAdapter.submitList(it.banners)
                localStockDetailsAdapter.submitList(listOf(it.columns) + it.members)
                if (args.sectorType == "fodder") {
                    binding.apply {
                        fodderExternalLayout.visibility = View.VISIBLE
//                        arrayAdapter = ArrayAdapter(requireContext(), R.layout.small_recycler_item,
//                            listOf())
//                        companyAutoCompelete.setAdapter(arrayAdapter)
                    }
                } else {
                    binding.apply {
                        fodderExternalLayout.visibility = View.GONE
                    }

                }

            } else {
                binding.errorMessage.apply {
                    text = "برجاء تسجيل الدخول لأستخدام جميع الخدمات بشكل كامل"
                    visibility = View.VISIBLE
                }
                binding.stockDataRecyclerView.visibility = View.GONE
            }
        }


        return binding.root
    }

    @SuppressLint("NewApi", "WeekBasedYear")
    private fun updateLabel() {
        val myFormat = "YYYY-MM-d"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        Log.i("dataFormant", dateFormat.format(myCalendar.time))
        viewModel.getLocalStockDetailsData(args.id,
            dateFormat.format(myCalendar.time),
            args.sectorType.toString())
    }
}