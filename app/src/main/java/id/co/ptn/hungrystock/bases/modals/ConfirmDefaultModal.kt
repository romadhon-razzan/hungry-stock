package id.co.ptn.hungrystock.bases.modals

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseBottomSheetModal
import id.co.ptn.hungrystock.databinding.ModalConfirmDefaultBinding

class ConfirmDefaultModal constructor(private val title: String,
                                     private val message: String,
                                     private val positiveButtonTitle: String = "Yes",
                                     private val negativeButtonTitle: String = "No"): BaseBottomSheetModal() {
    private lateinit var binding: ModalConfirmDefaultBinding
    private lateinit var listener: ConfirmDefaultModalListener

    public fun setListener(listener: ConfirmDefaultModalListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.modal_confirm_default,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        title.let { binding.cSt.title.text = it }
        positiveButtonTitle.let { binding.cBp.primaryButton.text = it }
        negativeButtonTitle.let { binding.cBt.textButton.text = it }

        binding.cMessage.label.gravity = Gravity.CENTER
        message.let { binding.cMessage.label.text = it }
        initListener()
    }

    private fun initListener() {
        binding.cBp.primaryButton.setOnClickListener { listener.onPositiveAction() }
        binding.cBt.textButton.setOnClickListener { listener.onNegativeAction() }
    }

    public interface ConfirmDefaultModalListener {
        fun onPositiveAction()
        fun onNegativeAction()
    }

}