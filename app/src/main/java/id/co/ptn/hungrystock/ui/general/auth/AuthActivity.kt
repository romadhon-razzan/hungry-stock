package id.co.ptn.hungrystock.ui.general.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.databinding.ActivityAuthBinding
import id.co.ptn.hungrystock.helper.Keyboard
import id.co.ptn.hungrystock.ui.general.view_model.AuthViewModel

class AuthActivity : BaseActivity() {
    private lateinit var binding: ActivityAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)

        init()
    }

    private fun init() {
        setToolbar(binding.cAppBar.toolbar,"Login")
        setView()
        listener()
        setObserve()
    }

    private fun setView() {
        binding.cLId.label.text = "ID"
        binding.cTfId.textField.hint = "Masukkan ID"

        binding.cLPassword.label.text = "kata Sandi"
        binding.cTfPassword.textField.hint = "Masukkan Kata Sandi"

        binding.cBp.primaryButton.text = "Masuk"
        binding.cBt.textButton.text = "Daftar Sekarang"
    }

    private fun listener() {
        binding.cBp.primaryButton.setOnClickListener { loginPressed() }
        binding.cBt.textButton.setOnClickListener {  }
    }

    private fun loginPressed() {
        Keyboard(this).hide()
        if (binding.cTfId.textField.text.toString().isEmpty()) {
            showSnackBar(binding.container, "ID harus diisi")
        } else {
            if (binding.cTfPassword.textField.text.toString().isEmpty()) {
                showSnackBar(binding.container, "Kata sandi harus diisi")
            } else {
                apiAuth()
            }
        }
    }

    private fun setObserve() {

    }

    /**
     * Api
     * */

    private fun apiAuth() {

    }
}