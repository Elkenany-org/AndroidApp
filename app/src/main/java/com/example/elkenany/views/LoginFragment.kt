package com.example.elkenany.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var email: String
    private lateinit var password: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        //showing app logo inside ImageView
        binding.appImage.setImageResource(R.drawable.logo)
        binding.signInBtn.setOnClickListener {
            email = binding.emailInput.text.toString().trim()
            password = binding.passwordInput.text.toString().trim()
            if (email.isEmpty() && password.isEmpty()) {
                Toast.makeText(this.context, "يرجي ادخال البيانات كاملة", Toast.LENGTH_LONG).show()
            } else if (email.isEmpty()) {
                binding.emailInput.error = "يرجي ادخال الايميل"
                binding.emailInput.requestFocus()
            } else if (password.isEmpty()) {
                binding.passwordInput.error = "يرجي ادخال الرقم السري"
                binding.passwordInput.requestFocus()
            } else {
                //ToDo --> implement viewModel.SigninWithEmailAndPassword function here
            }

        }
        binding.forgotPasswordBtn.setOnClickListener {
            //ToDo --> implement viewModel.ForgotPassword function here
        }
        binding.googleSigninBtn.setOnClickListener {
            //ToDo --> implement viewModel.SignInWithGoogle function here
        }
        binding.facebookSigninBtn.setOnClickListener {
            //ToDo --> implement viewModel.SignInWithFacebook function here
        }
        binding.skipBtn.setOnClickListener {
            //ToDo --> implement navigation to HomeFragment here
        }
        binding.createAccountBtn.setOnClickListener {
            //ToDo --> implement navigation to SignUpFragment here
        }

        return binding.root
    }


}