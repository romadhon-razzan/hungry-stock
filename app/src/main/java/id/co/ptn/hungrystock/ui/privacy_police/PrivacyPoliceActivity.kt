package id.co.ptn.hungrystock.ui.privacy_police

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseWebViewActivity
import id.co.ptn.hungrystock.databinding.ActivityPrivacyPoliceBinding
import id.co.ptn.hungrystock.ui.privacy_police.view_model.PrivacyPoliceViewModel

@AndroidEntryPoint
class PrivacyPoliceActivity : BaseWebViewActivity() {
    private lateinit var binding: ActivityPrivacyPoliceBinding
    private val viewModel: PrivacyPoliceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_police)
    }
}