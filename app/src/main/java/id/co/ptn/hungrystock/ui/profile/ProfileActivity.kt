package id.co.ptn.hungrystock.ui.profile

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.config.ASSET_URL
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.databinding.ActivityProfileBinding
import id.co.ptn.hungrystock.ui.profile.view_model.ProfileViewModel

@AndroidEntryPoint
class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.viewModel = viewModel
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        changeStatusBar()
        init()
    }

    private fun init() {
        setToolbar(binding.toolbar, resources.getString(R.string.title_profile))
        binding.lifecycleOwner = this
        setView()
        setListener()
    }

    private fun setView() {
        binding.etEmail.isEnabled = false
        binding.etWa.isEnabled = false
//        try {
//            Glide.with(this).load("$ASSET_URL${sessionManager.user.photo}").into(binding.imgPhoto)
//        }catch (e: Exception){
//            e.printStackTrace()
//        }

        viewModel.setUserId(sessionManager.user?.code ?: "")
        viewModel.setUsername(sessionManager.user?.name ?: "")
        viewModel.setValidDate(sessionManager.user?.membershipExpDate ?: 0)

        try {
//            viewModel.setJoinDate(sessionManager.user.email_verified_at)
        }catch (e: Exception){
            viewModel.setJoinDate("-")
        }
        viewModel.setEmail(sessionManager.user?.email ?: "")
        viewModel.setNoWa(sessionManager.user?.phone ?: "")

    }

    private fun setListener() {
        binding.btExit.setOnClickListener {
            TOKEN = ""
            sessionManager.destroy()
            router.toExit()
            finishAffinity()
        }
    }
}