package com.example.elkenany.views.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentForgotPasswordBinding
import com.example.elkenany.viewmodels.ForgotPasswordViewModel
import com.example.elkenany.viewmodels.ViewModelFactory

class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ForgotPasswordViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[ForgotPasswordViewModel::class.java]

        binding.confirmBtn.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            if (email.isEmpty()) {
                binding.emailInput.apply {
                    error = "يرجي ادخال الإيميل"
                    requestFocus()
                }
            } else {
                viewModel.getVerificationCodeForEmail(email)
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    loadingProgressbar.visibility = View.VISIBLE
                    confirmBtn.visibility = View.GONE
                }
            } else {
                binding.apply {
                    loadingProgressbar.visibility = View.GONE
                    confirmBtn.visibility = View.VISIBLE
                }
            }
        }

        viewModel.passwordRecoveryCode.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(),
                    "your code is : ${it.code.toString()}",
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "please try again", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

}