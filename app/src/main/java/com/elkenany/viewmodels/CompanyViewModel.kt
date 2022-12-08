package com.elkenany.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.auth.AuthImplementation.Companion.userApiToken
import com.elkenany.api.guide.IGuideImplementation
import com.elkenany.entities.guide.CompanyDetailsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CompanyViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _companyData = MutableLiveData<CompanyDetailsData?>()
    private val _exception = MutableLiveData<Int?>()
    private val _processing = MutableLiveData<Boolean?>()
    private val _loading = MutableLiveData(false)
    private val api = IGuideImplementation()


    val companyData: LiveData<CompanyDetailsData?> get() = _companyData
    val exception: LiveData<Int?> get() = _exception
    val loading: LiveData<Boolean> get() = _loading
    val processing: LiveData<Boolean?> get() = _processing

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

    fun rateThisCompany(rating: Long?, companyId: Long) {
        if (userApiToken == null) {
            _exception.value = 401
        } else {
            _processing.value = true
            uiScope.launch {
                val response = api.rateThisCompany("Bearer $userApiToken", companyId, rating)
                if (response != null) {
                    _exception.value = 200
                } else {
                    _exception.value = 400
                }
                Log.i("rateThisCompany", response.toString())
                _processing.value = false
            }
        }

    }

    fun onDoneRating() {
        _exception.value = null
    }
}