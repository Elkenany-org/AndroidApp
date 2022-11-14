package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.shows.IShowsImplementation
import com.elkenany.entities.shows_data.ShowsListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ShowsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _showsStoreData = MutableLiveData<ShowsListData?>()
    private val _openCloseSearch = MutableLiveData(false)
    private val _loading = MutableLiveData(false)
    private val api = IShowsImplementation()

    val showsStoreData: LiveData<ShowsListData?> get() = _showsStoreData
    val loading: LiveData<Boolean> get() = _loading
    val openCloseSearch: LiveData<Boolean> get() = _openCloseSearch

    private var isOpened = false

    fun getAllAdsStoreData(
        sectorType: String?,
        search: String?,
        sort: Long?,
        cityId: Long?,
        countryId: Long?,
    ) {
        _loading.value = true
        uiScope.launch {
            _showsStoreData.value =
                api.getAllShowsListData(sectorType, search, sort, cityId, countryId)
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