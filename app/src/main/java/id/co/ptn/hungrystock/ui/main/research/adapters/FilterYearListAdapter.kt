package id.co.ptn.hungrystock.ui.main.research.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
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

class FilterYearListAdapter(private val items: MutableList<Int>):
    RecyclerView.Adapter<FilterYearListAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemButtonYearBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) {
            item.let { binding.tvYear.text = it.toString() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemButtonYearBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_button_year, parent, false)
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