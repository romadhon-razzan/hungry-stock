package id.co.ptn.hungrystock.ui.main.research.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
class ResearchPageAdapter(activity: FragmentActivity?) : FragmentStateAdapter(activity!!) {
    private val mFragmentList: MutableList<Fragment> = ArrayList()
    private val mFragmentTitleList: MutableList<String> = ArrayList()

    public fun setTabTitle(position: Int, title: String){
        mFragmentTitleList[position] = title
    }

    public fun getTabTitle(position : Int): String{
        return mFragmentTitleList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }
}