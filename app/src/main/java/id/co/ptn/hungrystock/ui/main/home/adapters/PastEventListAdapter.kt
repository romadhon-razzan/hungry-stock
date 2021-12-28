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

class PastEventListAdapter(private val items: MutableList<PastEvent>):
    RecyclerView.Adapter<PastEventListAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemPastEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PastEvent) {
            item.title.let { binding.tvTitle.text = it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPastEventBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_past_event, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = items[position]
        holder.bind(element)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}