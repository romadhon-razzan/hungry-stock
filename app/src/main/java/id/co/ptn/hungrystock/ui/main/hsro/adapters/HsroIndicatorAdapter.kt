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
import id.co.ptn.hungrystock.models.main.hsro.HsroIndicator
import id.co.ptn.hungrystock.models.main.research.ResearchReport
import id.co.ptn.hungrystock.models.main.research.StockData
import id.co.ptn.hungrystock.models.registration.MainRegistration
import id.co.ptn.hungrystock.models.registration.RegistrationItem

class HsroIndicatorAdapter(private val items: MutableList<HsroIndicator>):
    RecyclerView.Adapter<HsroIndicatorAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemHsroIndicatorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HsroIndicator) {
            item.code.let { binding.tvCode.text = it }
            item.value.let { binding.tvValue.text = it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemHsroIndicatorBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_hsro_indicator, parent, false)
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