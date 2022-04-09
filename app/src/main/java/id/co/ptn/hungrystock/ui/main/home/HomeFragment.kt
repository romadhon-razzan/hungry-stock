package id.co.ptn.hungrystock.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseFragment
import id.co.ptn.hungrystock.bases.EmptyStateFragment
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
        initListener()
        setObserve()
        apiGetHome()
    }

    private fun initListener() {
        binding.swipeRefresh.setOnRefreshListener {
            apiGetHome()
        }
        binding.btNext.setOnClickListener { apiGetNextEvent() }
    }

    private fun initList() {
        binding.frameContainer.visibility = View.GONE
        eventListAdapter = EventListAdapter(viewModel.getEvents(), childFragmentManager, object : EventListAdapter.Listener{
            override fun openConference(url: String) {
                openUrlPage(url)
            }

            override fun openDetailUpcomingEvent(event: UpcomingEvent) {
                showSnackBar(binding.container," Open Detail")
            }

            override fun openDetailPastEvent(event: PastEvent) {
                
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
                    headlineEvent.title.toString(),
                    headlineEvent.content.toString(),
                    headlineEvent.speaker.toString(),
                    headlineEvent.event_date.toString(),
                    headlineEvent.event_hour_start.toString(),
                    headlineEvent.event_hour_end.toString(),
                    headlineEvent.photo_url.toString(),
                    headlineEvent.zoom_link.toString()))
            viewModel.setUpcomingEvents(upe)
        }catch (e: Exception){
            e.printStackTrace()
        }

        try {
            val pe: MutableList<PastEvent>  = mutableListOf()
            data.events.data.forEachIndexed { index, eventData ->
                pe.add(index, PastEvent(
                    eventData.title.toString(),
                    eventData.speaker.toString(),
                    eventData.event_date.toString(),
                    eventData.event_hour_start.toString(),
                    eventData.event_hour_end.toString(),
                    eventData.zoom_link.toString()))
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

    private fun setNextData(data: ResponseEventData) {
        try {
            val pe: MutableList<PastEvent>  = mutableListOf()
            data.events.data.forEachIndexed { index, eventData ->
                pe.add(index, PastEvent(
                    eventData.title.toString(),
                    eventData.speaker.toString(),
                    eventData.event_date.toString(),
                    eventData.event_hour_start.toString(),
                    eventData.event_hour_end.toString(),
                    eventData.zoom_link.toString()))
            }
            viewModel.getPastEvents().addAll(pe)
            eventListAdapter.updatePastEvent(eventListAdapter.itemCount, viewModel.getEvents().size)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun emptyState() {
        binding.frameContainer.visibility = View.VISIBLE
        childFragmentManager.commit {
            setReorderingAllowed(true)
            val bundle = Bundle()
            bundle.putString("title","Event belum ditemukan")
            bundle.putString("message","")
            add<EmptyStateFragment>(R.id.frame_container, "", bundle)
        }

    }

    private fun setObserve() {
        viewModel.reqHomeResponse().observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    it.data?.data?.let { data ->
                        data.events.next_page_url?.let { _ ->
                            data.events.current_page?.let { cp -> viewModel.setNextPage((cp+1).toString()) }
                            viewModel.setCanLoadNext(true)
                        } ?: viewModel.setCanLoadNext(false)
                        initData(data)
                        initList()
                    } ?: emptyState()
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    emptyState()
                    showSnackBar(binding.container,"Something wrong")
                }
            }
        }

        viewModel.reqNextEventResponse().observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    viewModel.setLoadingNext(false)
                    it.data?.data?.let { data ->
                        data.events.next_page_url?.let { _ ->
                            data.events.current_page?.let { cp -> viewModel.setNextPage((cp+1).toString()) }
                            viewModel.setCanLoadNext(true)
                        } ?: viewModel.setCanLoadNext(false)
                        setNextData(data) }
                }
                Status.LOADING -> {
                    viewModel.setLoadingNext(true)
                    viewModel.setCanLoadNext(false)
                }
                Status.ERROR -> {
                    viewModel.setLoadingNext(false)
                    showSnackBar(binding.container,"Something wrong")
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

    private fun apiGetNextEvent() {
        viewModel.apiGetNextEvent(viewModel.getNextPage())
    }

}