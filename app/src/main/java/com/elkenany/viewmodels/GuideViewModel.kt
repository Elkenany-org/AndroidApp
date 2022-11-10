package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.guide.IGuideImplementation
import com.elkenany.entities.guide.GuideData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GuideViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _guideData = MutableLiveData<GuideData?>()
    private val _loading = MutableLiveData(false)
    private val api = IGuideImplementation()


    val guideData: LiveData<GuideData?> get() = _guideData
    val loading: LiveData<Boolean> get() = _loading

    fun getGuideData(sectorType: Int?, search: String?) {
        _loading.value = true
        uiScope.launch {
            // ToDo --> implementing getHomeStockData(sectorType) function here
            _guideData.value = api.getAllGuideData(sectorType, search)
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}