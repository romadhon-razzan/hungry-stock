package id.co.ptn.hungrystock.ui.main.home.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.databinding.ActivityEventDetailBinding

@AndroidEntryPoint
class EventDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityEventDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_detail)
        init()
    }

    private fun init() {
        setToolbar(binding.toolbar, "Detail Info")
        initIntent()
        initListener()
        setView()
    }

    private fun initIntent() {

    }

    private fun initListener() {

    }

    private fun setView() {

    }


}