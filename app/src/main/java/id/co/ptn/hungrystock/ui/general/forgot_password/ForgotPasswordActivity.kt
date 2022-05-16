package id.co.ptn.hungrystock.ui.general.forgot_password

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.databinding.ActivityForgotPasswordBinding
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
        viewModel.reqResetPasswordResponse().observe(this){
            when(it.status){
                Status.SUCCESS -> {
                    binding.btSubmit.revertAnimation()
                    try {
                        it.data?.let { d ->
                            if (d.status == "success"){
                                router.toResetPasswordSuccess(viewModel.email.value)
                            } else {
                                showSnackBar(binding.container, d.message)
                            }
                        }
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                    binding.etEmail.setText("")
                }
                Status.LOADING -> {
                    binding.btSubmit.startAnimation()
                }
                Status.ERROR -> {
                    binding.etEmail.setText("")
                    binding.btSubmit.revertAnimation()
                    it.data?.message?.let { message ->
                        showSnackBar(binding.container, message)
                    }
                }
            }
        }
    }

    /**
     * Api
     * */

    private fun apiResetPassword() {
        viewModel.apiResetPassword()
    }
}