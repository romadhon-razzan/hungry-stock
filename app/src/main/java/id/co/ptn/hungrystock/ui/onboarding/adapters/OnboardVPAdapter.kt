package id.co.ptn.hungrystock.ui.onboarding.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import id.co.ptn.hungrystock.ui.onboarding.fragments.PageFourFragment
import id.co.ptn.hungrystock.ui.onboarding.fragments.PageOneFragment
import id.co.ptn.hungrystock.ui.onboarding.fragments.PageThreeFragment
import id.co.ptn.hungrystock.ui.onboarding.fragments.PageTwoFragment

class OnboardVPAdapter(fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                PageOneFragment()
            }
            1 -> {
                PageTwoFragment()
            }
            2 -> {
                PageThreeFragment()
            }
            else -> {
                PageFourFragment()
            }
        }
    }
}