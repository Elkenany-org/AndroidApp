package com.elkenany.views.local_stock

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentLocalStockDetailsBinding
import com.elkenany.utilities.GlobalUiFunctions.Companion.enableImageSlider
import com.elkenany.utilities.GlobalUiFunctions.Companion.navigateToBroswerIntent
import com.elkenany.viewmodels.LocalStockDetailsViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.local_stock.adapter.LocalStockDetailsAdapter
import com.elkenany.views.local_stock.adapter.LocalStockLogosAdapter
import java.text.SimpleDateFormat
import java.util.*


class LocalStockDetailsFragment : Fragment() {
    private lateinit var binding: FragmentLocalStockDetailsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: LocalStockDetailsViewModel
    private val args: LocalStockDetailsFragmentArgs by navArgs()
    private lateinit var logosAdapter: LocalStockLogosAdapter
    private lateinit var localStockDetailsAdapter: LocalStockDetailsAdapter
    private var companyId: String? = null
    private var feedId: String? = null
    private var dateFormat: String? = null
    private val myCalendar: Calendar = Calendar.getInstance()
    private lateinit var feedAdapter: ArrayAdapter<String?>
    private lateinit var companyAdapter: ArrayAdapter<String?>
    override fun onResume() {
        super.onResume()
        binding.companyAutoCompelete.setText("الشركات")
        binding.productAutoCompelete.setText("الأصناف")
        if (!dateFormat.isNullOrEmpty()) {
            binding.calenderBtn.text = dateFormat
        } else {
            binding.calenderBtn.setText(R.string.today_title)
        }
        Log.i("args.id", args.id.toString())
        viewModel.getLocalStockDetailsData(
            args.id,
            dateFormat,
            args.sectorType.toString(),
            null,
            null
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_local_stock_details,
            container,
            false
        )
        viewModelFactory = ViewModelFactory()
        viewModel =
            ViewModelProvider(this, viewModelFactory)[LocalStockDetailsViewModel::class.java]
        binding.appBarTitle.text = args.sectorName
        binding.bannersImageSlider.apply {
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
                .navigate(
                    LocalStockDetailsFragmentDirections.actionLocalStockDetailsFragmentToStatisticsFragment(
                        args.id,
                        args.sectorType
                    )
                )
        }
        logosAdapter = LocalStockLogosAdapter(ClickListener {
            navigateToBroswerIntent(it.link, requireActivity())
        })
        binding.logosRecyclerView.apply {
            adapter = logosAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }

        localStockDetailsAdapter = LocalStockDetailsAdapter(ClickListener {}, ClickListener {
            requireView().findNavController()
                .navigate(LocalStockDetailsFragmentDirections.actionLocalStockDetailsFragmentToCompanyFragment(
                    it.memId!!.toLong(),
                    it.name!!))
        })
        binding.stockDataRecyclerView.adapter = localStockDetailsAdapter
//        viewModel.getLocalStockDetailsData(args.id, "", args.sectorType.toString())
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.errorMessage.visibility = View.GONE
                binding.stockDataRecyclerView.visibility = View.GONE
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.stockDataRecyclerView.smoothScrollToPosition(0)
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        viewModel.feedsItem.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.productBtn.visibility = View.VISIBLE
                Log.i("feedList", it.fodderList.toString())
                val feedList =
                    it.fodderList.map { newList -> newList!!.name }.toList()
                feedAdapter = ArrayAdapter<String?>(
                    requireContext(),
                    R.layout.array_adapter_item,
                    feedList
                )
                binding.productAutoCompelete.setAdapter(feedAdapter)
                binding.productAutoCompelete.setOnItemClickListener { adapterView, _, position, _ ->
                    binding.companyAutoCompelete.setText("الشركات")
                    Toast.makeText(requireContext(),
                        it.fodderList[position]!!.name,
                        Toast.LENGTH_SHORT).show()
                    feedId = it.fodderList[position]!!.id.toString()
                    binding.productAutoCompelete.hint = adapterView.getItemAtPosition(position)
                        .toString()
                    viewModel.getLocalStockDetailsData(
                        args.id,
                        dateFormat,
                        args.sectorType.toString(),
                        feedId,
                        null
                    )
                }
            } else {
                binding.productBtn.visibility = View.GONE
                Log.i("feedList", "failed to load feed data in it $it")
            }
        }
        viewModel.companyItem.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.countryBtn.visibility = View.VISIBLE
                val companyList = it.map { newList -> newList!!.name }.toList()
                companyAdapter = ArrayAdapter<String?>(
                    requireContext(),
                    R.layout.array_adapter_item,
                    companyList
                )
                binding.companyAutoCompelete.setAdapter(companyAdapter)
                binding.companyAutoCompelete.setOnItemClickListener { adapterView, _, position, _ ->
                    binding.productAutoCompelete.setText("الأصناف")
                    Toast.makeText(requireContext(), it[position]!!.name, Toast.LENGTH_SHORT).show()
                    companyId = it[position]!!.id.toString()
                    binding.companyAutoCompelete.hint = adapterView.getItemAtPosition(position)
                        .toString()
                    viewModel.getLocalStockDetailsData(
                        args.id,
                        dateFormat,
                        args.sectorType.toString(),
                        null,
                        companyId
                    )
                }
            } else {
                binding.countryBtn.visibility = View.GONE
                Log.i(
                    "companyList",
                    "failed to load company data in localStockDetailsFragment.kt $it"
                )
            }
        }
        viewModel.exception.observe(viewLifecycleOwner) {
            when (it) {
                null -> {}
                200 -> {
                    binding.errorMessage.visibility = View.GONE
                }
                401 -> {
                    Toast.makeText(requireContext(),
                        "برجاء تسجيل الدخول أولا حتي تتمكن من معرفة تفاصيل البورصة",
                        Toast.LENGTH_SHORT)
                        .show()
                }
                402 -> {
                    binding.errorMessage.text =
                        "برجاء التحويل الي الباقة المدفوعة لمعرفة تفاصيل أكثر"
                    binding.errorMessage.visibility = View.VISIBLE
                }
                404 -> {
                    binding.errorMessage.text =
                        "لا توجد بيانات"
                    binding.errorMessage.visibility = View.VISIBLE
                }
                else -> {
                    binding.errorMessage.text =
                        "تعذر الحصول علي المعلومات"
                    binding.errorMessage.visibility = View.VISIBLE
                }
            }
        }
        viewModel.localStockDetailsData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.message.isNullOrEmpty()) {
                    binding.noticeTv.visibility = View.GONE
                } else {
                    binding.noticeTv.apply {
                        visibility = View.VISIBLE
                        text = it.message
                    }
                }
                binding.errorMessage.visibility = View.GONE
                binding.foundDataLayout.visibility = View.VISIBLE
                binding.stockDataRecyclerView.visibility = View.VISIBLE
                logosAdapter.submitList(it.logos)
                enableImageSlider(it.banners, binding.bannersImageSlider, requireActivity())
                localStockDetailsAdapter.submitList(listOf(it.columns) + it.members)
                if (args.sectorType == "fodder") {
                    binding.apply {
                        fodderExternalLayout.visibility = View.VISIBLE
                    }
                } else {
                    binding.apply {
                        fodderExternalLayout.visibility = View.GONE
                    }
                }
            }
        }
        return binding.root
    }

    @SuppressLint("NewApi", "WeekBasedYear")
    private fun updateLabel() {
        val myFormat = "YYYY-MM-d"
        dateFormat = SimpleDateFormat(myFormat, Locale.US).format(myCalendar.time)
        binding.calenderBtn.text = dateFormat
        viewModel.getLocalStockDetailsData(
            args.id,
            dateFormat,
            args.sectorType.toString(),
            feedId, companyId
        )
    }

}