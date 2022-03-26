package id.co.ptn.hungrystock.ui.profile

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.databinding.ActivityProfileBinding

@AndroidEntryPoint
class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        changeStatusBar()
        init()
    }

    private fun init() {
        setView()
        setListener()
    }

    private fun setView() {

    }

    private fun setListener() {
        binding.btExit.setOnClickListener {
            sessionManager.destroy()
            router.toExit()
        }
    }
}