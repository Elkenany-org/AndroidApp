package com.elkenany.views.guide

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentGuideCompaniesBinding
import com.elkenany.entities.guide.GuideFiltersData
import com.elkenany.utilities.GlobalUiFunctions
import com.elkenany.utilities.GlobalUiFunctions.Companion.enableImageSlider
import com.elkenany.utilities.GlobalUiFunctions.Companion.navigateToBroswerIntent
import com.elkenany.viewmodels.GuideCompaniesViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.guide.adapter.CompaniesAdapter
import com.elkenany.views.local_stock.adapter.LocalStockLogosAdapter


class GuideCompaniesFragment : Fragment() {
    private lateinit var binding: FragmentGuideCompaniesBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: GuideCompaniesViewModel
    private var search: String? = ""
    private var countryId: Long? = null
    private var cityId: Long? = null
    private var country: String? = "الدوله"
    private var city: String? = "المدينه"
    private var guideFilters: GuideFiltersData? = null
    private lateinit var logosAdapter: LocalStockLogosAdapter
    private lateinit var companiesAdapter: CompaniesAdapter
    private val args: GuideCompaniesFragmentArgs by navArgs()
    override fun onResume() {
        super.onResume()
        binding.companyAutoCompelete.setText(city)
        binding.productAutoCompelete.setText(country)
        viewModel.getCompaniesGuideData(args.sectionId, args.id, search, countryId, cityId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_guide_companies, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[GuideCompaniesViewModel::class.java]
        binding.appBarTitle.text = args.name
        binding.companyAutoCompelete.setText(city)
        binding.productAutoCompelete.setText(country)
//        viewModel.getCompaniesGuideData(args.id, search)
        binding.searchBar.addTextChangedListener {
            search = it.toString()
            viewModel.getCompaniesGuideData(args.sectionId, args.id, search, countryId, cityId)
        }
        binding.searchBtn.setOnClickListener {
            viewModel.openCloseSearchBar()
        }
        logosAdapter = LocalStockLogosAdapter(ClickListener {
            navigateToBroswerIntent(it.link, requireActivity())
        })
        binding.logosRecyclerView.adapter = logosAdapter
        companiesAdapter = CompaniesAdapter(ClickListener {
            requireView().findNavController().navigate(
                GuideCompaniesFragmentDirections.actionGuideCompaniesFragmentToCompanyFragment(
                    it.id!!,
                    it.name!!
                )
            )
        })
        binding.companyListRecyclerView.apply {
            adapter = companiesAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }
        viewModel.guideFilter.observe(viewLifecycleOwner) {
            if (it != null) {
                guideFilters = it
            } else {
                binding.apply {
                    productBtn.visibility = View.GONE
                    countryBtn.visibility = View.GONE
                }
            }
        }
        viewModel.openCloseSearch.observe(viewLifecycleOwner) {
            if (it) {
                binding.searchBarCard.layoutAnimation =
                    AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation)
                binding.searchBarCard.visibility = View.VISIBLE
            } else {
                binding.searchBarCard.visibility = View.GONE
            }
        }
        viewModel.companiesDataData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.companyListRecyclerView.visibility = View.VISIBLE
                binding.errorMessage.visibility = View.GONE
                //submitting lists to its own adapters
                enableImageSlider(it.banners, binding.bannersImageSlider, requireActivity())
                logosAdapter.submitList(it.logos)
                companiesAdapter.submitList(it.compsort + it.data)
                binding.filtersBtn.setOnClickListener { view ->
                    GlobalUiFunctions.openFilterDialog(requireActivity(),
                        inflater,
                        it.sectors,
                        null,
                        guideFilters?.countries,
                        guideFilters?.cities,
                        ClickListener { filterData ->
                            Log.i("filterData", filterData.toString())
                            viewModel.getCompaniesGuideData(
                                args.sectionId,
                                filterData.section!!.toLong(),
                                search,
                                filterData.country?.toLong(),
                                filterData.city?.toLong()
                            )
                        })
                }

            } else {
                binding.companyListRecyclerView.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
//                binding.fodderExternalLayout.visibility = View.GONE
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.companyListRecyclerView.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
            }
        }
        return binding.root
    }

}