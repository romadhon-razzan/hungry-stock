package id.co.ptn.hungrystock.router

import android.content.Context
import android.content.Intent
import id.co.ptn.hungrystock.ui.general.auth.AuthActivity
import id.co.ptn.hungrystock.ui.main.MainActivity
import id.co.ptn.hungrystock.ui.onboarding.OnboardActivity
import id.co.ptn.hungrystock.ui.reference.ReferenceLayoutActivity

class Router constructor(private val context: Context) {
    fun toOnboard() {
        context.startActivity(Intent(context, OnboardActivity::class.java))
    }

    fun toAuth() {
        context.startActivity(Intent(context, AuthActivity::class.java))
    }

    fun toRegistration() {
        context.startActivity(Intent(context, AuthActivity::class.java))
    }

    private fun toMain() {
        context.startActivity(Intent(context, MainActivity::class.java))
    }

    private fun toReference() {
        context.startActivity(Intent(context, ReferenceLayoutActivity::class.java))
    }
}