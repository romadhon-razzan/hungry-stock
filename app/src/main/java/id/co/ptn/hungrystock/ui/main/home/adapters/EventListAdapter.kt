package id.co.ptn.hungrystock.ui.main.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.ItemEventBinding
import id.co.ptn.hungrystock.models.main.home.PastEvent
import id.co.ptn.hungrystock.models.main.home.ResponseEventsData
import id.co.ptn.hungrystock.models.main.home.UpcomingEvent
import id.co.ptn.hungrystock.utils.getDateMMMMddyyyy
import id.co.ptn.hungrystock.utils.getHHmm

class EventListAdapter(private val items: MutableList<ResponseEventsData>,
                        private val listener: Listener):
    RecyclerView.Adapter<EventListAdapter.ViewHolder>() {
    private lateinit var context: Context

    fun updatePastEvent(positionStart: Int, total: Int) {
//        pastEventListAdapter.notifyItemInserted(positionStart)
//        pastEventHolder.binding.recyclerView.smoothScrollToPosition(positionStart+2)
    }

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class EventHolder(var binding: ItemEventBinding) : ViewHolder(binding.root) {
        fun setView(item: ResponseEventsData, position: Int) {
            Glide.with(context).load(item.image_file ?: "").into(binding.image)
            binding.tvUpcomingTitle.text = item.title ?: ""
            binding.tvUpcomingDate.text = getDateMMMMddyyyy((item.date_from ?: 0) * 1000)
            binding.tvUpcomingDate.append(" ${getHHmm((item.date_from ?: 0) * 1000)}-${getHHmm((item.date_to ?: 0) * 1000)} WIB")

            binding.tvPastEventSubTitle.visibility = if (position == 1 ) View.VISIBLE else View.GONE
            binding.dividerPastEventSubTitle.visibility = if (position == 1 ) View.VISIBLE else View.GONE
            binding.tvPastEventTitle.text = item.title ?: ""
            binding.tvPastEventDate.text = getDateMMMMddyyyy((item.date_from ?: 0) * 1000)
            binding.tvPastEventDate.append(" ${getHHmm((item.date_from ?: 0) * 1000)}-${getHHmm((item.date_to ?: 0) * 1000)} WIB")
            binding.tvPastEventSpeaker.text = item.speakers ?: ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding: ItemEventBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_event, parent, false)
        return EventHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = items[position]
        val viewHolder = holder as EventHolder
        viewHolder.binding.containerUpcomingEvent.visibility = if (position == 0) View.VISIBLE else View.GONE
        viewHolder.binding.containerPastEvent.visibility = if (position == 0) View.GONE else View.VISIBLE
        viewHolder.setView(element, position)
        viewHolder.binding.btnUpcomingJoin.setOnClickListener { listener.openConference(element.zoom_link ?: "") }
        viewHolder.binding.btnPastEventJoin.setOnClickListener { listener.openDetailPastEvent(element) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    public interface Listener {
        fun openConference(url: String)
        fun openDetailUpcomingEvent(event: UpcomingEvent)
        fun openDetailPastEvent(event: ResponseEventsData)
    }
}