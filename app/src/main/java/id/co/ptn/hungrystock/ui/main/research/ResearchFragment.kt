package id.co.ptn.hungrystock.ui.main.research

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.ResearchFragmentBinding
import id.co.ptn.hungrystock.ui.main.research.adapters.ResearchPageAdapter
import id.co.ptn.hungrystock.ui.main.research.dialogs.FilterResearchPageDialog
import id.co.ptn.hungrystock.ui.main.research.fragments.ResearchReportFragment
import id.co.ptn.hungrystock.ui.main.research.fragments.StockDataFragment
import id.co.ptn.hungrystock.ui.main.research.viewmodel.ResearchViewModel
import id.co.ptn.hungrystock.utils.currentYear
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class ResearchFragment : Fragment() {

    companion object {
        fun newInstance() = ResearchFragment()
    }

    private lateinit var binding: ResearchFragmentBinding
    private var viewModel: ResearchViewModel? = null
    private lateinit var researchPageAdapter: ResearchPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.research_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ResearchViewModel::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = this
        init()
    }

    private fun init() {
        setObserve()
        initListener()
        initPage()
        initSearch()
    }

    private fun initListener() {
        binding.btFilter.setOnClickListener { openFilterDialog() }
    }

    private fun initPage() {
        researchPageAdapter = ResearchPageAdapter(requireActivity())
        researchPageAdapter.addFragment(ResearchReportFragment(),requireActivity().getString(R.string.title_tab_research_report,"0"))
        researchPageAdapter.addFragment(StockDataFragment(),requireActivity().getString(R.string.title_tab_stock_data,"0"))

        binding.viewPager.adapter = researchPageAdapter
        binding.viewPager.currentItem = 0
        binding.viewPager.offscreenPageLimit = 2
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = researchPageAdapter.getTabTitle(position)
        }.attach()

        //initial filter
        lifecycleScope.launch {
            delay(1000)
            viewModel?.setYear(currentYear())
            viewModel?.onFilter()?.value = true
            viewModel?.setFilterValues()
        }
    }

    private fun initSearch() {
        binding.etSearch.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel?.setKeyword(s.toString())
                viewModel?.onSearch()?.value = true
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateResearchTitle(total: String) {
        try {
            val title = requireActivity().getString(R.string.title_tab_research_report,total)
            researchPageAdapter.setTabTitle(0, title)
            researchPageAdapter.notifyItemChanged(0)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun updateStockTitle(total: String) {
        try {
            val title = requireActivity().getString(R.string.title_tab_stock_data,total)
            researchPageAdapter.setTabTitle(1, title)
            researchPageAdapter.notifyItemChanged(1)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun openFilterDialog() {
        val dialog = FilterResearchPageDialog(object : FilterResearchPageDialog.Listener{
            override fun onFilter(year: String, month: String, monthId: String, abjad: String) {
                viewModel?.setYear(year)
                viewModel?.setMonth(monthId)
                viewModel?.setInitial(abjad)
                viewModel?.onFilter()?.value = true

                viewModel?.setFilterValues()
            }
        })
        dialog.setYearSelected(currentYear())
        dialog.show(childFragmentManager,"filter_dialog")
    }

    private fun setObserve() {
        viewModel?.researchTabTitle()?.observe(viewLifecycleOwner){
            updateResearchTitle(it)
        }
        viewModel?.stockTabTitle()?.observe(viewLifecycleOwner){
            updateStockTitle(it)
        }
    }
}