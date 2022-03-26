package id.co.ptn.hungrystock.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.config.SUCCESS
import id.co.ptn.hungrystock.databinding.ActivityOnboardingBinding
import id.co.ptn.hungrystock.router.Router
import id.co.ptn.hungrystock.ui.onboarding.adapters.OnboardVPAdapter

@AndroidEntryPoint
class OnboardActivity : BaseActivity() {
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)
        changeStatusBar()
        init()
    }

    private fun init() {
        setView()
        listener()
    }

    private fun setView() {
        binding.viewPager.adapter = OnboardVPAdapter(supportFragmentManager)
    }

    private fun listener() {
        binding.checkbox.setOnClickListener {
            binding.checkbox.isChecked = false
            resultLauncher.launch(router.toPrivacyPolice())
        }
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == SUCCESS) {
            binding.checkbox.isChecked = true
        }
    }

}