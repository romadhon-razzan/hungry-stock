package id.co.ptn.hungrystock.router

import android.content.Context
import android.content.Intent
import id.co.ptn.hungrystock.ui.general.auth.AuthActivity
import id.co.ptn.hungrystock.ui.general.forgot_password.ForgotPasswordActivity
import id.co.ptn.hungrystock.ui.general.forgot_password.ResetPasswordSuccessActivity
import id.co.ptn.hungrystock.ui.general.registration.RegistrationActivity
import id.co.ptn.hungrystock.ui.general.registration.RegistrationSuccessActivity
import id.co.ptn.hungrystock.ui.main.MainActivity
import id.co.ptn.hungrystock.ui.main.hsro.HsroDetailActivity
import id.co.ptn.hungrystock.ui.main.learning.LearningDetailActivity
import id.co.ptn.hungrystock.ui.onboarding.OnboardActivity
import id.co.ptn.hungrystock.ui.privacy_police.PrivacyPoliceActivity
import id.co.ptn.hungrystock.ui.profile.ProfileActivity
import id.co.ptn.hungrystock.ui.reference.ReferenceLayoutActivity
import id.co.ptn.hungrystock.ui.video_player.VideoPlayerActivity

class Router constructor(private val context: Context) {
    fun toOnboard() {
        context.startActivity(Intent(context, OnboardActivity::class.java))
    }

    fun toPrivacyPolice(): Intent {
        return Intent(context, PrivacyPoliceActivity::class.java)
    }

    fun toAuth() {
        context.startActivity(Intent(context, AuthActivity::class.java))
    }

    fun toForgotPassword() {
        context.startActivity(Intent(context, ForgotPasswordActivity::class.java))
    }

    fun toResetPasswordSuccess(email: String) {
        val intent = Intent(context, ResetPasswordSuccessActivity::class.java)
        intent.putExtra("email", email)
        context.startActivity(intent)
    }

    fun toExit() {
        val intent = Intent(context, AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startActivity(intent)
    }

    fun toRegistration() {
        context.startActivity(Intent(context, RegistrationActivity::class.java))
    }

    fun toRegistrationSuccess() {
        context.startActivity(Intent(context, RegistrationSuccessActivity::class.java))
    }

    fun toMain() {
        context.startActivity(Intent(context, MainActivity::class.java))
    }

    fun toProfile() {
        context.startActivity(Intent(context, ProfileActivity::class.java))
    }

    fun toReference() {
        context.startActivity(Intent(context, ReferenceLayoutActivity::class.java))
    }

    fun toHsroDetail() {
        context.startActivity(Intent(context, HsroDetailActivity::class.java))
    }

    fun toLearningDetail():Intent {
        return Intent(context, LearningDetailActivity::class.java)
    }

    fun toPlayVideo():Intent {
        return Intent(context, VideoPlayerActivity::class.java)
    }
}