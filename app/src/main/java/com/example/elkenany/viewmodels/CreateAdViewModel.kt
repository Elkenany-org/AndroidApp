package com.example.elkenany.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.auth.AuthImplementation.Companion.userApiToken
import com.example.elkenany.api.store.IStoreImplementation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CreateAdViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _createdAdd = MutableLiveData<Boolean?>(null)
    private val _exception = MutableLiveData<Int>()
    private val _loading = MutableLiveData(false)
    private val api = IStoreImplementation()


    val createdAd: LiveData<Boolean?> get() = _createdAdd
    val exception: LiveData<Int> get() = _exception
    val loading: LiveData<Boolean> get() = _loading

    fun createNewAd(
        title: String?,
        description: String?,
        phone: String?,
        price: String?,
        sectorId: Long?,
        address: String?,
        imageFile: String?,
    ) {
        Log.i("ArrayList", imageFile.toString())
        _loading.value = true
        uiScope.launch {
            val reponse = api.createNewAd("Bearer $userApiToken",
                title,
                description,
                phone,
                price,
                sectorId,
                address,
                "mobile",
                imageFile)
            if (reponse!!.error.isNullOrEmpty()) {
                _exception.value = 200
            } else {
                if (reponse.error == "402") {
                    _exception.value = 402
                } else {
                    _exception.value = 400
                }
            }
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}