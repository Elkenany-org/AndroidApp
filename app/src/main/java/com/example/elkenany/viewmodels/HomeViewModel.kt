package com.example.elkenany.viewmodels

import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class HomeViewModel : ViewModel() {
    private val job = Job()
    private val _uiScope = CoroutineScope(Dispatchers.Main + job)

    private val _homePage = MutableLiveData(false)
    private val _searchPage = MutableLiveData(false)
    private val _stockPage = MutableLiveData(false)
    private val _profilePage = MutableLiveData(false)

    val homePage: LiveData<Boolean> get() = _homePage
    val searchPage: LiveData<Boolean> get() = _searchPage
    val stockPage: LiveData<Boolean> get() = _stockPage
    val profilePage: LiveData<Boolean> get() = _profilePage

    init {
        _homePage.value = true
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun onDoneNavigating() {

        _homePage.value = false
        _profilePage.value = false
        _searchPage.value = false
        _stockPage.value = false
    }


    fun onItemClicked(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.homePage -> {
                _homePage.value = true
                true
            }
            R.id.searchPage -> {
                _searchPage.value = true
                true
            }
            R.id.stockPage -> {
                _stockPage.value = true
                true
            }
            R.id.profilePage -> {
                _profilePage.value = true
                true
            }
            else -> false
        }
    }
}