package com.example.elkenany.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.R
import com.example.elkenany.api.news.INewsImplementation
import com.example.elkenany.entities.news_data.NewsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _newsData = MutableLiveData<NewsData?>()
    private val _loading = MutableLiveData(false)
    private val api = INewsImplementation()

    val newsData: LiveData<NewsData?> get() = _newsData
    val loading: LiveData<Boolean> get() = _loading

    fun getAllNewsData(sectorType: String, search: String?) {
        _loading.value = true
        uiScope.launch {
            // ToDo --> implementing getHomeStockData(sectorType) function here
            _newsData.value = api.getAllNewsData(sectorType, search)
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}