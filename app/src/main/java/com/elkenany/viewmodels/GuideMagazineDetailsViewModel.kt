package com.elkenany.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.auth.AuthImplementation
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
    private val _exception = MutableLiveData<Int?>()
    private val _processing = MutableLiveData<Boolean?>()
    private val api = IMagazineImplementation()


    val magazineData: LiveData<MagazineDetailsData?> get() = _magazineData
    val loading: LiveData<Boolean> get() = _loading
    val exception: LiveData<Int?> get() = _exception
    val processing: LiveData<Boolean?> get() = _processing

    fun getGuideDetailsData(
        id: Long?,
    ) {
        _loading.value = true
        uiScope.launch {
            _magazineData.value = api.getMagazineDetailsData(id)
            _loading.value = false
        }
    }

    fun rateThisCompany(rating: Long?, companyId: Long) {
        if (AuthImplementation.userApiToken == null) {
            _exception.value = 401
        } else {
            _processing.value = true
            uiScope.launch {
                val response = api.rateThisMagazine("Bearer ${AuthImplementation.userApiToken}",
                    companyId,
                    rating)
                if (response.data != null && response.error.isNullOrEmpty()) {
                    _exception.value = 200
                } else {
                    _exception.value = 400
                }
                _processing.value = false
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun onDoneRating() {
        _exception.value = null
    }
}