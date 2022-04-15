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

class FilterYearListAdapter(
    private val items: MutableList<Int>,
    private val positionClicked: Int,
    private val listener: Listener):
    RecyclerView.Adapter<FilterYearListAdapter.ViewHolder>() {
    private lateinit var context: Context
    class ViewHolder(var binding: ItemButtonYearBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) {
            item.let { binding.tvYear.text = it.toString() }
        }

        fun setViewSelected(context: Context) {
            binding.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.secondary))
            binding.tvYear.setTextColor(ContextCompat.getColor(context, R.color.background))
        }

        fun setViewUnSelected(context: Context) {
            binding.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background))
            binding.tvYear.setTextColor(ContextCompat.getColor(context, R.color.secondary))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding: ItemButtonYearBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_button_year, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = items[position]
        holder.itemView.setOnClickListener { listener.onClick(element.toString()) }
        holder.bind(element)
        if (positionClicked == element)
            holder.setViewSelected(context)
        else holder.setViewUnSelected(context)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    public interface Listener{
        fun onClick(year: String)
    }
}