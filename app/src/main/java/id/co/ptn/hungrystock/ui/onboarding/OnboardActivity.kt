package id.co.ptn.hungrystock.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.bases.WebViewFragment
import id.co.ptn.hungrystock.config.SUCCESS
import id.co.ptn.hungrystock.databinding.ActivityOnboardingBinding
import id.co.ptn.hungrystock.router.Router
import id.co.ptn.hungrystock.ui.onboarding.adapters.OnboardVPAdapter
import id.co.ptn.hungrystock.ui.onboarding.view_model.OnboardViewModel
import id.co.ptn.hungrystock.utils.Status
import id.co.ptn.hungrystock.utils.getDateMMMMddyyyy
import java.lang.Exception
import java.lang.StringBuilder

@AndroidEntryPoint
class OnboardActivity : BaseActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private var viewModel: OnboardViewModel? = null
    var agree = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)
        viewModel = ViewModelProvider(this)[OnboardViewModel::class.java]
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        changeStatusBar()
        init()
    }

    private fun init() {
        setObserve()
        apiGetOnboard()
        listener()
    }

    private fun setView() {
        viewModel?.setShowPrivacyPolicy(true)
        if (!sessionManager.readPrivacyPolice)
            Handler(Looper.getMainLooper()).postDelayed({
                resultLauncher.launch(router.toPrivacyPolice())
            },1000)
        else
            binding.checkbox.isChecked = true

        binding.viewPager.adapter = OnboardVPAdapter(supportFragmentManager)
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
            sessionManager.setReadPrivacyPolice(true)
        }
    }

    private fun setObserve() {
        viewModel?.reqOnboardResponse()?.observe(this){
            when(it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    setView()
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    router.toAuth()
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    /**
     * Api
     * */

    private fun apiGetOnboard() {
        viewModel?.apiGetOnboard()
    }

}