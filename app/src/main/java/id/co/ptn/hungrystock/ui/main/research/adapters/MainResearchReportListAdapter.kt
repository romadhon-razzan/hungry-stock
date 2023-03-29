package id.co.ptn.hungrystock.ui.main.research.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.*
import id.co.ptn.hungrystock.models.main.research.ResearchReport
import id.co.ptn.hungrystock.models.main.research.ResearchReportData

class MainResearchReportListAdapter(
    private val fragmentManager: FragmentManager,
    private val items: MutableList<ResearchReport>):
    RecyclerView.Adapter<MainResearchReportListAdapter.ViewHolder>() {
    private lateinit var context: Context
    class ViewHolder(var binding: ItemResearchReportListBinding) : RecyclerView.ViewHolder(binding.root) {
//        private lateinit var listAdapter: ResearchReportListAdapter
        fun bind(item: ResearchReport, context: Context,fragmentManager: FragmentManager) {
            var title = ""
            item.title?.let { t -> title = t }
            binding.tvTitle.text = title
            initList(item.researchReportData as MutableList<ResearchReportData>, context, fragmentManager)
        }

        fun initList(items: MutableList<ResearchReportData>, context: Context, fragmentManager: FragmentManager) {
//            listAdapter = ResearchReportListAdapter(fragmentManager,items)
//            binding.recyclerView.apply {
//                layoutManager = GridLayoutManager(context, 2)
//                adapter = listAdapter
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding: ItemResearchReportListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_research_report_list, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = items[position]
        holder.bind(element, context, fragmentManager)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}