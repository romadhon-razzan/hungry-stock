package id.co.ptn.hungrystock.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.databinding.ActivityMainBinding
import id.co.ptn.hungrystock.ui.main.adapters.MainVPAdapter
import id.co.ptn.hungrystock.ui.main.viewmodel.MainViewModel
import id.co.ptn.hungrystock.ui.onboarding.adapters.OnboardVPAdapter
import id.co.ptn.hungrystock.ui.privacy_police.view_model.PrivacyPoliceViewModel

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
        changeStatusBar()
        init()
    }

    private fun init() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
        initListener()
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




}