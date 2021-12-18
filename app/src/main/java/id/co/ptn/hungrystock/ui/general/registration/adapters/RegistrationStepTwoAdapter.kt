package id.co.ptn.hungrystock.ui.general.registration.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.ItemOnboarding2Binding
import id.co.ptn.hungrystock.databinding.ItemRegistrationStep2Binding
import id.co.ptn.hungrystock.models.OnboardPageTwo
import id.co.ptn.hungrystock.models.registration.MainRegistration
import id.co.ptn.hungrystock.models.registration.RegistrationItem

class RegistrationStepTwoAdapter(private val items: MutableList<MainRegistration>):
    RecyclerView.Adapter<RegistrationStepTwoAdapter.ViewHolder>() {
    private lateinit var context: Context
    class ViewHolder(binding: ItemRegistrationStep2Binding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ItemRegistrationStep2Binding = binding
        lateinit var registrationItemAdapter: RegistrationItemAdapter
        fun initList(items: MutableList<RegistrationItem>, context: Context) {
            registrationItemAdapter = RegistrationItemAdapter(items)
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = registrationItemAdapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding: ItemRegistrationStep2Binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_registration_step_2, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = items[position]
        element.title.let { holder.binding.tvTitle.text = it }
        holder.initList(element.items, context)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}