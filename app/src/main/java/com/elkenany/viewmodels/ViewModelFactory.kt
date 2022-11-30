@file:Suppress("UNCHECKED_CAST")

package com.elkenany.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel() as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel() as T
            modelClass.isAssignableFrom(PopUpAdViewModel::class.java) -> PopUpAdViewModel() as T
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
            modelClass.isAssignableFrom(MyAdsListViewModel::class.java) -> MyAdsListViewModel() as T
            modelClass.isAssignableFrom(ShowsViewModel::class.java) -> ShowsViewModel() as T
            modelClass.isAssignableFrom(GuideMagazineViewModel::class.java) -> GuideMagazineViewModel() as T
            modelClass.isAssignableFrom(GuideMagazineDetailsViewModel::class.java) -> GuideMagazineDetailsViewModel() as T
            modelClass.isAssignableFrom(ShowsDetailsViewModel::class.java) -> ShowsDetailsViewModel() as T
            modelClass.isAssignableFrom(ShowReviewViewModel::class.java) -> ShowReviewViewModel() as T
            modelClass.isAssignableFrom(ShipsViewModel::class.java) -> ShipsViewModel() as T
            modelClass.isAssignableFrom(ShipsStatisticsViewModel::class.java) -> ShipsStatisticsViewModel() as T
            modelClass.isAssignableFrom(JobsViewModel::class.java) -> JobsViewModel() as T
            modelClass.isAssignableFrom(JobDetailsViewModel::class.java) -> JobDetailsViewModel() as T
            modelClass.isAssignableFrom(MyFavoriteJobsViewModel::class.java) -> MyFavoriteJobsViewModel() as T
            modelClass.isAssignableFrom(MyAppliedJobsViewModel::class.java) -> MyAppliedJobsViewModel() as T
            modelClass.isAssignableFrom(ApplyToJobViewModel::class.java) -> ApplyToJobViewModel() as T
            modelClass.isAssignableFrom(AddNewJobViewModel::class.java) -> AddNewJobViewModel() as T
            modelClass.isAssignableFrom(ApplicantsViewModel::class.java) -> ApplicantsViewModel() as T
            modelClass.isAssignableFrom(ApplicantDetailsViewModel::class.java) -> ApplicantDetailsViewModel() as T
            modelClass.isAssignableFrom(TendersViewModel::class.java) -> TendersViewModel() as T
            modelClass.isAssignableFrom(TenderSubSectionsViewModel::class.java) -> TenderSubSectionsViewModel() as T
            modelClass.isAssignableFrom(TendersDetailsViewModel::class.java) -> TendersDetailsViewModel() as T
            modelClass.isAssignableFrom(SponsersViewModel::class.java) -> SponsersViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel")
        }
    }
}