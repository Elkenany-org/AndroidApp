package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.shows.IShowsImplementation
import com.elkenany.entities.shows_data.ShowReviewersData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ShowReviewViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val api = IShowsImplementation()
    private val _loading = MutableLiveData(false)
    private val _goingState = MutableLiveData<Int>()
    private val _reviewerDetails = MutableLiveData<ShowReviewersData?>()


    val reviewerDetails: LiveData<ShowReviewersData?> get() = _reviewerDetails
    val loading: LiveData<Boolean> get() = _loading
    val goingState: LiveData<Int> get() = _goingState


    fun getShowDetailsData(id: Long?) {
        _loading.value = true
        uiScope.launch {
            _reviewerDetails.value = api.getAllShowReviewersData(id)
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}