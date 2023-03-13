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
    private var events: MutableList<Event> = mutableListOf()
    private var upcomingEvents: MutableList<UpcomingEvent> = mutableListOf()
    private var pastEvents: MutableList<PastEvent> = mutableListOf()

    fun setEvents(events: MutableList<Event>) {
        this.events = events
    }

    fun getEvents(): MutableList<Event> {
        return events
    }

    fun setUpcomingEvents(events: MutableList<UpcomingEvent>) {
        this.upcomingEvents = events
    }

    fun getUpcomingEvents(): MutableList<UpcomingEvent> {
        return upcomingEvents
    }

    fun setPastEvents(events: MutableList<PastEvent>) {
        this.pastEvents = events
    }

    fun getPastEvents(): MutableList<PastEvent> {
        return pastEvents
    }


    private var _reqHomeResponse: MutableLiveData<Resource<ResponseEvents>> = MutableLiveData()
    fun reqHomeResponse(): MutableLiveData<Resource<ResponseEvents>> = _reqHomeResponse

    private var _reqNextEventResponse: MutableLiveData<Resource<ResponseEvent>> = MutableLiveData()
    fun reqNextEventResponse(): MutableLiveData<Resource<ResponseEvent>> = _reqNextEventResponse

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
                repository.getEvent(sessionManager?.authData?.code ?: "").let {
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

    fun apiGetNextEvent(p: String) {
        Log.d("Page", p)
        viewModelScope.launch {
            try {
                _reqNextEventResponse.postValue(Resource.loading(null))
//                repository.getNextEvent(p).let {
//                    if (it.isSuccessful){
//                        _reqNextEventResponse.postValue(Resource.success(it.body()))
//                    } else {
//                        val type = object : TypeToken<ResponseEvent>() {}.type
//                        var errorResponse: ResponseEvent? = null
//                        try {
//                            errorResponse = Gson().fromJson(it.errorBody()?.charStream(), type)
//                        } catch(e: Exception) {
//                            e.printStackTrace()
//                        }
//                        _reqNextEventResponse.postValue(Resource.error(it.errorBody().toString(), errorResponse))
//                    }
//                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}