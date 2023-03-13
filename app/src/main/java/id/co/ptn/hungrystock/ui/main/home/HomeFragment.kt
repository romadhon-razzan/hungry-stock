package id.co.ptn.hungrystock.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseFragment
import id.co.ptn.hungrystock.bases.EmptyStateFragment
import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.core.network.RunningServiceType
import id.co.ptn.hungrystock.core.network.running_service
import id.co.ptn.hungrystock.databinding.HomeFragmentBinding
import id.co.ptn.hungrystock.models.User
import id.co.ptn.hungrystock.models.main.home.*
import id.co.ptn.hungrystock.ui.main.home.adapters.EventListAdapter
import id.co.ptn.hungrystock.utils.HashUtils
import id.co.ptn.hungrystock.utils.Status

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: HomeFragmentBinding
    private var viewModel: HomeViewModel? = null
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
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = this
        init()
    }

    private fun init() {
        initListener()
        setObserve()

        running_service = RunningServiceType.EVENT
        apiGetOtp()
    }

    private fun initListener() {
        binding.swipeRefresh.setOnRefreshListener {
            apiGetHome()
        }
        binding.btNext.setOnClickListener { apiGetNextEvent() }
    }

    private fun initList() {
        binding.frameContainer.visibility = View.GONE
        viewModel?.getEvents()?.let { events ->
            eventListAdapter = EventListAdapter(events, childFragmentManager, object : EventListAdapter.Listener{
                override fun openConference(url: String) {
                    if (!User.isExpired(childFragmentManager, sessionManager?.user?.membership_end_at ?: "")){
                        openUrlPage(url)
                    }
                }

                override fun openDetailUpcomingEvent(event: UpcomingEvent) {

                }

                override fun openDetailPastEvent(event: PastEvent) {
                    if (!User.isExpired(childFragmentManager,sessionManager?.user?.membership_end_at ?: "")){
                        val intent =  router.toLearningDetail()
                        intent.putExtra("event", Gson().toJson(event))
                        requireContext().startActivity(intent)
                    }
                }
            })
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = eventListAdapter
            }
        }
    }

    private fun initData(data: MutableList<ResponseEventsData>) {

        val pe: MutableList<PastEvent>  = mutableListOf()
        data.forEachIndexed { index, responseEventsData ->
            if (index == 0){
                try {
                    val upe: MutableList<UpcomingEvent>  = mutableListOf()
                    upe.add(UpcomingEvent(
                            responseEventsData.title ?: "",
                            responseEventsData.description ?: "",
                            responseEventsData.speakers ?: "",
                        (responseEventsData.date_from ?: 0) * 1000,
//                            responseEventsData.event_hour_start.toString(),
//                            responseEventsData.event_hour_end.toString(),
                            "12.00",
                            "14.00",
                            responseEventsData.image_file ?: "",
                            responseEventsData.zoom_link ?: ""))
                    viewModel?.setUpcomingEvents(upe)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            } else {
                try {
                    pe.add(PastEvent(
                        responseEventsData.description.toString(),
                        responseEventsData.title.toString(),
                        responseEventsData.speakers.toString(),
                        (responseEventsData.date_from ?: 0) * 1000,
//                        responseEventsData.event_hour_start.toString(),
//                        responseEventsData.event_hour_end.toString(),
//                        "2 Februari 2023",
                        "12.00",
                        "14.00",
                        responseEventsData.zoom_link.toString()))
                    viewModel?.setPastEvents(pe)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }

        val h: MutableList<Event>  = mutableListOf()

        viewModel?.getUpcomingEvents()?.let { upEvents ->
            if (upEvents.isNotEmpty())
                h.add(0, Event(Event.TYPE_UPCOMING_EVENT, upEvents, mutableListOf()))
        }

        viewModel?.getPastEvents()?.let { pEvents ->
            if (pEvents.isNotEmpty())
                h.add(1, Event(Event.TYPE_PAST_EVENT, mutableListOf(), pEvents))
        }
        viewModel?.setEvents(h)
    }

    private fun setNextData(data: ResponseEventData) {
       /* try {
            val pe: MutableList<PastEvent>  = mutableListOf()
            data.events.data.forEachIndexed { index, eventData ->
                pe.add(index, PastEvent(
                    eventData.slug.toString(),
                    eventData.title.toString(),
                    eventData.speaker.toString(),
                    eventData.event_date.toString(),
                    eventData.event_hour_start.toString(),
                    eventData.event_hour_end.toString(),
                    eventData.zoom_link.toString()))
            }
            viewModel?.getPastEvents()?.addAll(pe)
            viewModel?.getEvents()?.let { events ->
                eventListAdapter.updatePastEvent(eventListAdapter.itemCount, events.size)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }*/
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
        otpViewModel?.reqOtpResponse()?.observe(viewLifecycleOwner){
            if (running_service == RunningServiceType.EVENT){
                TOKEN = "${HashUtils.hash256Events("customer_id=${sessionManager?.authData?.code ?: ""}")}.${ENV.userKey()}.${it?.data?.data ?: ""}"
                Log.d("access_token", TOKEN)
                apiGetHome()
            } else if (running_service == RunningServiceType.EVENT_NEXT) {
                apiGetNextEvent()
            }
        }
        viewModel?.reqHomeResponse()?.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    val data = it?.data?.data ?: mutableListOf()
                    if (data.isNotEmpty()){
                        initData(data as MutableList<ResponseEventsData>)
                        initList()
                    } else {
                        emptyState()
                    }
//                    it.data?.data?.let { data ->
//                        data.events.next_page_url?.let { _ ->
//                            data.events.current_page?.let { cp -> viewModel?.setNextPage((cp+1).toString()) }
//                            viewModel?.setCanLoadNext(true)
//                        } ?: viewModel?.setCanLoadNext(false)
//                        initData(data)
//                        initList()
//                    } ?: emptyState()
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

        viewModel?.reqNextEventResponse()?.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    viewModel?.setLoadingNext(false)
                    it.data?.data?.let { data ->
                        data.events.next_page_url?.let { _ ->
                            data.events.current_page?.let { cp -> viewModel?.setNextPage((cp+1).toString()) }
                            viewModel?.setCanLoadNext(true)
                        } ?: viewModel?.setCanLoadNext(false)
                        setNextData(data) }
                }
                Status.LOADING -> {
                    viewModel?.setLoadingNext(true)
                    viewModel?.setCanLoadNext(false)
                }
                Status.ERROR -> {
                    viewModel?.setLoadingNext(false)
                    showSnackBar(binding.container,"Something wrong")
                }
            }
        }
    }

    /**
     * Api
     * */

    private fun apiGetOtp() {
        otpViewModel?.apiGetOtp()
    }

    private fun apiGetHome() {
        viewModel?.apiGetHome(sessionManager)
    }

    private fun apiGetNextEvent() {
        viewModel?.getNextPage()?.let {
            viewModel?.apiGetNextEvent(it)
        }
    }

}