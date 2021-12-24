package id.co.ptn.hungrystock.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import id.co.ptn.hungrystock.ui.main.engine.EngineFragment
import id.co.ptn.hungrystock.ui.main.home.HomeFragment
import id.co.ptn.hungrystock.ui.main.hsro.HsroFragment
import id.co.ptn.hungrystock.ui.main.hsro.HsroViewModel
import id.co.ptn.hungrystock.ui.main.learning.LearningFragment
import id.co.ptn.hungrystock.ui.main.research.ResearchFragment
import id.co.ptn.hungrystock.ui.onboarding.fragments.PageFourFragment
import id.co.ptn.hungrystock.ui.onboarding.fragments.PageOneFragment
import id.co.ptn.hungrystock.ui.onboarding.fragments.PageThreeFragment
import id.co.ptn.hungrystock.ui.onboarding.fragments.PageTwoFragment

class MainVPAdapter(fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 5
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                LearningFragment()
            }
            2 -> {
                ResearchFragment()
            }
            3 -> {
                EngineFragment()
            }
            else -> {
                HsroFragment()
            }
        }
    }
}