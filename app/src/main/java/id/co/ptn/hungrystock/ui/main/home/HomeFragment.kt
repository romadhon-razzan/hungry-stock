package id.co.ptn.hungrystock.ui.main.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseFragment
import id.co.ptn.hungrystock.databinding.HomeFragmentBinding
import id.co.ptn.hungrystock.models.main.home.Event
import id.co.ptn.hungrystock.models.main.home.PastEvent
import id.co.ptn.hungrystock.models.main.home.UpcomingEvent
import id.co.ptn.hungrystock.ui.main.home.adapters.EventListAdapter

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var eventListAdapter: EventListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.home_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initList()
    }

    private fun initList() {
        initData()
        eventListAdapter = EventListAdapter(viewModel.getEvents())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventListAdapter
        }
    }

    private fun initData() {
        val upe: MutableList<UpcomingEvent>  = mutableListOf()
        upe.add(0, UpcomingEvent("Temu Emiten: BSDE & DMAS", "Speaker 1", "Selasa, 9 Nov 2021. 18:30 WIB", "", ""))
        viewModel.setUpcomingEvents(upe)

        val pe: MutableList<PastEvent>  = mutableListOf()
        pe.add(0, PastEvent("Temu Emiten: BSDE & DMAS", "Speaker 1", "Selasa, 9 Nov 2021. 18:30 WIB", ""))
        pe.add(1, PastEvent("Temu Emiten: BSDE & DMAS", "Speaker 2", "Selasa, 9 Nov 2021. 18:30 WIB", ""))
        pe.add(2, PastEvent("Temu Emiten: BSDE & DMAS", "Speaker 3", "Selasa, 9 Nov 2021. 18:30 WIB", ""))
        pe.add(3, PastEvent("Temu Emiten: BSDE & DMAS", "Speaker 4", "Selasa, 9 Nov 2021. 18:30 WIB", ""))
        pe.add(4, PastEvent("Temu Emiten: BSDE & DMAS", "Speaker 5", "Selasa, 9 Nov 2021. 18:30 WIB", ""))
        viewModel.setPastEvents(pe)

        val h: MutableList<Event>  = mutableListOf()
        h.add(0, Event(Event.TYPE_UPCOMING_EVENT, viewModel.getUpcomingEvents(), mutableListOf()))
        h.add(1, Event(Event.TYPE_PAST_EVENT, mutableListOf(), viewModel.getPastEvents()))
        viewModel.setEvents(h)
    }

}