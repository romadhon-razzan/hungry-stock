package id.co.ptn.hungrystock.ui.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.core.SessionManager
import id.co.ptn.hungrystock.models.main.home.*
import id.co.ptn.hungrystock.repositories.EventRepository
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: EventRepository) : ViewModel() {
    private var events: MutableList<ResponseEventsData> = mutableListOf()
    private var upcomingEvents: MutableList<UpcomingEvent> = mutableListOf()
    private var pastEvents: MutableList<PastEvent> = mutableListOf()

    fun setEvents(events: MutableList<ResponseEventsData>) {
        this.events = events
    }

    fun getEvents(): MutableList<ResponseEventsData> {
        return events
    }


    private var _reqHomeResponse: MutableLiveData<Resource<ResponseEvents>> = MutableLiveData()
    fun reqHomeResponse(): MutableLiveData<Resource<ResponseEvents>> = _reqHomeResponse

    private var _reqNextEventResponse: MutableLiveData<Resource<ResponseEvents>> = MutableLiveData()
    fun reqNextEventResponse(): MutableLiveData<Resource<ResponseEvents>> = _reqNextEventResponse

    private val _loadingNext = MutableLiveData(false)
    val loadingNext: LiveData<Boolean> = _loadingNext
    fun setLoadingNext(value: Boolean) {
        _loadingNext.value = value
    }

    private val _canLoadNext = MutableLiveData(false)
    val canLoadNext: LiveData<Boolean> = _canLoadNext
    fun setCanLoadNext(value: Boolean) {
        _canLoadNext.value = value
    }

    private var nextPage = ""
    fun getNextPage(): String = nextPage
    fun setNextPage(value: String) {
        nextPage = value
    }

    /**
     * Api
     * */

    fun apiGetHome(sessionManager: SessionManager?) {
        viewModelScope.launch {
            try {
                _reqHomeResponse.postValue(Resource.loading(null))
                val param = "customer_id=${sessionManager?.authData?.code ?: ""}"
                repository.getEvent(param).let {
                    if (it.isSuccessful){
                        _reqHomeResponse.postValue(Resource.success(it.body()))
                    } else {
                       //
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun apiGetNextEvent(sessionManager: SessionManager?) {
        viewModelScope.launch {
            try {
                _reqNextEventResponse.postValue(Resource.loading(null))
                val param = "customer_id=${sessionManager?.authData?.code ?: ""}&offset=${nextPage}"
                repository.getEvent(param).let {
                    if (it.isSuccessful){
                        _reqNextEventResponse.postValue(Resource.success(it.body()))
                    } else {
                        //
                        _reqNextEventResponse.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}