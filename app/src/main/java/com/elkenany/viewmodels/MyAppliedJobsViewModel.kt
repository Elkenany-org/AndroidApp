package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.auth.AuthImplementation
import com.elkenany.api.recruitment.IRecruitmentImplementation
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.recruitment.MyJobsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyAppliedJobsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _responseData = MutableLiveData<MyJobsData?>()
    private val _exception = MutableLiveData<Int?>()
    private val _loading = MutableLiveData(false)
    private val _isRecruiter = MutableLiveData<Boolean?>()
    private val _whereToNavigate = MutableLiveData<Int?>()
    private val api = IRecruitmentImplementation()


    val responseData: LiveData<MyJobsData?> get() = _responseData
    val loading: LiveData<Boolean> get() = _loading
    val isRecruiter: LiveData<Boolean?> get() = _isRecruiter
    val exception: LiveData<Int?> get() = _exception
    val whereToNavigate: LiveData<Int?> get() = _whereToNavigate


    fun getMyJobsData() {
        if (AuthImplementation.userApiToken.isNullOrEmpty()) {
            _exception.value = 401
        } else {
            _loading.value = true
            uiScope.launch {
                val response =
                    api.getMyJobsData("Bearer ${AuthImplementation.userApiToken}")
                exceptionChecker(response)
                _loading.value = false
            }

        }
    }

    private fun exceptionChecker(response: GenericEntity<MyJobsData?>) {
        if (response.error != null) {
            _exception.value = response.error.toInt()
        } else {
            if (response.data!!.jobs.isEmpty()) {
                _exception.value = 404
            } else {
                _exception.value = 200
            }
            _isRecruiter.value = if (AuthImplementation.auth == null) {
                false
            } else AuthImplementation.auth!!.userType.equals("recruiter")
            _responseData.value = response.data
        }
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun navigateToAddJob() {
        if (AuthImplementation.userApiToken.isNullOrEmpty()) {
            _exception.value = 401
        } else {
            _exception.value = 201
        }
    }

    fun onDoneNavigating() {
        _exception.value = null
        _whereToNavigate.value = null
    }

    fun navigateToSpecificPage() {
        _whereToNavigate.value =
            if (AuthImplementation.auth != null && AuthImplementation.auth!!.userType.equals("recruiter")) {
                1
            } else
                2
    }
}
