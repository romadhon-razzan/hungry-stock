package id.co.ptn.hungrystock.ui.main.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.ItemPastEventListBinding
import id.co.ptn.hungrystock.databinding.ItemUpcomingEventListBinding
import id.co.ptn.hungrystock.models.main.home.Event
import id.co.ptn.hungrystock.models.main.home.PastEvent
import id.co.ptn.hungrystock.models.main.home.UpcomingEvent

class EventListAdapter(private val items: MutableList<Event>,
                       private val fragmentManager: FragmentManager,
private val listener: Listener):
    RecyclerView.Adapter<EventListAdapter.ViewHolder>() {
    private lateinit var context: Context

    companion object {
        const val TYPE_UPCOMING_EVENT = 0
        const val TYPE_PAST_EVENT = 1
    }

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class UpcomingEventHolder(var binding: ItemUpcomingEventListBinding) : ViewHolder(binding.root) {
        private lateinit var upcomingEventListAdapter: UpcomingEventListAdapter
        fun initList(items: MutableList<UpcomingEvent>, context: Context, fragmentManager: FragmentManager, listener: Listener) {
            upcomingEventListAdapter = UpcomingEventListAdapter(context,items, fragmentManager, object : UpcomingEventListAdapter.Listener{
                override fun openConference(url: String) {
                    listener.openConference(url)
                }

                override fun openDetailUpcomingEvent(event: UpcomingEvent) {
                    listener.openDetailUpcomingEvent(event)
                }
            })
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = upcomingEventListAdapter
            }
        }
    }

    inner class PastEventHolder(var binding: ItemPastEventListBinding) : ViewHolder(binding.root) {
        private lateinit var pastEventListAdapter: PastEventListAdapter
        fun initList(items: MutableList<PastEvent>, context: Context) {
            pastEventListAdapter = PastEventListAdapter(items, object : PastEventListAdapter.Listener{
                override fun openDetailPastEvent(event: PastEvent) {
                    openDetailPastEvent(event)
                }
            })
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = pastEventListAdapter
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].type == Event.TYPE_UPCOMING_EVENT)
            TYPE_UPCOMING_EVENT
        else TYPE_PAST_EVENT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return when(viewType) {
            TYPE_UPCOMING_EVENT -> {
                val binding: ItemUpcomingEventListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_upcoming_event_list, parent, false)
                UpcomingEventHolder(binding)
            }
            else -> {
                val binding: ItemPastEventListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_past_event_list, parent, false)
                PastEventHolder(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = items[position]
        when(element.type) {
            Event.TYPE_UPCOMING_EVENT -> {
                val viewHolder = holder as UpcomingEventHolder
                viewHolder.initList(element.upcomingEvents as MutableList<UpcomingEvent>, context, fragmentManager, listener)
            }
            else -> {
                val viewHolder = holder as PastEventHolder
                viewHolder.initList(element.pastEvents as MutableList<PastEvent>, context)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    public interface Listener {
        fun openConference(url: String)
        fun openDetailUpcomingEvent(event: UpcomingEvent)
        fun openDetailPastEvent(event: PastEvent)
    }
}