package id.co.ptn.hungrystock.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.config.ASSET_URL
import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.core.network.RunningServiceType
import id.co.ptn.hungrystock.core.network.running_service
import id.co.ptn.hungrystock.databinding.ActivityMainBinding
import id.co.ptn.hungrystock.ui.main.adapters.MainVPAdapter
import id.co.ptn.hungrystock.ui.main.viewmodel.MainViewModel
import id.co.ptn.hungrystock.ui.onboarding.adapters.OnboardVPAdapter
import id.co.ptn.hungrystock.ui.privacy_police.view_model.PrivacyPoliceViewModel
import id.co.ptn.hungrystock.utils.HashUtils
import id.co.ptn.hungrystock.utils.MediaUtils
import id.co.ptn.hungrystock.utils.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    companion object {
        const val HOME_PAGE = 0
        const val LEARNING_PAGE = 1
        const val RESEARCH_PAGE = 2
        const val ENGINE_PAGE = 3
        const val HSRO_PAGE = 4
    }
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        changeStatusBar()
        init()
    }

    private fun init() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
        initListener()
        setObserve()
        apiGetProfile()
    }

    private fun setView() {
        MediaUtils(this).setImageFromUrl(binding.imgProfile, sessionManager.user?.photo_file ?: "")
        initViewPager()
        navHomePressed()
    }

    private fun initListener() {
        binding.btnProfile.setOnClickListener { router.toProfile() }

        binding.btnNavHome.setOnClickListener { navHomePressed() }
        binding.btnNavLearning.setOnClickListener { navLearningPressed() }
        binding.btnNavResearch.setOnClickListener { navResearchPressed() }
        binding.btnNavEngine.setOnClickListener { navEnginePressed() }
        binding.btnNavHsro.setOnClickListener { navHsroPressed() }
    }

    private fun initViewPager() {
        binding.swipeLockableViewPager.adapter = MainVPAdapter(supportFragmentManager)
        binding.swipeLockableViewPager.offscreenPageLimit = 5
    }

    private fun setViewPagerPage(p: Int) {
        binding.swipeLockableViewPager.currentItem = p
    }

    /**
     * Bottom Navigation Menu
     * */

    private fun navHomePressed() {
        setViewPagerPage(HOME_PAGE)
        viewModel.homePressed(getString(R.string.title_event_and_agenda))
    }

    private fun navLearningPressed() {
        setViewPagerPage(LEARNING_PAGE)
        viewModel.learningPressed(getString(R.string.title_learning))
    }

    private fun navResearchPressed() {
        setViewPagerPage(RESEARCH_PAGE)
        viewModel.researchPressed(getString(R.string.title_research_n_data))
    }

    private fun navEnginePressed() {
        setViewPagerPage(ENGINE_PAGE)
        viewModel.enginePressed(getString(R.string.title_hungry_stock_engine))
    }

    private fun navHsroPressed() {
        setViewPagerPage(HSRO_PAGE)
        viewModel.hsroPressed(getString(R.string.title_hsro))
    }

    private fun setObserve() {
        viewModel.reqOtpResponse().observe(this){
            when(it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (running_service == RunningServiceType.PROFILE){
                        lifecycleScope.launch {
                            delay(500)
                            viewModel.apiGetProfile(sessionManager, it.data?.data ?: "")
                        }
                    }
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        viewModel.reqProfileResponse().observe(this){
            when(it.status) {
                Status.SUCCESS -> {
                    it?.data?.data?.forEach { data ->
                        sessionManager.setUser(data)
                    }
                    setView()
                    binding.constraint.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    /**
     * Api
     * */
    private fun apiGetProfile() {
        running_service = RunningServiceType.PROFILE
        viewModel.apiGetOtp()
    }


}