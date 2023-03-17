package id.co.ptn.hungrystock.ui.profile

import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.bases.dialogs.ConfirmDialog
import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.databinding.ActivityProfileV2Binding
import id.co.ptn.hungrystock.ui.profile.view_model.ProfileViewModel


@AndroidEntryPoint
class ProfileV2Activity : BaseActivity() {
    private lateinit var binding: ActivityProfileV2Binding
    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_v2)
        binding.viewModel = viewModel
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        changeStatusBar()
        init()
    }

    private fun init() {
        setToolbar(binding.toolbar, resources.getString(R.string.title_profile))
        binding.lifecycleOwner = this
        setView()
        setListener()
    }

    private fun setView() {
        binding.webView.webViewClient = webclient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("${ENV.webUrl()}mobile-profile?customer=${sessionManager.authData?.code ?: ""}")
    }

    private fun setListener() {
        binding.buttonExit.setOnClickListener {
            val confirmDialog = ConfirmDialog(this)
            confirmDialog.setTitle("Keluar")
            confirmDialog.setMessage("Apakah Anda ingin keluar dari aplikasi?")
            confirmDialog.show(object : ConfirmDialog.Listener{
                override fun onPositiveClick() {
                    TOKEN = ""
                    sessionManager.destroy()
                    router.toExit()
                    finishAffinity()
                }

                override fun onNegativeClick() {}

            })
        }
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