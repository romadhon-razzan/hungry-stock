package id.co.ptn.hungrystock.ui.general.registration

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.bases.BaseWebViewActivity
import id.co.ptn.hungrystock.config.ENV


@AndroidEntryPoint
class RegistrationV2Activity : BaseWebViewActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        changeStatusBar()
        init()
    }

    private fun init() {
        setToolbar(binding.toolbar, "")
        binding.lifecycleOwner = this
        setView()
    }

    private fun setView() {
        binding.buttonAction.visibility = View.GONE
        loadUrl("${ENV.webUrl()}mobile-registration")
    }

}