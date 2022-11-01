package com.elkenany.views.recruitment

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.elkenany.R
import com.elkenany.databinding.FragmentApplyJobBinding
import com.elkenany.viewmodels.ApplyToJobViewModel
import com.elkenany.viewmodels.ViewModelFactory
import java.io.File


class ApplyToJobFragment : Fragment() {
    private lateinit var binding: FragmentApplyJobBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ApplyToJobViewModel
    private val args: ApplyToJobFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_apply_job, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[ApplyToJobViewModel::class.java]
        binding.apply {
            jobImage = args.jobImage
            companyName = args.companyName
            jobType = args.jobWorkHours
            jobTitle = args.jobTitle
            experience = args.experience
            expectedSalary = args.salary
        }
        binding.attachCvBtn.setOnClickListener {
            getCvFromStorage()
        }
        return binding.root
    }

    @SuppressLint("IntentReset")
    private fun getCvFromStorage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "*/pdf"
        startActivityForResult(intent, 1)
    }


    @Deprecated("Deprecated in Java")
    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            1212 -> if (resultCode == RESULT_OK) {
                // Get the Uri of the selected file
                val uri = data!!.data
                val uriString = uri.toString()
                val myFile = File(uriString)
                val path = myFile.absolutePath
            }
        }
    }
}