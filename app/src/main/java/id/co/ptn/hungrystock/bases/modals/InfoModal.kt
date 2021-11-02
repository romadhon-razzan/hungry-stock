package id.co.ptn.hungrystock.bases.modals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseBottomSheetModal
import id.co.ptn.hungrystock.databinding.ModalInfoBinding

class InfoModal constructor(private val title: String, private val message: String): BaseBottomSheetModal() {

    private lateinit var binding: ModalInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.modal_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        title.let { binding.cSt.title.text = it }
        message.let { binding.cMessage.label.text = it }
        binding.btClose.setOnClickListener { dismiss() }
    }
}