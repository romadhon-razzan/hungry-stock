package id.co.ptn.hungrystock.ui.general.registration.adapters

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.ItemRegistrationStep2Binding
import id.co.ptn.hungrystock.models.registration.MainRegistration
import id.co.ptn.hungrystock.models.registration.RegistrationItem

class RegistrationStepTwoAdapter(
    private val items: MutableList<MainRegistration>,
    private val listener: Listener):
    RecyclerView.Adapter<RegistrationStepTwoAdapter.ViewHolder>() {
    private lateinit var context: Context
    class ViewHolder(var binding: ItemRegistrationStep2Binding, val listener: Listener) : RecyclerView.ViewHolder(binding.root) {
        lateinit var registrationItemAdapter: RegistrationItemAdapter
        fun initList(items: MutableList<RegistrationItem>, context: Context, listener: HolderListener) {
            registrationItemAdapter = RegistrationItemAdapter(context, items, object : RegistrationItemAdapter.Listener{
                override fun onItemPressed(position: Int) {
                    binding.recyclerView.requestFocus()
                    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.recyclerView.windowToken, 0)

                    items.forEachIndexed { index, registrationItem ->
                        registrationItem.selected = index == position
                        registrationItemAdapter.notifyItemChanged(index)
                    }
                    if (position == items.size -1) {
                        listener.onClear()
                    } else {
                        listener.onItemClick(items[position].title)
                    }
                }
            })
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = registrationItemAdapter
            }
        }

        public interface HolderListener {
            fun onItemClick(value: String)
            fun onClear()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding: ItemRegistrationStep2Binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_registration_step_2, parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = items[position]
        element.title.let { holder.binding.tvTitle.text = it }
        holder.initList(element.items, context, object : ViewHolder.HolderListener{
            override fun onItemClick(value: String) {
                element.error = false
                element.otherActive = false
                notifyItemChanged(position)
                listener.onOtherField(position, value)
            }

            override fun onClear() {
                element.error = true
                element.otherActive = true
                notifyItemChanged(position)
                listener.onOtherField(position, "")
            }
        })

        if (element.otherActive) {
            holder.binding.cardEtFullName.visibility = View.VISIBLE
        } else {
            holder.binding.cardEtFullName.visibility = View.GONE
        }

        holder.binding.etOther.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener.onOtherField(position, s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

        })
        if (element.error)
            holder.binding.error.text.text = context.getText(R.string.message_need_fill)
        else
            holder.binding.error.text.text = ""
    }

    override fun getItemCount(): Int {
        return items.size
    }

    public interface Listener{
        fun onOtherField(position: Int, value: String)
    }
}