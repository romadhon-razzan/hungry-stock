package id.co.ptn.hungrystock.ui.general.registration

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.databinding.ActivityRegistrationBinding
import id.co.ptn.hungrystock.ui.general.registration.adapters.RegistrationStepAdapter
import id.co.ptn.hungrystock.ui.onboarding.adapters.OnboardVPAdapter

@AndroidEntryPoint
class RegistrationActivity : BaseActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        changeStatusBar()
        init()
    }

    private fun init() {
        binding.textView3.text = getString(R.string.label_halaman_1_dari_n,"1","2")
        initStep()
    }

    private fun initStep() {
        binding.viewPager2.adapter = RegistrationStepAdapter(supportFragmentManager)
    }

    fun changePage(p: Int) {
        binding.viewPager2.currentItem = p
        updateProgressStep(p)
    }

    private fun updateProgressStep(p: Int) {
        if (p == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.progressBar2.setProgress(100, true)
            } else binding.progressBar2.setProgress(100)
        } else if (p == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.progressBar2.setProgress(50, true)
            } else binding.progressBar2.setProgress(50)
        }
        binding.textView3.setText(getString(R.string.label_halaman_1_dari_n,""+(p+1),"2"))
    }
}