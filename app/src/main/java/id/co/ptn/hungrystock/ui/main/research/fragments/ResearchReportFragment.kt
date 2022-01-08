package id.co.ptn.hungrystock.ui.main.research.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.FragmentResearchReportBinding
import id.co.ptn.hungrystock.databinding.ResearchFragmentBinding
import id.co.ptn.hungrystock.models.main.research.ResearchFilter
import id.co.ptn.hungrystock.models.main.research.ResearchPage
import id.co.ptn.hungrystock.models.main.research.ResearchReport
import id.co.ptn.hungrystock.models.main.research.ResearchSorting
import id.co.ptn.hungrystock.ui.main.research.adapters.ResearchReportPageAdapter
import id.co.ptn.hungrystock.ui.main.research.viewmodel.ResearchReportViewModel
import id.co.ptn.hungrystock.ui.main.research.viewmodel.ResearchViewModel

@AndroidEntryPoint
class ResearchReportFragment : Fragment() {

    companion object {
        fun newInstance() = ResearchReportFragment()
    }

    private lateinit var binding: FragmentResearchReportBinding
    private val viewModel: ResearchReportViewModel by viewModels()
    private lateinit var researchReportPageAdapter: ResearchReportPageAdapter
    private var items: MutableList<ResearchPage> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_research_report, container, false)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initData()
        initList()
    }

    private fun initList() {
        researchReportPageAdapter = ResearchReportPageAdapter(items)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = researchReportPageAdapter
        }
    }

    private fun initData() {
        items.clear()
        items.add(ResearchPage(ResearchPage.TYPE_SORTING, listOf(), listOf(), listOf(), ResearchSorting("n","Terbaru")))

        val filters: MutableList<ResearchFilter> = mutableListOf()
        filters.add(ResearchFilter("t","2022"))

        items.add(ResearchPage(ResearchPage.TYPE_FILTER, listOf(), listOf(), listOf(), ResearchSorting("n","Terbaru")))
        items.add(ResearchPage(ResearchPage.TYPE_FILTER, listOf(), listOf(), filters, ResearchSorting("n","Terbaru")))

        val researchReport: MutableList<ResearchReport> = mutableListOf()
        researchReport.add(ResearchReport("1","1"))
        researchReport.add(ResearchReport("1","1"))
        researchReport.add(ResearchReport("1","1"))
        researchReport.add(ResearchReport("1","1"))
        researchReport.add(ResearchReport("1","1"))
        items.add(ResearchPage(ResearchPage.TYPE_LIST, researchReport, listOf(), filters, ResearchSorting("n","Terbaru")))
    }
}