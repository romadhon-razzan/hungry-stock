package id.co.ptn.hungrystock.ui.privacy_police

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseWebViewActivity
import id.co.ptn.hungrystock.databinding.ActivityPrivacyPoliceBinding

@AndroidEntryPoint
class PrivacyPoliceActivity : BaseWebViewActivity() {
    private lateinit var binding: ActivityPrivacyPoliceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_police)
    }
}