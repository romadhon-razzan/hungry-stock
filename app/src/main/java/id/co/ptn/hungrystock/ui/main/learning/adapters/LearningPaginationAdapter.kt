package id.co.ptn.hungrystock.ui.main.learning.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.*
import id.co.ptn.hungrystock.models.Links
import id.co.ptn.hungrystock.models.main.learning.Learning
import id.co.ptn.hungrystock.utils.getDateMMMMddyyyy

class LearningPaginationAdapter(private val items: MutableList<Links>, private val listener: LearningListener):
    RecyclerView.Adapter<LearningPaginationAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemLinkPaginationBinding, var context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Links) {
            item.active?.let {
                if (it){
                    binding.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.secondary))
                } else {
                    binding.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
                }
            }
            item.label?.let {
                binding.tvValue.text = it
                when {
                    it.lowercase().contains(Links.previous) -> {
                        binding.tvValue.visibility = View.GONE
                        binding.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_prev_arrow))
                    }
                    it.lowercase().contains(Links.next) -> {
                        binding.tvValue.visibility = View.GONE
                        binding.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_next_arrow))
                    }
                    else -> {
                        binding.tvValue.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemLinkPaginationBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_link_pagination, parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = items[position]
        holder.bind(element)
        holder.itemView.setOnClickListener {
            listener.itemClicked(element, position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    public interface LearningListener {
        fun itemClicked(page: Links, position: Int)
    }
}