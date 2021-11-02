package id.co.ptn.hungrystock.bases.modals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseBottomSheetModal
import id.co.ptn.hungrystock.databinding.ModalSuggestedBinding
import id.co.ptn.hungrystock.databinding.ModalSuggestedOptionBinding

class SuggestedOptionModal constructor(private val title: String,
                                       private val message: String,
                                       private val positiveButtonTitle: String = "Yes",
                                       private val negativeButtonTitle: String = "No"): BaseBottomSheetModal() {
    private lateinit var binding: ModalSuggestedOptionBinding
    private lateinit var listener: SuggestedModalListener

    public fun setListener(listener: SuggestedModalListener) {
        this.listener = listener
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),  R.layout.modal_suggested_option, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        title.let { binding.cSt.title.text = it }
        message.let { binding.cMessage.label.text = it }
        positiveButtonTitle.let { binding.cBp.primaryButton.text = it }
        negativeButtonTitle.let { binding.cBt.textButton.text = it }
        initListener()
    }

    private fun initListener() {
        binding.btClose.setOnClickListener { dismiss() }
    }

    public interface SuggestedModalListener{
        fun onPositiveAction()
        fun  onNegativeAction()
    }
}