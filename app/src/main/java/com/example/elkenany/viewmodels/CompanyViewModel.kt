package com.example.elkenany.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.guide.IGuideImplementation
import com.example.elkenany.entities.guide.CompanyDetailsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CompanyViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _companyData = MutableLiveData<CompanyDetailsData?>()
    private val _loading = MutableLiveData(false)
    private val api = IGuideImplementation()


    val companyData: LiveData<CompanyDetailsData?> get() = _companyData
    val loading: LiveData<Boolean> get() = _loading

    fun getCompaniesGuideData(id: Long) {
        _loading.value = true
        uiScope.launch {
            _companyData.value = api.getCompanyData(id)
            Log.i("companyData", _companyData.value.toString())
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}