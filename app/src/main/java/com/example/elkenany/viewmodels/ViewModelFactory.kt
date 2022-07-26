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
            modelClass.isAssignableFrom(LocalStockViewModel::class.java) -> LocalStockViewModel() as T
            modelClass.isAssignableFrom(NewViewModel::class.java) -> NewViewModel() as T
            modelClass.isAssignableFrom(StoreViewModel::class.java) -> StoreViewModel() as T
            modelClass.isAssignableFrom(GuideViewModel::class.java) -> GuideViewModel() as T
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel() as T
            modelClass.isAssignableFrom(MainMenuViewModel::class.java) -> MainMenuViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel")
        }
    }
}