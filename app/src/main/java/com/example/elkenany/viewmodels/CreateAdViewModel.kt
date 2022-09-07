package com.example.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.auth.AuthImplementation.Companion.userApiToken
import com.example.elkenany.api.store.IStoreImplementation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File

class CreateAdViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _createdAdd = MutableLiveData<Boolean?>(null)
    private val _loading = MutableLiveData(false)
    private val api = IStoreImplementation()


    val createdAd: LiveData<Boolean?> get() = _createdAdd
    val loading: LiveData<Boolean> get() = _loading

    fun createNewAd(
        title: String?,
        description: String?,
        phone: String?,
        price: String?,
        sectorId: Long?,
        address: String?,
        imageFile: Array<File?>,
    ) {
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
            _createdAdd.value = reponse != null
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}