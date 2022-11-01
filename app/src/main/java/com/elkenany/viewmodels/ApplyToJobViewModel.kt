package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.auth.AuthImplementation.Companion.userApiToken
import com.elkenany.api.recruitment.IRecruitmentImplementation
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.recruitment.ApplyData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ApplyToJobViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _responseData = MutableLiveData<ApplyData?>()
    private val _exception = MutableLiveData<Int?>()
    private val _loading = MutableLiveData(false)
    private val api = IRecruitmentImplementation()


    val responseData: LiveData<ApplyData?> get() = _responseData
    val loading: LiveData<Boolean> get() = _loading
    val exception: LiveData<Int?> get() = _exception

    fun applyToJob(
        education: String?,
        experience: String?,
        jobId: Int?,
        expectedSalary: String?,
        otherInfo: String?,
        cv_file: MultipartBody.Part,
        fullName: String?,
        phone: String?,
        noticePeriod: String?,
    ) {
        if (userApiToken == null) {
            _exception.value = 401
        } else {
            _loading.value = true
            uiScope.launch {
                val response = api.applyToJob("Bearer $userApiToken",
                    education,
                    experience,
                    jobId,
                    expectedSalary,
                    otherInfo,
                    cv_file,
                    fullName,
                    phone,
                    noticePeriod)
                exceptionChecker(response)
                _loading.value = false
            }
        }

    }


    private fun exceptionChecker(response: GenericEntity<ApplyData?>) {
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

}


