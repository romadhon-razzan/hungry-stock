package id.co.ptn.hungrystock.ui.general.registration

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.databinding.ActivityRegistrationBinding
import id.co.ptn.hungrystock.models.registration.RequestRegistrationStepOne
import id.co.ptn.hungrystock.ui.general.registration.adapters.RegistrationStepAdapter

@AndroidEntryPoint
class RegistrationActivity : BaseActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    var requestRegistrationStepOne: RequestRegistrationStepOne? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
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
            } else binding.progressBar2.progress = 100
        } else if (p == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.progressBar2.setProgress(50, true)
            } else binding.progressBar2.progress = 50
        }
        binding.textView3.text = getString(R.string.label_halaman_1_dari_n,""+(p+1),"2")
    }

    fun toRegistrationSuccess() {
        router.toRegistrationSuccess()
        finish()
    }

    override fun onBackPressed() {
        val currentPage = binding.viewPager2.currentItem
        if (currentPage == 0)
            finish()
        else changePage(0)
    }

}