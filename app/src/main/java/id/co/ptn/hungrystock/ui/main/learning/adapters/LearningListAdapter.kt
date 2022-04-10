package id.co.ptn.hungrystock.ui.main.learning.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.*
import id.co.ptn.hungrystock.models.main.learning.Learning
import id.co.ptn.hungrystock.utils.getDateMMMMddyyyy

class LearningListAdapter(private val items: MutableList<Learning>, private val listener: LearningListener):
    RecyclerView.Adapter<LearningListAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemLearningBinding, var context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Learning) {
            item.photo_url.let { Glide.with(context).load(it).into(binding.image) }
            item.title.let { binding.tvTitle.text = it }
            item.event_date?.let {
                binding.tvSubTitle.text = getDateMMMMddyyyy(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemLearningBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_learning, parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = items[position]
        holder.bind(element)
        holder.itemView.setOnClickListener { listener.itemClicked(element) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    public interface LearningListener {
        fun itemClicked(learning: Learning)
    }
}