package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.auth.AuthImplementation
import com.elkenany.api.recruitment.IRecruitmentImplementation
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.recruitment.ApplicationDetailsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ApplicantDetailsViewModel : ViewModel() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _responseData = MutableLiveData<ApplicationDetailsData?>()
    private val _exception = MutableLiveData<Int?>()
    private val _loading = MutableLiveData(false)
    private val api = IRecruitmentImplementation()


    val responseData: LiveData<ApplicationDetailsData?> get() = _responseData
    val loading: LiveData<Boolean> get() = _loading
    val exception: LiveData<Int?> get() = _exception

    fun addQualifiedApplicants(qualifiedValue: String?, applicationId: String?) {
        if (AuthImplementation.userApiToken.isNullOrEmpty()) {
            _exception.value = 401
        } else {
            uiScope.launch {
                val response =
                    api.addQualifiedApplicant("Bearer ${AuthImplementation.userApiToken}",
                        qualifiedValue,
                        applicationId)
                if (!response.message.isNullOrEmpty() && response.error.isNullOrEmpty()) {
                    _exception.value = 201
                } else {
                    _exception.value = 500
                }
            }
        }
    }

    fun getApplicationData(
        jobId: Long?,
    ) {
        if (AuthImplementation.userApiToken.isNullOrEmpty()) {
            _exception.value = 401
        } else {
            _loading.value = true
            uiScope.launch {
                val response =
                    api.getApplicationDetailsData("Bearer ${AuthImplementation.userApiToken}",
                        jobId)
                exceptionChecker(response)
                _loading.value = false
            }

        }
    }

    private fun exceptionChecker(response: GenericEntity<ApplicationDetailsData?>) {
        if (response.error != null) {
            _exception.value = response.error.toInt()
        } else {
            if (response.data!!.application == null) {
                _exception.value = 404
            } else {
                _responseData.value = response.data
                _exception.value = 200
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}

