package com.example.elkenany.views.local_stock

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
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
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentLocalStockDetailsBinding
import com.example.elkenany.entities.stock_data.LocalStockBanner
import com.example.elkenany.viewmodels.LocalStockDetailsViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.local_stock.adapter.LocalStockBannersAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockDetailsAdapter
import com.example.elkenany.views.local_stock.adapter.LocalStockLogosAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        bannersAdapter = LocalStockBannersAdapter(ClickListener {
            navigateToBroswerIntent(it.link)
        })
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
            navigateToBroswerIntent(it.link)
        })
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
        viewModel.localStockDetailsData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.errorMessage.visibility = View.GONE
                binding.foundDataLayout.visibility = View.VISIBLE
                binding.stockDataRecyclerView.visibility = View.VISIBLE
                logosAdapter.submitList(it.logos)
                bannersAdapter.submitList(it.banners)
                scrollRecyclerView(it.banners)
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

            } else {
                binding.errorMessage.apply {
                    text = "تعذر الحصول علي اي بيانات"
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
        dateFormat = SimpleDateFormat(myFormat, Locale.US).format(myCalendar.time)
        viewModel.getLocalStockDetailsData(
            args.id,
            dateFormat,
            args.sectorType.toString(),
            feedId, companyId
        )
    }

    private fun scrollRecyclerView(banners: List<LocalStockBanner?>) {
        CoroutineScope(Dispatchers.Main).launch {
            var counter = 0
            while (counter < banners.size) {
                delay(3000L).apply {
                    binding.bannersRecyclerView.smoothScrollToPosition(counter)
                }
                if (counter == banners.size - 1) {
                    counter = 0
                } else {
                    counter += 1
                }
            }
        }
    }

    private fun navigateToBroswerIntent(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}