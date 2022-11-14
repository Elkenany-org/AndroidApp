package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.news.INewsImplementation
import com.elkenany.entities.news_data.NewsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _newsData = MutableLiveData<NewsData?>()
    private val _openCloseSearch = MutableLiveData(false)
    private val _loading = MutableLiveData(false)
    private val api = INewsImplementation()

    val newsData: LiveData<NewsData?> get() = _newsData
    val loading: LiveData<Boolean> get() = _loading
    val openCloseSearch: LiveData<Boolean> get() = _openCloseSearch

    private var isOpened = false

    fun getAllNewsData(sectorType: Long?, search: String?, sort: String?) {
        _loading.value = true
        uiScope.launch {
            // ToDo --> implementing getHomeStockData(sectorType) function here
            _newsData.value = api.getAllNewsData(sectorType, search, sort)
            _loading.value = false
        }
    }

    fun openCloseSearchBar() {
        isOpened = !isOpened
        _openCloseSearch.value = isOpened
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}