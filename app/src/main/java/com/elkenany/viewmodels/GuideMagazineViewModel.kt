package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.guide_magazine.IMagazineImplementation
import com.elkenany.entities.guide_magazine.MagazineData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GuideMagazineViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _magazineData = MutableLiveData<MagazineData?>()
    private val _loading = MutableLiveData(false)
    private val api = IMagazineImplementation()


    val magazineData: LiveData<MagazineData?> get() = _magazineData
    val loading: LiveData<Boolean> get() = _loading

    fun getGuideData(
        sectorType: String?,
        sort: Long?,
        cityId: Long?,
        search: String?,
    ) {
        _loading.value = true
        uiScope.launch {
            // ToDo --> implementing getHomeStockData(sectorType) function here
            _magazineData.value = api.getAllMagazineData(sectorType, sort, cityId, search)
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}