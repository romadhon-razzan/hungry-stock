package id.co.ptn.hungrystock.ui.onboarding.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.*
import id.co.ptn.hungrystock.models.onboard.Books

class BookListAdapter(private val items: MutableList<Books>, private val listener: Listener):
    RecyclerView.Adapter<BookListAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemBookBinding, var context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Books) {
            item.photo_url.let { Glide.with(context).load(it).into(binding.image) }
            item.title.let { binding.tvTitle.text = it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemBookBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_book, parent, false)
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

    public interface Listener {
        fun itemClicked(books: Books)
    }
}