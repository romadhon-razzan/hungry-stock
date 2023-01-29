package id.co.ptn.hungrystock.ui.main.research.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rajat.pdfviewer.PdfViewerActivity
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.core.SessionManager
import id.co.ptn.hungrystock.databinding.*
import id.co.ptn.hungrystock.models.OnboardPageTwo
import id.co.ptn.hungrystock.models.User
import id.co.ptn.hungrystock.models.main.home.PastEvent
import id.co.ptn.hungrystock.models.main.research.ResearchReport
import id.co.ptn.hungrystock.models.main.research.ResearchReportData
import id.co.ptn.hungrystock.models.registration.MainRegistration
import id.co.ptn.hungrystock.models.registration.RegistrationItem

class ResearchReportListAdapter(
    private val fragmentManager: FragmentManager,
    private val items: MutableList<ResearchReportData>
):
    RecyclerView.Adapter<ResearchReportListAdapter.ViewHolder>() {
    private lateinit var context: Context
    class ViewHolder(var binding: ItemResearchAndDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResearchReportData, context: Context, fragmentManager: FragmentManager) {
            var name = ""
            item.value.let { name = it }
            binding.tvTitle.text = name
            item.photo_url.let { Glide.with(context).load(it).into(binding.image) }
            item.file_url.let { url ->
                binding.item.setOnClickListener {
                    val sessionManager = SessionManager.getInstance(context)
                    if (!User.isExpired(fragmentManager, sessionManager.user.membership_end_at)){
                        context.startActivity(
                            PdfViewerActivity.launchPdfFromUrl(
                                context,
                                url,
                                name,
                                "",
                                enableDownload = false
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding: ItemResearchAndDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_research_and_data, parent, false)
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