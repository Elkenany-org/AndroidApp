package com.elkenany.api.retrofit_configs

import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Suppress("ClassName")
class GoogleAuth_Config {
    companion object {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("319259960376-k1ri3ii3aeh7c1fg237tvqm425joahb5.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }
}