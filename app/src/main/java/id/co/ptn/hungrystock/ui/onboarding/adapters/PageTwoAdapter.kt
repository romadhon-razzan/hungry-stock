package id.co.ptn.hungrystock.ui.onboarding.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.ItemOnboarding2Binding
import id.co.ptn.hungrystock.models.OnboardPageTwo

class PageTwoAdapter(private val items: MutableList<OnboardPageTwo>):
    RecyclerView.Adapter<PageTwoAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemOnboarding2Binding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ItemOnboarding2Binding = binding

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemOnboarding2Binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_onboarding_2, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = items[position]
        element.let { holder.binding.tvTitle.text = it.title }
        element.let { holder.binding.tvDescription.text = it.description }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}