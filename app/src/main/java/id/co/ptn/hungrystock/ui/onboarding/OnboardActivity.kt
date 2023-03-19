package id.co.ptn.hungrystock.ui.onboarding

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.config.SUCCESS
import id.co.ptn.hungrystock.databinding.ActivityOnboardingBinding
import id.co.ptn.hungrystock.ui.general.view_model.AuthViewModel
import id.co.ptn.hungrystock.ui.onboarding.adapters.OnboardVPAdapter
import id.co.ptn.hungrystock.ui.onboarding.view_model.OnboardViewModel

@AndroidEntryPoint
class OnboardActivity : BaseActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private var viewModel: OnboardViewModel? = null
    private var authViewModel: AuthViewModel? = null
    var agree = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)
        viewModel = ViewModelProvider(this)[OnboardViewModel::class.java]
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        changeStatusBar()
        init()
    }

    private fun init() {
        listener()
        setView()
    }

    private fun setView() {
        viewModel?.setShowPrivacyPolicy(true)
        if (sessionManager?.readPrivacyPolice == false)
            Handler(Looper.getMainLooper()).postDelayed({
                resultLauncher.launch(router.toPrivacyPolice())
            },1000)
        else
            binding.checkbox.isChecked = true

        binding.viewPager.adapter = OnboardVPAdapter(supportFragmentManager)
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                viewModel?.setPageLatestEvent(position == OnboardVPAdapter.PAGE_LATEST_EVENT)
                viewModel?.setPageWebinar(position == OnboardVPAdapter.PAGE_WEBINAR)
                viewModel?.setPageBooks(position == OnboardVPAdapter.PAGE_BOOKS)
            }

        })
        binding.viewPager.offscreenPageLimit = 5
    }

    private fun listener() {
        binding.checkbox.setOnClickListener {
            binding.checkbox.isChecked = agree
            resultLauncher.launch(router.toPrivacyPolice())
        }
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == SUCCESS) {
            agree = true
            binding.checkbox.isChecked = true
            sessionManager?.setReadPrivacyPolice(true)
        }
    }


}