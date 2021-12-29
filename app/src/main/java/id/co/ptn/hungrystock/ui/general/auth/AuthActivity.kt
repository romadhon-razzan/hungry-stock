package id.co.ptn.hungrystock.ui.general.auth

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.databinding.ActivityAuthBinding
import id.co.ptn.hungrystock.helper.Keyboard
import id.co.ptn.hungrystock.router.Router
import id.co.ptn.hungrystock.ui.general.view_model.AuthViewModel

@AndroidEntryPoint
class AuthActivity : BaseActivity() {
    private lateinit var binding: ActivityAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)
        binding.viewModel = viewModel
        changeStatusBar()
        init()
    }

    private fun init() {
        setView()
        initListener()
        setObserve()
    }

    private fun setView() {

    }

    private fun initListener() {
        binding.btRegister.setOnClickListener { router.toRegistration() }
        binding.btForgotPassword.setOnClickListener {
            Toast.makeText(this,"KLIK", Toast.LENGTH_SHORT).show()
        }
        binding.btLogin.setOnClickListener { loginPressed() }
    }

    private fun loginPressed() {
        Keyboard(this).hide()
        router.toMain()

    }

    private fun setObserve() {

    }

    /**
     * Api
     * */

    private fun apiAuth() {

    }
}