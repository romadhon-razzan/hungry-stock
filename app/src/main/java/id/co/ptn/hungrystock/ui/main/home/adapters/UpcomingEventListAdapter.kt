package id.co.ptn.hungrystock.ui.main.home.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.WebViewFragment
import id.co.ptn.hungrystock.databinding.ItemOnboarding2Binding
import id.co.ptn.hungrystock.databinding.ItemRadioButtonBinding
import id.co.ptn.hungrystock.databinding.ItemRegistrationStep2Binding
import id.co.ptn.hungrystock.databinding.ItemUpcomingEventBinding
import id.co.ptn.hungrystock.models.OnboardPageTwo
import id.co.ptn.hungrystock.models.main.home.UpcomingEvent
import id.co.ptn.hungrystock.models.registration.MainRegistration
import id.co.ptn.hungrystock.models.registration.RegistrationItem
import id.co.ptn.hungrystock.utils.getDateMMMMddyyyy

class UpcomingEventListAdapter(private val context: Context, private val items: MutableList<UpcomingEvent>,
                               private val fragmentManager: FragmentManager,
    private val listener: Listener):
    RecyclerView.Adapter<UpcomingEventListAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemUpcomingEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, item: UpcomingEvent, fragmentManager: FragmentManager, listener: Listener) {
            item.title.let { binding.tvTitle.text = it }
            item.description.let {
                binding.tvDescription.text = Html.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY)
                fragmentManager.commit {
                    val bundle = Bundle()
                    bundle.putString("content", it)
                    add<WebViewFragment>(R.id.frame_web, null, bundle)
                }
            }


            item.date.let {
                binding.tvDate.text = getDateMMMMddyyyy(it)
                try {
                    binding.tvDate.append(" ")
                    binding.tvDate.append(item.startAt)
                    binding.tvDate.append(" - ")
                    binding.tvDate.append(item.endAt)
                    binding.tvDate.append(" WIB")
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
            Glide.with(context).load(item.cover).into(binding.image)
            binding.btnJoin.setOnClickListener { listener.openConference(item.link) }
            binding.container.setOnClickListener { listener.openDetailUpcomingEvent(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemUpcomingEventBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_upcoming_event, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = items[position]
        holder.bind(context, element, fragmentManager, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    public interface Listener{
        fun openConference(url: String)
        fun openDetailUpcomingEvent(event: UpcomingEvent)
    }
}