package id.co.ptn.hungrystock.ui.general.auth

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.core.network.RunningServiceType
import id.co.ptn.hungrystock.core.network.running_service
import id.co.ptn.hungrystock.databinding.ActivityAuthBinding
import id.co.ptn.hungrystock.helper.Keyboard
import id.co.ptn.hungrystock.router.Router
import id.co.ptn.hungrystock.ui.general.view_model.AuthViewModel
import id.co.ptn.hungrystock.utils.HashUtils
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
        //        for user test
//                binding.etEmail.setText("082110735190")
//                binding.etPassword.setText("123456")
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
            running_service = RunningServiceType.CUSTOMER_LOGIN
            apiGetOtp()
        } else {
            showSnackBar(binding.container, getString(R.string.message_email_password_empty))
        }
    }

    private fun setObserve() {
        viewModel.reqOtpResponse().observe(this){
            when (it.status) {
                Status.SUCCESS -> {
                    if (running_service == RunningServiceType.CUSTOMER_LOGIN){
                        TOKEN = "${HashUtils.hash256CustomerLogin()}.${ENV.userKey()}.${it?.data?.data ?: ""}"
                        apiAuth()
                    }
                }
                Status.LOADING -> {binding.btLogin.startAnimation()}
                Status.ERROR -> {
                    binding.btLogin.revertAnimation()
                }
            }
        }
        viewModel.reqAuthResponse().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.btLogin.revertAnimation()
                    sessionManager?.setAuthData(Gson().toJson(it.data?.success_data?.get(0)?.data ?: ""))
                    router.toMain()
                    finishAffinity()
                }
                Status.LOADING -> {binding.btLogin.startAnimation()}
                Status.ERROR -> {
                    binding.btLogin.revertAnimation()
                    it.message?.let { message -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }
                }
            }
        }
    }

    /**
     * Api
     * */
    private fun apiGetOtp() {
        viewModel.apiGetOtp()
    }
    private fun apiAuth() {
        viewModel.apiAuth(this)
    }
}