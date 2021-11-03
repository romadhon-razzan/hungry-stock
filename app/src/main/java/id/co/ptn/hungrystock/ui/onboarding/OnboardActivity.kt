package id.co.ptn.hungrystock.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.databinding.ActivityOnboardingBinding
import id.co.ptn.hungrystock.router.Router

class OnboardActivity : BaseActivity() {
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)

        init()
    }

    private fun init() {
        setView()
        listener()
    }

    private fun setView() {
        binding.cBp.primaryButton.text = "Masuk"
        binding.cBt.textButton.text = "Daftar Sekarang"
    }

    private fun listener() {
        binding.cBp.primaryButton.setOnClickListener { router.toAuth() }
        binding.cBt.textButton.setOnClickListener { router.toRegistration() }
    }
}