package id.co.ptn.hungrystock.ui.general.auth

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.databinding.ActivityAuthBinding
import id.co.ptn.hungrystock.helper.Keyboard
import id.co.ptn.hungrystock.router.Router
import id.co.ptn.hungrystock.ui.general.view_model.AuthViewModel
import id.co.ptn.hungrystock.utils.Status

@AndroidEntryPoint
class AuthActivity : BaseActivity() {
    private lateinit var binding: ActivityAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)
        binding.viewModel = viewModel
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        changeStatusBar()
        init()
    }

    private fun init() {
        setView()
        initListener()
        setObserve()
    }

    private fun setView() {

    }

    private fun initListener() {
        binding.btRegister.setOnClickListener { router.toRegistration() }
        binding.btForgotPassword.setOnClickListener { router.toForgotPassword() }
        binding.btLogin.setOnClickListener { loginPressed() }
    }

    private fun loginPressed() {
        Keyboard(this).hide()
        if (binding.etEmail.text.toString().isNotEmpty() && binding.etPassword.text.toString().isNotEmpty()){
            viewModel.setUsername(binding.etEmail.text.toString())
            viewModel.setPassword(binding.etPassword.text.toString())
            apiAuth()
        } else {
            showSnackBar(binding.container, getString(R.string.message_email_password_empty))
        }
    }

    private fun setObserve() {
        viewModel.reqAuthResponse().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.btLogin.revertAnimation()
                    it.data?.let { d ->
                       d.status.let { s ->
                           if (s == "success") {
                               d.data.token.let { t -> sessionManager.setToken(t) }
                               d.data.user.let { u -> sessionManager.setUser(u) }
                               router.toMain()
                               finishAffinity()
                           } else {
                               d.data.status.let { message ->
                                   showSnackBar(binding.container, message)
                               }
                           }
                       }

                    }
                }
                Status.LOADING -> {binding.btLogin.startAnimation()}
                Status.ERROR -> {
                    binding.btLogin.revertAnimation()
                    it.data?.data?.status?.let { s ->
                        showSnackBar(binding.container, s)
                    }
                }
            }
        }
    }

    /**
     * Api
     * */

    private fun apiAuth() {
        viewModel.apiAuth()
    }
}