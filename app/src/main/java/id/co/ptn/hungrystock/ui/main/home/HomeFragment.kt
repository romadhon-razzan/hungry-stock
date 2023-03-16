package id.co.ptn.hungrystock.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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
import id.co.ptn.hungrystock.ui.general.view_model.OtpViewModel
import id.co.ptn.hungrystock.ui.main.home.adapters.EventListAdapter
import id.co.ptn.hungrystock.utils.HashUtils
import id.co.ptn.hungrystock.utils.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: HomeFragmentBinding
    private var viewModel: HomeViewModel? = null
    private lateinit var eventListAdapter: EventListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

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
        binding.btNext.setOnClickListener {
            apiGetNextEvent()
        }
    }

    private fun initList() {
        binding.frameContainer.visibility = View.GONE
        eventListAdapter = EventListAdapter(viewModel?.getEvents() ?: mutableListOf(), object : EventListAdapter.Listener{
            override fun openConference(url: String) {
                if (!User.isExpired(childFragmentManager, sessionManager?.user?.membershipExpDate ?: 0)){
                    if (url.isNotEmpty()) {
                        openUrlPage(url)
                    } else {
                        Toast.makeText(requireContext(), "Link belum disiapkan", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun openDetailUpcomingEvent(event: UpcomingEvent) {

            }

            override fun openDetailPastEvent(event: ResponseEventsData) {
                if (!User.isExpired(childFragmentManager,sessionManager?.user?.membershipExpDate ?: 0)){
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

    private fun initData(data: MutableList<ResponseEventsData>) {
        viewModel?.setEvents(data)
    }

    private fun setNextData(data: MutableList<ResponseEventsData>) {
        viewModel?.getEvents()?.addAll(data)
        viewModel?.getEvents()?.let { events ->
            eventListAdapter.updatePastEvent(eventListAdapter.itemCount, events.size)
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
        viewModel?.reqOtpResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    if (running_service == RunningServiceType.EVENT){
                        TOKEN = "${HashUtils.hash256Events("customer_id=${sessionManager?.authData?.code ?: ""}")}.${ENV.userKey()}.${it.data?.data ?: ""}"
                        Log.d("access_token_event", TOKEN)
                        lifecycleScope.launch {
                            delay(500)
                            viewModel?.apiGetHome(sessionManager)
                        }
                    } else if (running_service == RunningServiceType.EVENT_NEXT) {
                        TOKEN = "${HashUtils.hash256Events("customer_id=${sessionManager?.authData?.code ?: ""}&offset=${viewModel?.getNextPage()}")}.${ENV.userKey()}.${it.data?.data ?: ""}"
                        Log.d("access_token_next_event", TOKEN)
                        lifecycleScope.launch {
                            delay(500)
                            viewModel?.apiGetNextEvent(sessionManager)
                        }
                    }
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
        viewModel?.reqHomeResponse()?.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    it.data?.let {responseEvents ->
                        val data = responseEvents.data
                        if (data.isNotEmpty()){
                            initData(data as MutableList<ResponseEventsData>)
                            initList()
                            viewModel?.setNextPage(ResponseEvents.getNextPage(responseEvents).toString())
                            viewModel?.setCanLoadNext(ResponseEvents.canLoadNext(responseEvents))
                        } else {
                            emptyState()
                        }
                    }
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    emptyState()
                }
            }
        }

        viewModel?.reqNextEventResponse()?.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    viewModel?.setLoadingNext(false)
                    it.data?.let { responseEvents ->
                        setNextData(responseEvents.data as MutableList<ResponseEventsData>)
                        viewModel?.setNextPage(ResponseEvents.getNextPage(responseEvents).toString())
                        viewModel?.setCanLoadNext(ResponseEvents.canLoadNext(responseEvents))
                    }
                }
                Status.LOADING -> {
                    viewModel?.setLoadingNext(true)
                    viewModel?.setCanLoadNext(false)
                }
                Status.ERROR -> {
                    viewModel?.setLoadingNext(false)
                }
            }
        }
    }

    /**
     * Api
     * */

    private fun apiGetOtp() {
        viewModel?.apiGetOtp()
    }

    private fun apiGetHome() {
        running_service = RunningServiceType.EVENT
        apiGetOtp()
    }

    private fun apiGetNextEvent() {
        running_service = RunningServiceType.EVENT_NEXT
        apiGetOtp()
    }

}