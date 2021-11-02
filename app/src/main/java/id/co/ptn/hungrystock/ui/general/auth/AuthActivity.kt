package id.co.ptn.hungrystock.ui.general.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.databinding.ActivityAuthBinding

class AuthActivity : BaseActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)

        binding.cBp.primaryButton.text = "Primary Button"
        binding.cBp.primaryButton.setOnClickListener { sessionManager.setToken("A") }
        binding.cBo.outlinedButton.text = "Outlined Button"
        binding.cBt.textButton.text = "Text Button"
    }
}