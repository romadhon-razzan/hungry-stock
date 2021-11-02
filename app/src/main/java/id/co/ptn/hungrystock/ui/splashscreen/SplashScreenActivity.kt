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

@AndroidEntryPoint
class SplashScreenActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        init()
    }

    private fun init() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (sessionManager.token.isEmpty()) toLogin()
            else toMain()
            finish()
        }, 2000)
    }

    private fun toLogin() {
        startActivity(Intent(this@SplashScreenActivity, AuthActivity::class.java))
    }

    private fun toMain() {
        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
    }
}