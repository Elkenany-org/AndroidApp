package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.auth.AuthImplementation.Companion.userApiToken
import com.elkenany.api.recruitment.IRecruitmentImplementation
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.recruitment.MyFavoriteJobsListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyFavoriteJobsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _responseData = MutableLiveData<MyFavoriteJobsListData?>()
    private val _exception = MutableLiveData<Int?>()
    private val _loading = MutableLiveData(false)
    private val api = IRecruitmentImplementation()


    val responseData: LiveData<MyFavoriteJobsListData?> get() = _responseData
    val loading: LiveData<Boolean> get() = _loading
    val exception: LiveData<Int?> get() = _exception

    init {
        getFavoritJobsListData()
    }

    private fun getFavoritJobsListData() {
        if (userApiToken.isNullOrEmpty()) {
            _exception.value = 401
        } else {
            _loading.value = true
            uiScope.launch {
                val response = api.getFavoriteJobsListData("Bearer $userApiToken")
                exceptionChecker(response)
                _loading.value = false
            }
        }
    }

    private fun exceptionChecker(response: GenericEntity<MyFavoriteJobsListData?>) {
        if (response.error != null) {
            _exception.value = response.error.toInt()
        } else {
            if (response.data != null) {
                if (response.data.jobs.isEmpty()) {
                    _exception.value = 404
                } else {
                    _exception.value = 200
                }
                _responseData.value = response.data
            } else {
                _exception.value = 400
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}