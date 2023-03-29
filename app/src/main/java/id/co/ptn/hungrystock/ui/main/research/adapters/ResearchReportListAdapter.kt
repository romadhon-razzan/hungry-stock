package id.co.ptn.hungrystock.ui.main.research.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.rajat.pdfviewer.PdfViewerActivity
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.core.SessionManager
import id.co.ptn.hungrystock.databinding.*
import id.co.ptn.hungrystock.models.User
import id.co.ptn.hungrystock.models.main.research.ResponseResearchData
import id.co.ptn.hungrystock.utils.MediaUtils
import id.co.ptn.hungrystock.utils.getDateMMMMddyyyy

class ResearchReportListAdapter(
    private val fragmentManager: FragmentManager,
    private val items: MutableList<ResponseResearchData>
):
    RecyclerView.Adapter<ResearchReportListAdapter.ViewHolder>() {
    private lateinit var context: Context
    class ViewHolder(var binding: ItemResearchAndDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseResearchData, context: Context, fragmentManager: FragmentManager) {

            binding.tvTitle.text = item.title ?: ""
            binding.tvDate.text = getDateMMMMddyyyy((item.date ?: 0) * 1000)
            MediaUtils(context).setImageFromUrl(binding.image, item.imageFile ?: "", R.drawable.img_reseach_placeholder)
            item.file?.let { url ->
                binding.item.setOnClickListener {
                    val sessionManager = SessionManager.getInstance(context)
                    if (!User.isExpired(fragmentManager, sessionManager.user?.membershipExpDate ?: 0)){
                        context.startActivity(
                            PdfViewerActivity.launchPdfFromUrl(
                                context,
                                url,
                                item.title ?: "",
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