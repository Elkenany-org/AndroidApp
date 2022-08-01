package com.example.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.guide.IGuideImplementation
import com.example.elkenany.entities.guide.CompaniesData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GuideCompaniesViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _companiesData = MutableLiveData<CompaniesData?>()
    private val _loading = MutableLiveData(false)
    private val api = IGuideImplementation()


    val companiesDataData: LiveData<CompaniesData?> get() = _companiesData
    val loading: LiveData<Boolean> get() = _loading

    fun getCompaniesGuideData(sectionId: Long,search:String?) {
        _loading.value = true
        uiScope.launch {
            // ToDo --> implementing getHomeStockData(sectorType) function here
            _companiesData.value = api.getAllCompaniesData(sectionId,search)
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}