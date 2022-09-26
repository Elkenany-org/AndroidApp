@file:Suppress("DEPRECATION")

package com.elkenany.views.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.elkenany.api.retrofit_configs.GoogleAuth_Config.Companion.gso
import com.elkenany.databinding.FragmentLoginBinding
import com.elkenany.viewmodels.LoginViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

const val SHARED_PREFRENCES = "user_credentials"

class LoginFragment : Fragment() {
    private lateinit var facebookCallbackManager: CallbackManager
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private var email: String? = null
    private var password: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        sharedPreferences =
            requireActivity().getSharedPreferences(SHARED_PREFRENCES, Context.MODE_PRIVATE)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
//        viewModel.initViewModel(requireContext(), requireActivity())
        loadSavedData()
        facebookCallbackManager = CallbackManager.Factory.create()
        binding.lifecycleOwner = viewLifecycleOwner
        // showing app logo inside ImageView
        binding.signInBtn.setOnClickListener {
            email = binding.emailInput.text.toString().trim()
            password = binding.passwordInput.text.toString().trim()
            if (email.isNullOrEmpty() && password.isNullOrEmpty()) {
                Toast.makeText(this.context, "يرجي ادخال البيانات كاملة", Toast.LENGTH_LONG).show()
            } else if (email.isNullOrEmpty()) {
                binding.emailInput.error = "يرجي ادخال الايميل"
                binding.emailInput.requestFocus()
            } else if (password.isNullOrEmpty()) {
                binding.passwordInput.error = "يرجي ادخال الرقم السري"
                binding.passwordInput.requestFocus()
            } else {
                //ToDo --> implement viewModel.SigninWithEmailAndPassword function here
                viewModel.signInWithEmailAndPassword(email!!, password!!)
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
                    saveUserCredentials()
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
                300 -> {}
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

    private fun saveUserCredentials() {
        with(sharedPreferences.edit()) {
            putString("email", email)
            putString("password", password)
            apply()
        }

    }


    private fun loadSavedData() {
        email = sharedPreferences.getString("email", null)
        password = sharedPreferences.getString("password", null)
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            binding.emailInput.setText(email.toString())
            binding.passwordInput.setText(password.toString())
            viewModel.signInWithEmailAndPassword(email!!, password!!)
        } else {
            viewModel.initViewModel(requireContext())
        }
    }
}