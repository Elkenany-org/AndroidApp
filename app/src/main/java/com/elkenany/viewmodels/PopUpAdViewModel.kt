package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class PopUpAdViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _counter = MutableLiveData<Int>()

    val counter: LiveData<Int> get() = _counter

    private var cnter = 5

    init {
        uiScope.launch {

            (0..5).forEach { _ ->
                _counter.value = cnter
                delay(1000)
                cnter--
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}