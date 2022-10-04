package com.elkenany.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.guide_magazine.IMagazineImplementation
import com.elkenany.entities.guide_magazine.MagazineDetailsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GuideMagazineDetailsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _magazineData = MutableLiveData<MagazineDetailsData?>()
    private val _loading = MutableLiveData(false)
    private val api = IMagazineImplementation()


    val magazineData: LiveData<MagazineDetailsData?> get() = _magazineData
    val loading: LiveData<Boolean> get() = _loading

    fun getGuideDetailsData(
        id: Long?,
    ) {
        _loading.value = true
        uiScope.launch {
            // ToDo --> implementing getHomeStockData(sectorType) function here
            Log.i("Magazine id", id.toString())
            _magazineData.value = api.getMagazineDetailsData(id)
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}