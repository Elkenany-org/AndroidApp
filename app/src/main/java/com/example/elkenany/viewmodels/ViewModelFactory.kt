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
            modelClass.isAssignableFrom(NewsDetailsViewModel::class.java) -> NewsDetailsViewModel() as T
            modelClass.isAssignableFrom(LocalStockDetailsViewModel::class.java) -> LocalStockDetailsViewModel() as T
            modelClass.isAssignableFrom(GuideCompaniesViewModel::class.java) -> GuideCompaniesViewModel() as T
            modelClass.isAssignableFrom(NotificationViewModel::class.java) -> NotificationViewModel() as T
            modelClass.isAssignableFrom(AdDetailsViewModel::class.java) -> AdDetailsViewModel() as T
            modelClass.isAssignableFrom(CompanyViewModel::class.java) -> CompanyViewModel() as T
            modelClass.isAssignableFrom(StatisticsViewModel::class.java) -> StatisticsViewModel() as T
            modelClass.isAssignableFrom(AboutViewModel::class.java) -> AboutViewModel() as T
            modelClass.isAssignableFrom(ChatsViewModel::class.java) -> ChatsViewModel() as T
            modelClass.isAssignableFrom(MessagesViewModel::class.java) -> MessagesViewModel() as T
            modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java) -> ForgotPasswordViewModel() as T
            modelClass.isAssignableFrom(CreateAdViewModel::class.java) -> CreateAdViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel")
        }
    }
}