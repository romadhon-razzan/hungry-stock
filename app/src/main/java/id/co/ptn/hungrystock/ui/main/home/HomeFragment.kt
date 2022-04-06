package id.co.ptn.hungrystock.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseFragment
import id.co.ptn.hungrystock.databinding.HomeFragmentBinding
import id.co.ptn.hungrystock.models.main.home.Event
import id.co.ptn.hungrystock.models.main.home.PastEvent
import id.co.ptn.hungrystock.models.main.home.ResponseEventData
import id.co.ptn.hungrystock.models.main.home.UpcomingEvent
import id.co.ptn.hungrystock.ui.main.home.adapters.EventListAdapter
import id.co.ptn.hungrystock.utils.Status

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: HomeFragmentBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var eventListAdapter: EventListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.home_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        init()
    }

    private fun init() {
        setObserve()
        apiGetHome()
    }

    private fun initList() {
        eventListAdapter = EventListAdapter(viewModel.getEvents(), object : EventListAdapter.Listener{
            override fun openConference(url: String) {
                openUrlPage(url)
            }
        })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventListAdapter
        }
    }

    private fun initData(data: ResponseEventData) {
        try {
            val headlineEvent = data.headlineEvent
            val upe: MutableList<UpcomingEvent>  = mutableListOf()
            upe.add(0,
                UpcomingEvent(
                    headlineEvent.title!!,
                    headlineEvent.content!!,
                    headlineEvent.speaker!!,
                    "Selasa, 9 Nov 2021. 18:30 WIB",
                    headlineEvent.photo_url!!,
                    headlineEvent.zoom_link!!))
            viewModel.setUpcomingEvents(upe)
        }catch (e: Exception){
            e.printStackTrace()
        }

        try {
            val pe: MutableList<PastEvent>  = mutableListOf()
            data.events.data.forEachIndexed { index, eventData ->
                pe.add(index, PastEvent(eventData.title!!, eventData.speaker!!, "Selasa, 9 Nov 2021. 18:30 WIB", eventData.zoom_link!!))
            }
            viewModel.setPastEvents(pe)
        }catch (e: Exception){
            e.printStackTrace()
        }

        val h: MutableList<Event>  = mutableListOf()

        if (viewModel.getUpcomingEvents().isNotEmpty())
        h.add(0, Event(Event.TYPE_UPCOMING_EVENT, viewModel.getUpcomingEvents(), mutableListOf()))

        if (viewModel.getPastEvents().isNotEmpty())
        h.add(1, Event(Event.TYPE_PAST_EVENT, mutableListOf(), viewModel.getPastEvents()))

        viewModel.setEvents(h)
    }

    private fun setObserve() {
        viewModel.reqHomeResponse().observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.data?.let { data -> initData(data) }
                    initList()
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    /**
     * Api
     * */

    private fun apiGetHome() {
        viewModel.apiGetHome()
    }

}