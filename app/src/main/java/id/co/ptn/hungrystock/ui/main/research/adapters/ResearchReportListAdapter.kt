package id.co.ptn.hungrystock.ui.main.research.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.*
import id.co.ptn.hungrystock.models.OnboardPageTwo
import id.co.ptn.hungrystock.models.main.home.PastEvent
import id.co.ptn.hungrystock.models.main.research.ResearchReport
import id.co.ptn.hungrystock.models.registration.MainRegistration
import id.co.ptn.hungrystock.models.registration.RegistrationItem

class ResearchReportListAdapter(private val items: MutableList<ResearchReport>):
    RecyclerView.Adapter<ResearchReportListAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemResearchAndDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PastEvent) {
            item.title.let { binding.tvTitle.text = it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemResearchAndDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_research_and_data, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val element = items[position]
//        holder.bind(element)
    }

    override fun getItemCount(): Int {
        return 4
    }
}