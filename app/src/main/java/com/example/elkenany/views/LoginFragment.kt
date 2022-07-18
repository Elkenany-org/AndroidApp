package com.example.elkenany.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentLoginBinding
import com.example.elkenany.viewmodels.LoginViewModel
import com.example.elkenany.viewmodels.ViewModelFactory

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: LoginViewModel
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        // showing app logo inside ImageView
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
                viewModel.signInWithEmailAndPassword(email, password)
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
            //navigation to HomeFragment here
            viewModel.signInWithGuestAccount()
        }
        binding.createAccountBtn.setOnClickListener {
            //navigation to SignUpFragment here
            view!!.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }


        // ViewModel Observers

        viewModel.login.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it) {
                    // ToDo--> implement navigation to Home screen
                } else {
                    Toast.makeText(context, "تعذر تسجيل الدخول", Toast.LENGTH_LONG).show()
                }
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                // ToDo --> implement progressBar to load and remove the signin btn from the fragment
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.signInBtn.visibility = View.GONE
            } else {
                // ToDo --> implement signin btn  to load and remove progressBar from the fragment
                binding.loadingProgressbar.visibility = View.GONE
                binding.signInBtn.visibility = View.VISIBLE
            }
        }

        return binding.root
    }


}