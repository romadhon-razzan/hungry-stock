package id.co.ptn.hungrystock.ui.reference

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.bases.modals.InfoModal
import id.co.ptn.hungrystock.bases.modals.SuggestedModal
import id.co.ptn.hungrystock.bases.modals.SuggestedOptionModal
import id.co.ptn.hungrystock.databinding.ReferenceLayoutBinding

@AndroidEntryPoint
class ReferenceLayoutActivity: BaseActivity() {
    private lateinit var binding: ReferenceLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.reference_layout)


        binding.cTf1.textField.hint = "Input text field 1"
        binding.cTf2.textField.hint = "Input text field 2"

        binding.cBp.primaryButton.text = "Primary Button"
        binding.cBp.primaryButton.setOnClickListener { sessionManager.setToken("A") }
        binding.cBo.outlinedButton.text = "Print Text Field 1"
        binding.cBo.outlinedButton.setOnClickListener { Log.d("TEXT FIELD", binding.cTf1.textField.text.toString()) }
        binding.cBt.textButton.text = "Print Text Field 2"
        binding.cBt.textButton.setOnClickListener { Log.d("TEXT FIELD 2", binding.cTf2.textField.text.toString()) }

        binding.cBpModal.primaryButton.text = "Show Modal Info"
        binding.cBpModal.primaryButton.setOnClickListener {
            InfoModal("Info", "Detail infonya apa aja. Bisa dijelasin di sini ya. Pada bagian ini, Bisa ditambahkan ilustrasi. Makasih").show(supportFragmentManager,"info_modal")
        }

        binding.cBpModalSuggested.primaryButton.text = "Show Modal Suggested"
        binding.cBpModalSuggested.primaryButton.setOnClickListener {
            val modal = SuggestedModal("Suggested", "Detail infonya apa aja. Bisa dijelasin di sini ya. Pada bagian ini, Bisa ditambahkan ilustrasi. Makasih")
                modal.setListener(object : SuggestedModal.SuggestedModalListener {
                    override fun onSuggestedAction() {

                    }
                })
                modal.show(supportFragmentManager,"info_modal")
        }

        binding.cBpModalSuggestedOption.primaryButton.text = "Show Modal Suggested Option"
        binding.cBpModalSuggestedOption.primaryButton.setOnClickListener {
            val modal = SuggestedOptionModal("Suggested", "Detail infonya apa aja. Bisa dijelasin di sini ya. Pada bagian ini, Bisa ditambahkan ilustrasi. Makasih")
            modal.setListener(object : SuggestedOptionModal.SuggestedModalListener {
                override fun onPositiveAction() {
                    TODO("Not yet implemented")
                }

                override fun onNegativeAction() {
                    TODO("Not yet implemented")
                }

            })
            modal.show(supportFragmentManager,"info_modal")
        }
    }
}