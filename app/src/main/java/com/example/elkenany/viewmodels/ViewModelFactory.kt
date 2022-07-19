@file:Suppress("UNCHECKED_CAST")

package com.example.elkenany.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel() as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel() as T
            modelClass.isAssignableFrom(HomeSectorViewModel::class.java) -> HomeSectorViewModel() as T
            modelClass.isAssignableFrom(HomeServiceViewModel::class.java) -> HomeServiceViewModel() as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel() as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel")
        }
    }
}