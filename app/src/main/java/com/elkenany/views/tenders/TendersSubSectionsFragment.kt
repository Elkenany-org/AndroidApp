package com.elkenany.views.tenders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentTendersSubSectionsBinding
import com.elkenany.entities.guide.Sector
import com.elkenany.utilities.GlobalLogicFunctions
import com.elkenany.utilities.GlobalUiFunctions
import com.elkenany.utilities.SharedPrefrencesType
import com.elkenany.viewmodels.TenderSubSectionsViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.tenders.adapters.TendersListAdapter


class TendersSubSectionsFragment : Fragment() {
    private lateinit var binding: FragmentTendersSubSectionsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: TenderSubSectionsViewModel
    private lateinit var tenderSubsectionAdapter: TendersListAdapter
    private val args: TendersSubSectionsFragmentArgs by navArgs()


    private var sectionId: Long? = null
    private var sort: Long? = null
    private var search: String? = null

    override fun onPause() {
        super.onPause()
        GlobalLogicFunctions.saveSharedPrefrences(requireActivity(),
            SharedPrefrencesType.tenders,
            sectionId.toString())
    }

    override fun onResume() {
        super.onResume()
        sectionId = GlobalLogicFunctions.retrieveSavedSharedPrefrences(requireActivity(),
            SharedPrefrencesType.tenders)?.toLong()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tenders_sub_sections,
            container,
            false
        )
        viewModelFactory = ViewModelFactory()
        viewModel =
            ViewModelProvider(this, viewModelFactory)[TenderSubSectionsViewModel::class.java]

        binding.searchBar.addTextChangedListener {
            search = it.toString()
            viewModel.getAllTendersData(sectionId, sort, search)
        }
        binding.searchBtn.setOnClickListener {
            viewModel.openCloseSearchBar()
        }
        binding.mostCommonBtn.setOnClickListener {
            sort = 1
            binding.mostCommonBtn.setTextColor(requireContext().getColor(R.color.orange))
            binding.newestBtn.setTextColor(requireContext().getColor(R.color.green))
            binding.alphabetBtn.setTextColor(requireContext().getColor(R.color.green))
            viewModel.getAllTendersData(sectionId, sort, search)
        }
        binding.alphabetBtn.setOnClickListener {
            sort = 0
            binding.alphabetBtn.setTextColor(requireContext().getColor(R.color.orange))
            binding.newestBtn.setTextColor(requireContext().getColor(R.color.green))
            binding.mostCommonBtn.setTextColor(requireContext().getColor(R.color.green))
            viewModel.getAllTendersData(sectionId, sort, search)
        }
        binding.newestBtn.setOnClickListener {
            sort = 2
            binding.newestBtn.setTextColor(requireContext().getColor(R.color.orange))
            binding.mostCommonBtn.setTextColor(requireContext().getColor(R.color.green))
            binding.alphabetBtn.setTextColor(requireContext().getColor(R.color.green))
            viewModel.getAllTendersData(sectionId, sort, search)
        }
        sectionId = args.sectionId
        viewModel.getAllTendersData(sectionId, sort, search)

        tenderSubsectionAdapter = TendersListAdapter(ClickListener {
            requireView().findNavController()
                .navigate(
                    TendersSubSectionsFragmentDirections.actionTendersSubSectionsFragmentToTenderDetailsFragment(
                        it.id!!
                    )
                )
        })
        binding.newsRecyclerView.adapter = tenderSubsectionAdapter
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    loadingProgressbar.visibility = View.VISIBLE
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
                null -> {}
                200 -> {
                    binding.errorMessage.visibility = View.GONE
                }
                401 -> {
                    Toast.makeText(
                        requireContext(),
                        "برجاء تسجيل الدخول أولا حتي تتمكن من معرفة تفاصيل المناقصات",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                402 -> {
                    binding.errorMessage.text =
                        "برجاء التحويل الي الباقة المدفوعة لمعرفة تفاصيل أكثر"
                    binding.errorMessage.visibility = View.VISIBLE
                }
                404 -> {
                    binding.errorMessage.text =
                        "لا توجد أعلانات مناقصات بعد"
                    binding.errorMessage.visibility = View.VISIBLE
                }
                else -> {
                    binding.errorMessage.text =
                        "تعذر الحصول علي المعلومات"
                    binding.errorMessage.visibility = View.VISIBLE
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
        viewModel.responseData.observe(viewLifecycleOwner) { tendersData ->
            if (tendersData != null) {
                tenderSubsectionAdapter.submitList(tendersData.data)
                val sectosList =
                    tendersData.sections.map { sector ->
                        Sector(
                            sector!!.id,
                            sector.name,
                            sector.type,
                            sector.selected
                        )
                    }.toList()
                var defaultSector: Long? = null
                binding.filtersBtn.setOnClickListener { _ ->
                    tendersData.sections.map { sector ->
                        if (sector?.selected == 1L) {
                            defaultSector = sector.id
                        }
                    }
                    GlobalUiFunctions.openFilterDialog(requireActivity(),
                        inflater,
                        defaultSector,
                        sectosList,
                        null,
                        null,
                        null,
                        ClickListener { filterData ->
                            sectionId = filterData.section?.toLong()
                            viewModel.getAllTendersData(
                                sectionId,
                                sort,
                                search
                            )
                        })
                }
            }
        }

        return binding.root
    }

}