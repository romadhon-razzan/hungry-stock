package id.co.ptn.hungrystock.ui.main.hsro.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.*
import id.co.ptn.hungrystock.models.OnboardPageTwo
import id.co.ptn.hungrystock.models.main.home.PastEvent
import id.co.ptn.hungrystock.models.main.hsro.Hsro
import id.co.ptn.hungrystock.models.main.research.ResearchReport
import id.co.ptn.hungrystock.models.main.research.StockData
import id.co.ptn.hungrystock.models.registration.MainRegistration
import id.co.ptn.hungrystock.models.registration.RegistrationItem

class HsroListAdapter(private val items: MutableList<Hsro>, val listener: HsroListener):
    RecyclerView.Adapter<HsroListAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemHsroBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Hsro) {
            item.code.let { binding.tvCode.text = it }
            item.name.let { binding.tvCompanyName.text = it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemHsroBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_hsro, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = items[position]
        holder.bind(element)
        holder.itemView.setOnClickListener { listener.onItemClick(element) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    public interface HsroListener {
        fun onItemClick(hsro: Hsro)
    }
}