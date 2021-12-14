package id.co.ptn.hungrystock.ui.general.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.databinding.ActivityAuthBinding
import id.co.ptn.hungrystock.helper.Keyboard
import id.co.ptn.hungrystock.ui.general.view_model.AuthViewModel

class AuthActivity : BaseActivity() {
    private lateinit var binding: ActivityAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)

        init()
    }

    private fun init() {
        setView()
        listener()
        setObserve()
    }

    private fun setView() {

    }

    private fun listener() {

    }

    private fun loginPressed() {
        Keyboard(this).hide()


    }

    private fun setObserve() {

    }

    /**
     * Api
     * */

    private fun apiAuth() {

    }
}