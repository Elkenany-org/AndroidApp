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
    private val _loading = MutableLiveData(false)
    private val api = IShowsImplementation()

    val showsStoreData: LiveData<ShowsListData?> get() = _showsStoreData
    val loading: LiveData<Boolean> get() = _loading


    fun getAllAdsStoreData(
        sectorType: String?,
        search: String?,
        sort: Long?,
        cityId: Long?,
        countryId: Long?,
    ) {
        _loading.value = true
        uiScope.launch {
            // ToDo --> implementing getHomeStockData(sectorType) function here
            _showsStoreData.value =
                api.getAllShowsListData(sectorType, search, sort, cityId, countryId)
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}