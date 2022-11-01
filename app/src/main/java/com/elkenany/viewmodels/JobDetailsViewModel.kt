package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.auth.AuthImplementation
import com.elkenany.api.recruitment.IRecruitmentImplementation
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.recruitment.JobDetailsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class JobDetailsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _responseData = MutableLiveData<JobDetailsData?>()
    private val _exception = MutableLiveData<Int?>()
    private val _loading = MutableLiveData(false)
    private val api = IRecruitmentImplementation()


    val responseData: LiveData<JobDetailsData?> get() = _responseData
    val loading: LiveData<Boolean> get() = _loading
    val exception: LiveData<Int?> get() = _exception

    fun getJobDetailsData(
        jobId: Int?,
    ) {
        _loading.value = true
        uiScope.launch {
            val response = api.getJobDetailsData(jobId)
            exceptionChecker(response)
            _loading.value = false
        }
    }

    private fun exceptionChecker(response: GenericEntity<JobDetailsData?>) {
        if (response.error != null) {
            _exception.value = response.error.toInt()
        } else {
            if (response.data == null) {
                _exception.value = 404
            } else {
                _exception.value = 200
            }
            _responseData.value = response.data
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun applyForJob() {
        if (AuthImplementation.userApiToken == null) {
            _exception.value = 401
        } else {
            _exception.value = 1
        }
    }
}