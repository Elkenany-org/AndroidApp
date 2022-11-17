package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.guide.IGuideImplementation
import com.elkenany.entities.guide.CompaniesData
import com.elkenany.entities.guide.GuideFiltersData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GuideCompaniesViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _companiesData = MutableLiveData<CompaniesData?>()
    private val _guideFilter = MutableLiveData<GuideFiltersData?>()
    private val _loading = MutableLiveData(false)
    private val _openCloseSearch = MutableLiveData(false)
    private val api = IGuideImplementation()


    val companiesDataData: LiveData<CompaniesData?> get() = _companiesData
    val guideFilter: LiveData<GuideFiltersData?> get() = _guideFilter
    val loading: LiveData<Boolean> get() = _loading
    val openCloseSearch: LiveData<Boolean> get() = _openCloseSearch

    private var isOpened = false

    fun getCompaniesGuideData(sectionId: Long,subSectionId: Long, search: String?, countryId: Long?, cityId: Long?) {
        _loading.value = true
        uiScope.launch {
            _guideFilter.value = api.getGuideFilterData(sectionId)
            _companiesData.value = api.getAllCompaniesData(subSectionId, search, countryId, cityId)
            _loading.value = false
        }
    }

    fun openCloseSearchBar() {
        isOpened = !isOpened
        _openCloseSearch.value = isOpened
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}