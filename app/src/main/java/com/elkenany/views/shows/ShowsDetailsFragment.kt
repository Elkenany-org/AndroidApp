package com.elkenany.views.shows

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.FragmentShowsDetailsBinding
import com.elkenany.databinding.ImageDialogItemBinding
import com.elkenany.entities.shows_data.AttendanceStateData
import com.elkenany.utilities.GlobalUiFunctions.Companion.onsharingdata
import com.elkenany.viewmodels.ShowsDetailsViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.shows.adapter.ShowsImageAdapter


class ShowsDetailsFragment : Fragment() {
    private lateinit var binding: FragmentShowsDetailsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewmodel: ShowsDetailsViewModel
    private lateinit var showsImageAdapter: ShowsImageAdapter
    private val args: ShowsDetailsFragmentArgs by navArgs()
    private val _attendanceState = listOf(
        AttendanceStateData(1, "مهتم بالذهاب", false),
        AttendanceStateData(2, "غير مهتم بالذهاب", false)
    )
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private var parent: ViewGroup? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_shows_details,
            container,
            false)
        parent = container
        viewModelFactory = ViewModelFactory()
        viewmodel = ViewModelProvider(this, viewModelFactory)[ShowsDetailsViewModel::class.java]
        viewmodel.getShowDetailsData(args.id)
        binding.appBarTitle.text = args.name
        showsImageAdapter = ShowsImageAdapter(ClickListener {
            openImageDialog(it.image!!)
        })
        binding.moreImagesRecyclerview.adapter = showsImageAdapter
        binding.shareShowBtn.setOnClickListener {
            onsharingdata("https://elkenany.com/#/gallery/showes/${args.id}/about",
                requireActivity())
        }
        binding.reviewsBtn.setOnClickListener {
            requireView().findNavController()
                .navigate(ShowsDetailsFragmentDirections.actionShowsDetailsFragmentToShowReviewFragment(
                    args.id))
        }
        binding.attendanceStateAutoCompelete.setText(R.string.going_state_zero_title)
        //viewModel observers
        viewmodel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    scrollView.visibility = View.GONE
                    loadingProgressbar.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                }
            } else {
                binding.apply {
                    loadingProgressbar.visibility = View.GONE
                }
            }
        }
        viewmodel.showsDetails.observe(viewLifecycleOwner) {
            val attendancelist =
                _attendanceState.map { newList -> newList.name }.toList()
            arrayAdapter = ArrayAdapter<String>(
                requireContext(),
                R.layout.array_adapter_item,
                attendancelist
            )
            binding.attendanceStateAutoCompelete.setAdapter(arrayAdapter)
            binding.attendanceStateAutoCompelete.setOnItemClickListener { adapterView, _, position, _ ->
                val id = _attendanceState[position].id
                binding.attendanceStateAutoCompelete.hint = adapterView.getItemAtPosition(position)
                    .toString()
                viewmodel.postGoingState(args.id, id!!)
            }
            Log.i("ShowsDetailsData", args.id.toString() + it.toString())
            if (it != null) {
                binding.apply {
                    scrollView.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                    showsImageAdapter.submitList(it.images)
                    data = it
                    intrestedPeople = "المشاركون ${it.viewCount}"
                }
            } else {
                binding.apply {
                    scrollView.visibility = View.GONE
                    errorMessage.visibility = View.VISIBLE
                }
            }
        }

        viewmodel.goingState.observe(viewLifecycleOwner) {
            when (it) {
                200 -> Toast.makeText(requireContext(), "تم التعديل بنجاح", Toast.LENGTH_SHORT)
                    .show()
                401 -> {
                    Toast.makeText(requireContext(),
                        "برجاء تسجيل الدخول أولا",
                        Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(),
                        "تعذر تحديث حالة الحضور لديكم",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }


        return binding.root
    }

    private fun openImageDialog(image: String) {
        val dialogBinding = ImageDialogItemBinding.inflate(layoutInflater)
        dialogBinding.image = image
        val dialog = Dialog(requireActivity())
        dialog.setCancelable(true)
        Log.i("imageUrl", dialogBinding.image.toString())
        dialog.setContentView(dialogBinding.root)
        dialog.show()

    }

}