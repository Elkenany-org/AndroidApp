package com.example.elkenany.views.local_stock

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentStatisticsBinding
import com.example.elkenany.viewmodels.StatisticsViewModel
import com.example.elkenany.viewmodels.ViewModelFactory


class StatisticsFragment : Fragment() {
    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: StatisticsViewModel
    private val args: StatisticsFragmentArgs by navArgs()
    private lateinit var dataFrom: String
    private lateinit var dataTo: String
    private lateinit var item: String
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[StatisticsViewModel::class.java]
        Log.i("statistics args", args.toString())
        viewModel.getLocalStockDetailsData(args.id, args.type, "", "")

        viewModel.statisticsData.observe(viewLifecycleOwner) {
            if (it != null) {
                val list: List<String> = buildList {
                    for (i in it.listMembers) {
                        this.add(i!!.name.toString())
                    }
                }
                adapter = ArrayAdapter<String>(requireContext(), R.layout.array_adapter_item, list)
                binding.companyAutoCompelete.setAdapter(adapter)

                Log.i("statisticsData", it.toString())
            } else {
                Log.i("statisticsData", "failed to get any data")
            }
        }
        return binding.root
    }

}