package com.elkenany.api.retrofit_configs

import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GoogleAuth_Config {
    companion object{
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }
}