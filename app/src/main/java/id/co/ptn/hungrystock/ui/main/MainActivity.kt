package id.co.ptn.hungrystock.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        changeStatusBar()
        init()
    }

    private fun init() {
        initListener()
    }

    private fun initListener() {
        binding.btnProfile.setOnClickListener { router.toProfile() }
    }
}