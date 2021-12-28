package id.co.ptn.hungrystock.ui.main.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.models.main.home.Event
import id.co.ptn.hungrystock.models.main.home.PastEvent
import id.co.ptn.hungrystock.models.main.home.UpcomingEvent
import id.co.ptn.hungrystock.repositories.AppRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {
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


    /**
     * Api
     * */

    fun apiGetEvents() {

    }

}