package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.auth.AuthImplementation.Companion.userApiToken
import com.elkenany.api.shows.IShowsImplementation
import com.elkenany.entities.shows_data.ShowsDetailsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ShowsDetailsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val api = IShowsImplementation()
    private val _loading = MutableLiveData(false)
    private val _goingState = MutableLiveData<Int>()
    private val _showsDetails = MutableLiveData<ShowsDetailsData?>()


    val showsDetails: LiveData<ShowsDetailsData?> get() = _showsDetails
    val loading: LiveData<Boolean> get() = _loading
    val goingState: LiveData<Int> get() = _goingState


    fun getShowDetailsData(id: Long?) {
        _loading.value = true
        uiScope.launch {
            _showsDetails.value = api.getShowsDetailsData(id)
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun postGoingState(showId: Long, stateId: Int) {
        if (userApiToken != null) {
            uiScope.launch {
                _goingState.value = when (stateId) {
                    1 -> {
                        api.onGoingState("Bearer $userApiToken", showId)
                    }
                    2 -> {
                        api.onNotGoingState("Bearer $userApiToken", showId)
                    }
                    else -> {
                        throw IllegalArgumentException("Unknown state")
                    }
                }

            }
        } else {
            _goingState.value = 401
        }

    }
}