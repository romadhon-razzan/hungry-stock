package id.co.ptn.hungrystock.ui.main.learning.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.*
import id.co.ptn.hungrystock.models.main.learning.Learning

class LearningListAdapter(private val items: MutableList<Learning>):
    RecyclerView.Adapter<LearningListAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemLearningBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Learning) {
            item.title.let { binding.tvTitle.text = it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemLearningBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_learning, parent, false)
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