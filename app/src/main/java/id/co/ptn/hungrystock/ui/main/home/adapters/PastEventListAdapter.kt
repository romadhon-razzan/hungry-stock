package id.co.ptn.hungrystock.ui.main.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.ItemOnboarding2Binding
import id.co.ptn.hungrystock.databinding.ItemPastEventBinding
import id.co.ptn.hungrystock.databinding.ItemRadioButtonBinding
import id.co.ptn.hungrystock.databinding.ItemRegistrationStep2Binding
import id.co.ptn.hungrystock.models.OnboardPageTwo
import id.co.ptn.hungrystock.models.main.home.PastEvent
import id.co.ptn.hungrystock.models.registration.MainRegistration
import id.co.ptn.hungrystock.models.registration.RegistrationItem
import id.co.ptn.hungrystock.utils.getDateMMMMddyyyy

class PastEventListAdapter(private val items: MutableList<PastEvent>,
private val listener: Listener):
    RecyclerView.Adapter<PastEventListAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemPastEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PastEvent, listener: Listener) {
            item.title.let { binding.tvTitle.text = it }
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
            item.speaker.let { binding.tvSpeaker.text = "Bersama ${it}" }
            binding.btnJoin.setOnClickListener { listener.openDetailPastEvent(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPastEventBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_past_event, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = items[position]
        holder.bind(element, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    public interface Listener{
        fun openDetailPastEvent(event: PastEvent)
    }
}