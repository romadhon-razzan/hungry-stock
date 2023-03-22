package id.co.ptn.hungrystock.ui.general.forgot_password

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.core.network.RunningServiceType
import id.co.ptn.hungrystock.core.network.running_service
import id.co.ptn.hungrystock.databinding.ActivityForgotPasswordBinding
import id.co.ptn.hungrystock.helper.extension.hideKeyboard
import id.co.ptn.hungrystock.helper.extension.toast
import id.co.ptn.hungrystock.ui.general.view_model.ForgotPasswordViewModel
import id.co.ptn.hungrystock.utils.Status

@AndroidEntryPoint
class ForgotPasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private val viewModel: ForgotPasswordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        init()
    }

    private fun init() {
        setToolbar(binding.toolbar, resources.getString(R.string.button_lupa_password))
        setListener()
        setObserve()
    }

    private fun setListener() {
        binding.btSubmit.setOnClickListener { resetButtonPressed() }
    }

    private fun resetButtonPressed() {
        if (binding.etEmail.text.toString().isNotEmpty()) {
            viewModel.setEmail(binding.etEmail.text.toString())
            apiResetPassword()
        }
        else
            showSnackBar(binding.container,getString(R.string.message_email_empty))
    }

    private fun setObserve() {
        viewModel.reqOtpResponse().observe(this){
            when(it.status){
                Status.SUCCESS -> {
                    if (running_service == RunningServiceType.FORGOT_PASSWORD){
                        viewModel.apiResetPassword(it.data?.data ?: "")
                    }
                }
                Status.LOADING -> {
                    binding.btSubmit.startAnimation()
                }
                Status.ERROR -> {

                }
            }
        }
        viewModel.reqResetPasswordResponse().observe(this){
            when(it.status){
                Status.SUCCESS -> {
                    binding.btSubmit.revertAnimation()
                    router.toResetPasswordSuccess(viewModel.email.value)
                    binding.etEmail.setText("")
                }
                Status.LOADING -> {
                    binding.btSubmit.startAnimation()
                }
                Status.ERROR -> {
//                    binding.etEmail.setText("")
                    binding.btSubmit.revertAnimation()
                    it.message?.let { message -> toast(message)}
                }
            }
        }
    }

    /**
     * Api
     * */

    private fun apiResetPassword() {
        running_service = RunningServiceType.FORGOT_PASSWORD
        viewModel.apiGetOtp()
        hideKeyboard()
    }
}