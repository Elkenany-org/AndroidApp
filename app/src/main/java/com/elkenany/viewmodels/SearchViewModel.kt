package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.elkenany.api.search.ISearchImplementation
import com.elkenany.entities.search.SearchResult

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _searchData = MutableLiveData<SearchResult?>()
    private val _loading = MutableLiveData(false)
    private val api = ISearchImplementation()

    val searchData: LiveData<SearchResult?> get() = _searchData
    val loading: LiveData<Boolean> get() = _loading

    fun getAllSearchData(search: String?) {
        _loading.value = true
        uiScope.launch {
            _searchData.value = api.getAllSearchData(search)
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}