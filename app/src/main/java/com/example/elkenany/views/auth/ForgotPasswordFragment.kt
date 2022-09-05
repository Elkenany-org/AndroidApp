package com.example.elkenany.views.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private lateinit var email: String
    private lateinit var password:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[ForgotPasswordViewModel::class.java]

        binding.confirmBtn.setOnClickListener {
            email = binding.emailInput.text.toString().trim()
            if (email.isEmpty()) {
                binding.emailInput.apply {
                    error = "يرجي ادخال الإيميل"
                    requestFocus()
                }
            } else {
                viewModel.getVerificationCodeForEmail(email)
            }
        }
        binding.changePasswordBtn.setOnClickListener {
            password = binding.passwordInput.text.toString().trim()
            //ToDo --> implement send new password function
        }
        binding.resendEmailMessage.setOnClickListener {
            viewModel.getVerificationCodeForEmail(email)
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    loadingProgressbar1.visibility = View.VISIBLE
                    loadingProgressbar2.visibility = View.VISIBLE
                    confirmBtn.visibility = View.GONE
                    changePasswordBtn.visibility = View.GONE
                }
            } else {
                binding.apply {
                    loadingProgressbar1.visibility = View.GONE
                    loadingProgressbar2.visibility = View.GONE
                    changePasswordBtn.visibility = View.VISIBLE
                    confirmBtn.visibility = View.VISIBLE
                }
            }
        }

        viewModel.passwordRecoveryCode.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    confirmEmailLayout.visibility = View.GONE
                    confirmCodeLayout.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    confirmEmailLayout.visibility = View.VISIBLE
                    confirmCodeLayout.visibility = View.GONE
                }
            }
        }
        return binding.root
    }

}