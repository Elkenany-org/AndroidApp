package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.news.INewsImplementation
import com.elkenany.entities.news_data.NewsDetailsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsDetailsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _newsDetails = MutableLiveData<NewsDetailsData?>()
    private val _loading = MutableLiveData(false)
    private val api = INewsImplementation()

    val newsDetails: LiveData<NewsDetailsData?> get() = _newsDetails
    val loading: LiveData<Boolean> get() = _loading

    fun getAllNewsData(id: Int) {
        _loading.value = true
        uiScope.launch {
            _newsDetails.value = api.getNewsDetailsData(id)
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}