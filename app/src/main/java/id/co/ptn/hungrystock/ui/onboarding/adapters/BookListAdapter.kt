package id.co.ptn.hungrystock.ui.onboarding.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.*
import id.co.ptn.hungrystock.models.landing.ResponseBooksData
import id.co.ptn.hungrystock.models.onboard.Books
import id.co.ptn.hungrystock.utils.MediaUtils

class BookListAdapter(private val items: MutableList<ResponseBooksData>, private val listener: Listener):
    RecyclerView.Adapter<BookListAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemBookBinding, var context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseBooksData) {
            MediaUtils(context).setImageFromUrl(binding.image, item.imageFile ?: "", R.drawable.img_reseach_placeholder)
            binding.tvTitle.text = item.name ?: ""
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
        fun itemClicked(books: ResponseBooksData)
    }
}