package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.ships.IShipsImplementation
import com.elkenany.entities.ships.ShipsListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ShipsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _shipsData = MutableLiveData<ShipsListData?>()
    private val _exception = MutableLiveData<Int?>()
    private val _loading = MutableLiveData(false)
    private val api = IShipsImplementation()

    val shipsData: LiveData<ShipsListData?> get() = _shipsData
    val exception: LiveData<Int?> get() = _exception
    val loading: LiveData<Boolean> get() = _loading

    fun getAllSearchData(date: String?) {
        _loading.value = true
        uiScope.launch {
            // ToDo --> implementing getHomeStockData(sectorType) function here
            val response = api.getAllShipsData(date)
            if (response.error == null) {
                _shipsData.value = response.data
                if (response.data!!.ships.isEmpty()) {
                    _exception.value = 404
                } else {
                    _exception.value = 200
                }
            } else {
                _exception.value = response.error.toInt()
            }
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}