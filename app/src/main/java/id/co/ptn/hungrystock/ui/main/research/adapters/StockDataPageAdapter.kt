package id.co.ptn.hungrystock.ui.main.research.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.*
import id.co.ptn.hungrystock.models.main.home.Event
import id.co.ptn.hungrystock.models.main.home.PastEvent
import id.co.ptn.hungrystock.models.main.home.UpcomingEvent
import id.co.ptn.hungrystock.models.main.research.ResearchPage
import id.co.ptn.hungrystock.models.main.research.ResearchReport
import id.co.ptn.hungrystock.models.main.research.StockData

class StockDataPageAdapter(private val items: MutableList<ResearchPage>):
    RecyclerView.Adapter<StockDataPageAdapter.ViewHolder>() {
    private lateinit var context: Context

    companion object {
        const val TYPE_SORTING = 0
        const val TYPE_FILTER = 1
        const val TYPE_LIST = 2
    }

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class SortingHolder(var binding: ItemSortingBinding) : ViewHolder(binding.root) {

    }

    inner class FilterHolder(var binding: ItemFilterBinding) : ViewHolder(binding.root) {

    }

    inner class ListHolder(var binding: ItemRecyclerViewBinding) : ViewHolder(binding.root) {
        private lateinit var listAdapter: StockDataListAdapter
        fun initList(items: MutableList<StockData>, context: Context) {
            listAdapter = StockDataListAdapter(items)
            binding.recyclerView.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = listAdapter
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            ResearchPage.TYPE_SORTING -> TYPE_SORTING
            ResearchPage.TYPE_FILTER -> TYPE_FILTER
            else -> TYPE_LIST
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return when(viewType) {
            TYPE_SORTING -> {
                val binding: ItemSortingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_sorting, parent, false)
                SortingHolder(binding)
            }
            TYPE_FILTER -> {
                val binding: ItemFilterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_filter, parent, false)
                FilterHolder(binding)
            }
            else -> {
                val binding: ItemRecyclerViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_recycler_view, parent, false)
                ListHolder(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = items[position]
        when(element.type) {
            ResearchPage.TYPE_SORTING -> {
                val viewHolder = holder as SortingHolder
               }
            ResearchPage.TYPE_FILTER -> {
                val viewHolder = holder as FilterHolder
            }
            else -> {
                val viewHolder = holder as ListHolder
                viewHolder.initList(element.stockData as MutableList<StockData>, context)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}