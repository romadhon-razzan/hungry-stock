package id.co.ptn.hungrystock.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.ui.general.auth.AuthActivity
import id.co.ptn.hungrystock.ui.main.MainActivity
import id.co.ptn.hungrystock.ui.reference.ReferenceLayoutActivity

@AndroidEntryPoint
class SplashScreenActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        changeStatusBar()
        init()
    }

    private fun init() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (sessionManager.token.isEmpty()) router.toOnboard()
            else router.toMain()
            finish()
        }, 2000)
    }

}