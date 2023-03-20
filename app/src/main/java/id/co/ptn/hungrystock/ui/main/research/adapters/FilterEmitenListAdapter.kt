package id.co.ptn.hungrystock.ui.main.research.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.*
import id.co.ptn.hungrystock.models.OnboardPageTwo
import id.co.ptn.hungrystock.models.main.home.PastEvent
import id.co.ptn.hungrystock.models.main.research.ResearchReport
import id.co.ptn.hungrystock.models.main.research.StockData
import id.co.ptn.hungrystock.models.registration.MainRegistration
import id.co.ptn.hungrystock.models.registration.RegistrationItem

class FilterEmitenListAdapter(
    private val items: MutableList<String>,
    private val itemSelected: String,
    private val listener: Listener):
    RecyclerView.Adapter<FilterEmitenListAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemButtonEmitenBinding,
    var context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, itemSelected: String) {
            item.let {
                binding.tvInitial.text = it
                if (it == itemSelected){
                    setViewSelected(context)
                } else {
                    setViewUnSelected(context)
                }
            }
        }

        private fun setViewSelected(context: Context) {
            binding.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.secondary))
            binding.tvInitial.setTextColor(ContextCompat.getColor(context, R.color.background))
        }

        private fun setViewUnSelected(context: Context) {
            binding.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background))
            binding.tvInitial.setTextColor(ContextCompat.getColor(context, R.color.secondary))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemButtonEmitenBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_button_emiten, parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = items[position]
        holder.bind(element, itemSelected)
        holder.itemView.setOnClickListener { listener.onItemClick(element) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    public interface Listener{
        fun onItemClick(v: String)
    }
}