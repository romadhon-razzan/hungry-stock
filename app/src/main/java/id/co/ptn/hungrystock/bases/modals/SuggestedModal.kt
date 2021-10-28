package id.co.ptn.hungrystock.bases.modals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseBottomSheetModal
import id.co.ptn.hungrystock.databinding.ModalSuggestedBinding

class SuggestedModal constructor(private val title: String, private val message: String): BaseBottomSheetModal() {
    private lateinit var binding: ModalSuggestedBinding
    private lateinit var listener: SuggestedModalListener

    public fun setListener(listener: SuggestedModalListener) {
        this.listener = listener
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),  R.layout.modal_suggested, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.tvTitle.text = title
        initListener()
    }

    private fun initListener() {
        binding.btClose.setOnClickListener { dismiss() }
    }

    public interface SuggestedModalListener{
        fun onSuggestedAction()
        fun onOtherAction()
    }
}