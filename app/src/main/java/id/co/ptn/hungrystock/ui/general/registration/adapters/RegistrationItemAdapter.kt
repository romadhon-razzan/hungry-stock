package id.co.ptn.hungrystock.ui.general.registration.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.ItemOnboarding2Binding
import id.co.ptn.hungrystock.databinding.ItemRadioButtonBinding
import id.co.ptn.hungrystock.databinding.ItemRegistrationStep2Binding
import id.co.ptn.hungrystock.models.OnboardPageTwo
import id.co.ptn.hungrystock.models.registration.MainRegistration
import id.co.ptn.hungrystock.models.registration.RegistrationItem

class RegistrationItemAdapter(
    private val context: Context,
    private val items: MutableList<RegistrationItem>,
private val listener: Listener):
    RecyclerView.Adapter<RegistrationItemAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemRadioButtonBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ItemRadioButtonBinding = binding

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRadioButtonBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_radio_button, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = items[position]
        element.title.let { holder.binding.tvTitle.text = it }
        if (element.selected)
            holder.binding.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_radio_button_enabled))
        else
            holder.binding.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_radio_button_disabled))
        holder.binding.button.setOnClickListener { listener.onItemPressed(position)  }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    public interface Listener{
        fun onItemPressed(position: Int)
    }
}