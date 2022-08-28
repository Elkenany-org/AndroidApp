package com.example.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.callback.IHomeImplementation
import com.example.elkenany.entities.home_data.AboutUsData
import com.example.elkenany.entities.home_data.ContactUsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AboutViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _about = MutableLiveData<AboutUsData?>()
    private val _contactUs = MutableLiveData<ContactUsData?>()
    private val _loading = MutableLiveData(false)
    private val api = IHomeImplementation()


    val about: LiveData<AboutUsData?> get() = _about
    val contactUs: LiveData<ContactUsData?> get() = _contactUs
    val loading: LiveData<Boolean> get() = _loading

    fun onGettingFromBackEnd() {
        _loading.value = true
        uiScope.launch {
            _about.value = api.getAboutUsData()
            _contactUs.value = api.getAllContactUsData()
            _loading.value = false
        }

    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}