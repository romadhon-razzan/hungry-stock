package id.co.ptn.hungrystock.ui.onboarding.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import id.co.ptn.hungrystock.ui.onboarding.fragments.*

class OnboardVPAdapter(fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 5
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
            3 -> {
                PageFourFragment()
            }
            else -> {
                PageFiveFragment()
            }
        }
    }
}