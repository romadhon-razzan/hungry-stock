package id.co.ptn.hungrystock.ui.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.models.main.home.Event
import id.co.ptn.hungrystock.models.main.home.PastEvent
import id.co.ptn.hungrystock.models.main.home.ResponseEvent
import id.co.ptn.hungrystock.models.main.home.UpcomingEvent
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
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


    private var _reqHomeResponse: MutableLiveData<Resource<ResponseEvent>> = MutableLiveData()
    fun reqHomeResponse(): MutableLiveData<Resource<ResponseEvent>> = _reqHomeResponse

    /**
     * Api
     * */

    fun apiGetHome() {
        viewModelScope.launch {
            try {
                _reqHomeResponse.postValue(Resource.loading(null))
                repository.getEvent().let {
                    if (it.isSuccessful){
                        _reqHomeResponse.postValue(Resource.success(it.body()))
                    } else {
                        val type = object : TypeToken<ResponseEvent>() {}.type
                        var errorResponse: ResponseEvent? = null
                        try {
                            errorResponse = Gson().fromJson(it.errorBody()?.charStream(), type)
                        } catch(e: Exception) {
                            e.printStackTrace()
                        }
                        _reqHomeResponse.postValue(Resource.error(it.errorBody().toString(), errorResponse))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}