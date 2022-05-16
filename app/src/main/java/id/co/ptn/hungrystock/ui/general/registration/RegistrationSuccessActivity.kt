package id.co.ptn.hungrystock.ui.general.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.databinding.ActivityRegistrationSuccessBinding

@AndroidEntryPoint
class RegistrationSuccessActivity : BaseActivity() {
    private lateinit var binding: ActivityRegistrationSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration_success)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        changeStatusBar()
        init()
    }

    private fun init() {
        initListener()
    }

    private fun initListener() {
        binding.btBack.setOnClickListener {
            finish()
            router.toOnboard() }
    }
}