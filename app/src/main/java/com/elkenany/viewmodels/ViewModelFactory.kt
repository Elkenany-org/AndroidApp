@file:Suppress("UNCHECKED_CAST")

package com.elkenany.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <V : ViewModel> create(modelClass: Class<V>): V {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel() as V
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel() as V
            modelClass.isAssignableFrom(PopUpAdViewModel::class.java) -> PopUpAdViewModel() as V
            modelClass.isAssignableFrom(HomeSectorViewModel::class.java) -> HomeSectorViewModel() as V
            modelClass.isAssignableFrom(HomeServiceViewModel::class.java) -> HomeServiceViewModel() as V
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel() as V
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel() as V
            modelClass.isAssignableFrom(LocalStockViewModel::class.java) -> LocalStockViewModel() as V
            modelClass.isAssignableFrom(NewViewModel::class.java) -> NewViewModel() as V
            modelClass.isAssignableFrom(StoreViewModel::class.java) -> StoreViewModel() as V
            modelClass.isAssignableFrom(GuideViewModel::class.java) -> GuideViewModel() as V
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel() as V
            modelClass.isAssignableFrom(MainMenuViewModel::class.java) -> MainMenuViewModel() as V
            modelClass.isAssignableFrom(NewsDetailsViewModel::class.java) -> NewsDetailsViewModel() as V
            modelClass.isAssignableFrom(LocalStockDetailsViewModel::class.java) -> LocalStockDetailsViewModel() as V
            modelClass.isAssignableFrom(GuideCompaniesViewModel::class.java) -> GuideCompaniesViewModel() as V
            modelClass.isAssignableFrom(NotificationViewModel::class.java) -> NotificationViewModel() as V
            modelClass.isAssignableFrom(AdDetailsViewModel::class.java) -> AdDetailsViewModel() as V
            modelClass.isAssignableFrom(CompanyViewModel::class.java) -> CompanyViewModel() as V
            modelClass.isAssignableFrom(StatisticsViewModel::class.java) -> StatisticsViewModel() as V
            modelClass.isAssignableFrom(AboutViewModel::class.java) -> AboutViewModel() as V
            modelClass.isAssignableFrom(ChatsViewModel::class.java) -> ChatsViewModel() as V
            modelClass.isAssignableFrom(MessagesViewModel::class.java) -> MessagesViewModel() as V
            modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java) -> ForgotPasswordViewModel() as V
            modelClass.isAssignableFrom(CreateAdViewModel::class.java) -> CreateAdViewModel() as V
            modelClass.isAssignableFrom(MyAdsListViewModel::class.java) -> MyAdsListViewModel() as V
            modelClass.isAssignableFrom(ShowsViewModel::class.java) -> ShowsViewModel() as V
            modelClass.isAssignableFrom(GuideMagazineViewModel::class.java) -> GuideMagazineViewModel() as V
            modelClass.isAssignableFrom(GuideMagazineDetailsViewModel::class.java) -> GuideMagazineDetailsViewModel() as V
            modelClass.isAssignableFrom(ShowsDetailsViewModel::class.java) -> ShowsDetailsViewModel() as V
            modelClass.isAssignableFrom(ShowReviewViewModel::class.java) -> ShowReviewViewModel() as V
            modelClass.isAssignableFrom(ShipsViewModel::class.java) -> ShipsViewModel() as V
            modelClass.isAssignableFrom(ShipsStatisticsViewModel::class.java) -> ShipsStatisticsViewModel() as V
            modelClass.isAssignableFrom(JobsViewModel::class.java) -> JobsViewModel() as V
            modelClass.isAssignableFrom(JobDetailsViewModel::class.java) -> JobDetailsViewModel() as V
            modelClass.isAssignableFrom(MyFavoriteJobsViewModel::class.java) -> MyFavoriteJobsViewModel() as V
            modelClass.isAssignableFrom(MyAppliedJobsViewModel::class.java) -> MyAppliedJobsViewModel() as V
            modelClass.isAssignableFrom(ApplyToJobViewModel::class.java) -> ApplyToJobViewModel() as V
            modelClass.isAssignableFrom(AddNewJobViewModel::class.java) -> AddNewJobViewModel() as V
            modelClass.isAssignableFrom(ApplicantsViewModel::class.java) -> ApplicantsViewModel() as V
            modelClass.isAssignableFrom(ApplicantDetailsViewModel::class.java) -> ApplicantDetailsViewModel() as V
            modelClass.isAssignableFrom(TendersViewModel::class.java) -> TendersViewModel() as V
            modelClass.isAssignableFrom(TenderSubSectionsViewModel::class.java) -> TenderSubSectionsViewModel() as V
            modelClass.isAssignableFrom(TendersDetailsViewModel::class.java) -> TendersDetailsViewModel() as V
            modelClass.isAssignableFrom(SponsersViewModel::class.java) -> SponsersViewModel() as V
            modelClass.isAssignableFrom(AboutUsViewModel::class.java) -> AboutUsViewModel() as V

            else -> throw IllegalArgumentException("Unknown ViewModel")
        }
    }
}