package id.co.ptn.hungrystock.ui.general.registration

import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.databinding.ActivityRegisterV2Binding


@AndroidEntryPoint
class RegistrationV2Activity : BaseActivity() {
    private lateinit var binding: ActivityRegisterV2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_v2)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        changeStatusBar()
        init()
    }

    private fun init() {
        setToolbar(binding.toolbar, "")
        binding.lifecycleOwner = this
        setView()
        setListener()
    }

    private fun setView() {
        binding.webView.webViewClient = webclient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("${ENV.webUrl()}mobile-registration")
    }

    private fun setListener() {

    }

    inner class webclient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.progressBar.visibility = View.GONE
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            view.loadUrl(url!!)
            return super.shouldOverrideUrlLoading(view, url)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
            binding.webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}