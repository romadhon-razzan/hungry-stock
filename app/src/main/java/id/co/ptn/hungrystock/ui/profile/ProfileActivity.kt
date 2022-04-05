package id.co.ptn.hungrystock.ui.profile

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
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
        try {
            viewModel.setUserId(sessionManager.user.id.toString())
        }catch (e: Exception){
            viewModel.setUserId("")
        }

        try {
            viewModel.setUsername(sessionManager.user.name)
        }catch (e: Exception){
            viewModel.setUsername("")
        }

        try {
            viewModel.setValidDate(sessionManager.user.membership_end_at)
        }catch (e: Exception){
            viewModel.setValidDate("-")
        }

        try {
            viewModel.setJoinDate(sessionManager.user.email_verified_at)
        }catch (e: Exception){
            viewModel.setJoinDate("-")
        }

        try {
            viewModel.setEmail(sessionManager.user.email)
        }catch (e: Exception){
            viewModel.setEmail("")
        }

        try {
            viewModel.setNoWa(sessionManager.user.phone)
        }catch (e: Exception){
            viewModel.setNoWa("")
        }

    }

    private fun setListener() {
        binding.btExit.setOnClickListener {
            sessionManager.destroy()
            router.toExit()
            finish()
        }
    }
}