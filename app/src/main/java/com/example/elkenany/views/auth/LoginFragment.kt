@file:Suppress("DEPRECATION")

package com.example.elkenany.views.auth

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
import com.example.elkenany.R
import com.example.elkenany.api.retrofit_configs.GoogleAuth_Config.Companion.gso
import com.example.elkenany.databinding.FragmentLoginBinding
import com.example.elkenany.viewmodels.LoginViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task


class LoginFragment : Fragment() {
    private lateinit var facebookCallbackManager: CallbackManager
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
        viewModel.initViewModel(requireContext())
        facebookCallbackManager = CallbackManager.Factory.create()
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
//        binding.passwordVisibiltyBtn.setOnClickListener {
//            binding.passwordInput.inputType = InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD
//        }
        binding.forgotPasswordBtn.setOnClickListener {
            requireView().findNavController()
                .navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
        }
        binding.googleSigninBtn.setOnClickListener {
            signInWithGoogle()
        }
        binding.facebookSigninBtn.setOnClickListener {
            viewModel.signInWithFaceBook(this, facebookCallbackManager)
        }
        binding.skipBtn.setOnClickListener {
            //navigation to HomeFragment here
            viewModel.signInWithGuestAccount()
        }
        binding.createAccountBtn.setOnClickListener {
            //navigation to SignUpFragment here
            requireView().findNavController()
                .navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }


        // ViewModel Observers

        viewModel.login.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it) {
                    // navigation to Home screen
                    requireView().findNavController()
                        .navigate(R.id.action_loginFragment_to_homeFragment)
                }
            }
        }
        viewModel.exception.observe(viewLifecycleOwner) {
            when (it) {
                200 -> Toast.makeText(context, "تم تسجيل الدخول", Toast.LENGTH_LONG).show()
                404 -> Toast.makeText(context, "الأيميل غير موجود", Toast.LENGTH_LONG).show()
                406 -> Toast.makeText(context, "كلمة المرور خاطئة", Toast.LENGTH_LONG).show()
                else -> Toast.makeText(context, "تعذر تسجيل الدخول", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                // ToDo --> implement progressBar to load and remove the signin btn from the fragment
                binding.apply {
                    loadingProgressbar.visibility = View.VISIBLE
                    signInBtn.visibility = View.GONE
                    emailInput.isEnabled = false
                    skipBtn.visibility = View.INVISIBLE
                    googleSigninBtn.isEnabled = false
                    facebookSigninBtn.isEnabled = false
                    passwordInput.isEnabled = false
                }

            } else {
                // ToDo --> implement signin btn  to load and remove progressBar from the fragment
                binding.apply {
                    loadingProgressbar.visibility = View.GONE
                    signInBtn.visibility = View.VISIBLE
                    skipBtn.visibility = View.VISIBLE
                    googleSigninBtn.isEnabled = true
                    facebookSigninBtn.isEnabled = true
                    emailInput.isEnabled = true
                    passwordInput.isEnabled = true
                }
            }
        }

        return binding.root
    }

    private fun signInWithGoogle() {
        val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
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