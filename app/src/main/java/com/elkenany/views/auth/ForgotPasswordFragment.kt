package com.elkenany.views.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.elkenany.R
import com.elkenany.databinding.FragmentForgotPasswordBinding
import com.elkenany.viewmodels.ForgotPasswordViewModel
import com.elkenany.viewmodels.ViewModelFactory

class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ForgotPasswordViewModel
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var code: String

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
            code = binding.codeInput.text.toString().trim()
            if (password.isEmpty() && code.isEmpty()) {
                Toast.makeText(requireContext(), "برجاء ادخال البيانات", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                binding.apply {
                    passwordInput.requestFocus()
                    passwordInput.error = "برجاء ادخال الرقم السري الجديد"
                }
            } else if (code.isEmpty()) {
                binding.apply {
                    codeInput.requestFocus()
                    codeInput.error = "برجاء ادخال الكود التأكيد"
                }
            } else {
                viewModel.resetPassword(email, code, password)
            }
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
        viewModel.resetPasswordSuccess.observe(viewLifecycleOwner) {
            if (it == true) {
                requireView().findNavController()
                    .navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToHomeFragment())
            } else if (it == false) {
                Toast.makeText(requireContext(),
                    "فشل في اتمام العملية برجاء المحاولة مرة اخري",
                    Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.passwordRecoveryCode.observe(viewLifecycleOwner) {
            when (it) {
                200 -> {
                    binding.apply {
                        confirmEmailLayout.visibility = View.GONE
                        confirmCodeLayout.visibility = View.VISIBLE
                    }
                }
                404 -> {
                    binding.apply {
                        confirmEmailLayout.visibility = View.VISIBLE
                        confirmCodeLayout.visibility = View.GONE
                    }
                    Toast.makeText(requireContext(),
                        "لم يتم العثور علي الأيميل",
                        Toast.LENGTH_SHORT).show()
                }
                else -> {
                    binding.apply {
                        confirmEmailLayout.visibility = View.VISIBLE
                        confirmCodeLayout.visibility = View.GONE
                    }
                    Toast.makeText(requireContext(),
                        "تعذر إتمام العملية",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }

}