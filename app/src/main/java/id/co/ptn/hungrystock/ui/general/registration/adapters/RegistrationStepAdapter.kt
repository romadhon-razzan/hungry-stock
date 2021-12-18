package id.co.ptn.hungrystock.ui.general.registration.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import id.co.ptn.hungrystock.ui.general.registration.fragments.RegisterStepOneFragment
import id.co.ptn.hungrystock.ui.general.registration.fragments.RegisterStepTwoFragment

class RegistrationStepAdapter(fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                RegisterStepOneFragment()
            }
            else -> {
                RegisterStepTwoFragment()
            }
        }
    }
}