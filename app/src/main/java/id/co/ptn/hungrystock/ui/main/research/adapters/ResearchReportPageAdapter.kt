package id.co.ptn.hungrystock.ui.main.research.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.*
import id.co.ptn.hungrystock.models.main.home.PastEvent
import id.co.ptn.hungrystock.models.main.home.UpcomingEvent
import id.co.ptn.hungrystock.models.main.research.ResearchPage
import id.co.ptn.hungrystock.models.main.research.ResearchReport
import java.lang.StringBuilder

class ResearchReportPageAdapter(private val items: MutableList<ResearchPage>,
private val listener: ResearchReportListener):
    RecyclerView.Adapter<ResearchReportPageAdapter.ViewHolder>() {
    private lateinit var context: Context

    companion object {
        const val TYPE_SORTING = 0
        const val TYPE_FILTER = 1
        const val TYPE_LIST = 2
    }

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class SortingHolder(var binding: ItemSortingBinding,
    var context: Context) : ViewHolder(binding.root) {
        fun sortingPressed( listener: ResearchReportListener) {
            val popup = PopupMenu(context, binding.btSorting)
            popup.inflate(R.menu.sorting_research_menu)
            popup.setOnMenuItemClickListener { item: MenuItem? ->
                item?.let {
                    when (it.itemId) {
                        R.id.terbaru -> {
                            binding.lblSorting.text = context.resources.getString(R.string.sorting_terbaru)
//                            viewModel.setSortingLabel(resources.getString(R.string.sorting_terbaru))
//                            viewModel.setCategory("")
                        }
                        R.id.one_pager -> {
                            binding.lblSorting.text = context.resources.getString(R.string.one_pager)
//                            viewModel.setSortingLabel(resources.getString(R.string.sorting_top_up_knowledge_amp_wisdom))
//                            viewModel.setCategory(viewModel.sortingLabel.value.toString())
                        }
                        R.id.stock_discovery -> {
                            binding.lblSorting.text = context.resources.getString(R.string.stock_discovery)
//                            viewModel.setSortingLabel(resources.getString(R.string.sorting_temu_emiten))
//                            viewModel.setCategory(viewModel.sortingLabel.value.toString())
                        }
                        R.id.takeaway_emiten -> {
                            binding.lblSorting.text = context.resources.getString(R.string.takeaway_temu_emiten)
//                            viewModel.setSortingLabel(resources.getString(R.string.sorting_bedah_emiten))
//                            viewModel.setCategory(viewModel.sortingLabel.value.toString())
                        }
                        R.id.bedah_emiten -> {
                            binding.lblSorting.text = context.resources.getString(R.string.bedah_emiten)
//                            viewModel.setSortingLabel(resources.getString(R.string.sorting_stockscope))
//                            viewModel.setCategory(viewModel.sortingLabel.value.toString())
                        }
                        R.id.understanding_sector -> {
                            binding.lblSorting.text = context.resources.getString(R.string.understanding_sector)
//                            viewModel.setSortingLabel(resources.getString(R.string.sorting_belajar_invest_bareng))
//                            viewModel.setCategory(viewModel.sortingLabel.value.toString())
                        }
                    }
                }
                listener.onSorting(binding.lblSorting.text.toString())
                true
            }

            popup.show()

        }
    }

    inner class FilterHolder(var binding: ItemFilterBinding) : ViewHolder(binding.root) {

    }

    inner class ListHolder(var binding: ItemRecyclerViewBinding) : ViewHolder(binding.root) {
        private lateinit var listAdapter: MainResearchReportListAdapter
        fun initList(items: MutableList<ResearchReport>, context: Context) {
            listAdapter = MainResearchReportListAdapter(items)
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
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
                SortingHolder(binding, context)
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
                viewHolder.binding.lblSorting.text = element.sorting.value
                viewHolder.binding.btSorting.setOnClickListener {
                    viewHolder.sortingPressed(listener)
                }
               }
            ResearchPage.TYPE_FILTER -> {
                val viewHolder = holder as FilterHolder
                val stringBuilder = StringBuilder()
                element.filter.forEach { stringBuilder.append(it.value).append(", ") }
                viewHolder.binding.tvValue.text = stringBuilder.toString()
            }
            else -> {
                val viewHolder = holder as ListHolder
                viewHolder.initList(element.researchData as MutableList<ResearchReport>, context)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    public interface ResearchReportListener {
        fun onFilterClick()
        fun onSorting(value: String)
    }
}