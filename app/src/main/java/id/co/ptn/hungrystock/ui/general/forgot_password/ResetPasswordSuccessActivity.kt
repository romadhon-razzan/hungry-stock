package id.co.ptn.hungrystock.ui.general.forgot_password

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.databinding.ActivityResetPasswordSuccessBinding

class ResetPasswordSuccessActivity : BaseActivity() {
    private lateinit var binding: ActivityResetPasswordSuccessBinding

    private var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password_success)
        init()
    }

    private fun init() {
        setToolbar(binding.toolbar,"")
        initIntent()
        setListener()
    }

    private fun initIntent() {
        intent?.extras?.let {
            if (it.containsKey("email"))
                email = it.getString("email","")
        }
    }

    private fun setListener() {
        binding.btSubmit.setOnClickListener {
            router.toExit()
        }
    }
}