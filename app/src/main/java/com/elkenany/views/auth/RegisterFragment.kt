@file:Suppress("DEPRECATION")

package com.elkenany.views.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.elkenany.R
import com.elkenany.api.retrofit_configs.GoogleAuth_Config
import com.elkenany.databinding.FragmentRegisterBinding
import com.elkenany.viewmodels.RegisterViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task


class RegisterFragment : Fragment() {
    private lateinit var facebookCallbackManager: CallbackManager
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: RegisterViewModel
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var phone: String
    private lateinit var password: String
    private lateinit var confirmPassword: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]
        facebookCallbackManager = CallbackManager.Factory.create()
        //Register button onClick
        binding.signUpBtn.setOnClickListener {
            name = binding.nameInput.text.toString().trim()
            email = binding.emailInput.text.toString().trim()
            phone = binding.phoneInput.text.toString().trim()
            password = binding.passwordInput.text.toString().trim()
            confirmPassword = binding.confirmPasswordInput.text.toString().trim()

            // applying validations
            if (name.isEmpty() && email.isEmpty() && phone.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
                Toast.makeText(this.context, "يرجي ادخال البيانات كاملة", Toast.LENGTH_LONG).show()
            } else if (name.isEmpty()) {
                binding.nameInput.requestFocus()
            } else if (email.isEmpty()) {
                binding.emailInput.requestFocus()
                binding.emailInput.error = "يرجي ادخال الايميل"
            } else if (phone.isEmpty()) {
                binding.phoneInput.requestFocus()
                binding.phoneInput.error = "يرجي ادخال رقم الهاتف"
            } else if (password.isEmpty()) {
                binding.passwordInput.requestFocus()
                binding.phoneInput.error = "يرجي ادخال الرقم السري"
            } else if (confirmPassword != password) {
                binding.confirmPasswordInput.requestFocus()
                binding.confirmPasswordInput.error = "غير متطابق"
            } else {
                // register function here
                // ToDo --> implement to receive device token here
                viewModel.registerAccount(name, email, phone, password)
            }

        }
        binding.googleSignupBtn.setOnClickListener {
            // ToDo --> implement signUpWithGoogle function here
            signUpWithGoogle()
        }
        binding.facebookSignupBtn.setOnClickListener {
            // ToDo --> implement signUpWithFacebook function here
            viewModel.signUpWithFacebook(this, facebookCallbackManager)
        }
        binding.haveAccountBtn.setOnClickListener {
            // ToDo --> implement navigation to login fragment here
            requireView().findNavController()
                .navigate(R.id.action_registerFragment_to_loginFragment)
        }

        //observers
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.signUpBtn.visibility = View.GONE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
                binding.signUpBtn.visibility = View.VISIBLE
            }
        }
        viewModel.exception.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    200 -> {
                        Toast.makeText(context, "تم التسجيل بنجاح", Toast.LENGTH_LONG).show()
                        requireView().findNavController()
                            .navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
                    }
                    406 -> Toast.makeText(context, "هذا الحساب موجود بالفعل", Toast.LENGTH_LONG)
                        .show()
                    else -> Toast.makeText(context, "تعذر انشاء حساب", Toast.LENGTH_LONG).show()
                }
            }
        }
        return binding.root
    }

    private fun signUpWithGoogle() {
        val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), GoogleAuth_Config.gso)
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1000)
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            facebookCallbackManager.onActivityResult(requestCode, resultCode, data)
        } catch (e: Exception) {
            Log.i("LoginInformation", "cancel")
        }
        if (requestCode == 1000) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            viewModel.handleSignInResult(task)
        }
    }

}