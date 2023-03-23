package id.co.ptn.hungrystock.ui.profile

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseWebViewActivity
import id.co.ptn.hungrystock.bases.dialogs.ConfirmDialog
import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.helper.extension.toast
import id.co.ptn.hungrystock.ui.profile.view_model.ProfileViewModel
import id.co.ptn.hungrystock.utils.HashUtils
import id.co.ptn.hungrystock.utils.Status


@AndroidEntryPoint
open class ProfileV2Activity : BaseWebViewActivity() {
    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        changeStatusBar()
        init()
    }

    private fun init() {
        setToolbar(binding.toolbar, resources.getString(R.string.title_profile))
        binding.lifecycleOwner = this
        setView()
        setListener()
        setObserve()
        apiGetOtp()
    }

    private fun setView() {
        binding.buttonAction.text = "Keluar"
    }
    private fun setListener() {
        binding.buttonAction.setOnClickListener {
            val confirmDialog = ConfirmDialog(this)
            confirmDialog.setTitle("Keluar")
            confirmDialog.setMessage("Apakah Anda ingin keluar dari aplikasi?")
            confirmDialog.show(object : ConfirmDialog.Listener{
                override fun onPositiveClick() {
                    TOKEN = ""
                    sessionManager?.destroy()
                    router.toExit()
                    finishAffinity()
                }

                override fun onNegativeClick() {}

            }, "Keluar", "Batal")
        }
    }

    private fun setObserve() {
        webViewModel?.onPageStarted()?.observe(this){url ->
            if ((url ?: "").contains("mobile-profile-success")){
                toast("Berhasil mengubah profil")
            }
        }
        viewModel.reqOtpResponse().observe(this){
            when(it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    val parameter = StringBuilder()
                    parameter.append("mobile-profile?customer_id=${sessionManager?.authData?.code ?: ""}")
                    parameter.append("&otp=${it.data?.data ?: ""}")
                    parameter.append("&activationhashkey=${sessionManager?.user?.activation_key ?: ""}")
                    loadUrl("${ENV.webUrl()}$parameter")
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
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

}