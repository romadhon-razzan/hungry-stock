package id.co.ptn.hungrystock.ui.main.research

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.ResearchFragmentBinding
import id.co.ptn.hungrystock.ui.main.research.adapters.ResearchPageAdapter
import id.co.ptn.hungrystock.ui.main.research.dialogs.FilterResearchPageDialog
import id.co.ptn.hungrystock.ui.main.research.fragments.ResearchReportFragment
import id.co.ptn.hungrystock.ui.main.research.fragments.StockDataFragment
import id.co.ptn.hungrystock.ui.main.research.viewmodel.ResearchViewModel

@AndroidEntryPoint
class ResearchFragment : Fragment() {

    companion object {
        fun newInstance() = ResearchFragment()
    }

    private lateinit var binding: ResearchFragmentBinding
    private val viewModel: ResearchViewModel by viewModels()
    private lateinit var researchPageAdapter: ResearchPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.research_fragment, container, false)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initListener()
        initPage()
    }

    private fun initListener() {
        binding.btFilter.setOnClickListener {  }
    }

    private fun initPage() {
        researchPageAdapter = ResearchPageAdapter(requireActivity())
        researchPageAdapter.addFragment(ResearchReportFragment(),requireActivity().getString(R.string.title_tab_research_report,"0"))
        researchPageAdapter.addFragment(StockDataFragment(),requireActivity().getString(R.string.title_tab_stock_data,"2"))

        binding.viewPager.adapter = researchPageAdapter
        binding.viewPager.currentItem = 0
        binding.viewPager.offscreenPageLimit = 2
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = researchPageAdapter.getTabTitle(position)
        }.attach()
    }

    private fun updateTitle() {

    }

    private fun openFilterDialog() {
        val dialog = FilterResearchPageDialog()
        dialog.show(childFragmentManager,"filter_dialog")
    }
}