package id.co.ptn.hungrystock.ui.privacy_police

import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.bases.BaseWebViewActivity
import id.co.ptn.hungrystock.config.PRIVACY_POLICY_URL
import id.co.ptn.hungrystock.config.SUCCESS
import id.co.ptn.hungrystock.databinding.ActivityPrivacyPoliceBinding
import id.co.ptn.hungrystock.ui.privacy_police.view_model.PrivacyPoliceViewModel

@AndroidEntryPoint
class PrivacyPoliceActivity : BaseActivity() {
    private lateinit var binding: ActivityPrivacyPoliceBinding
    private val viewModel: PrivacyPoliceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_police)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        init()
    }

    private fun init() {
        setToolbar(binding.toolbar,"")
        setView()
        setListener()
    }

    private fun setView() {
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(PRIVACY_POLICY_URL)
//        binding.webView.loadData(data(), "text/html", "utf-8")
    }

    private fun setListener() {
        binding.btAgree.setOnClickListener {
            setResult(SUCCESS)
            finish()
        }
    }
}