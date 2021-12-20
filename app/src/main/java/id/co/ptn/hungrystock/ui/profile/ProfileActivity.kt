package id.co.ptn.hungrystock.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity

class ProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        changeStatusBar()
    }
}