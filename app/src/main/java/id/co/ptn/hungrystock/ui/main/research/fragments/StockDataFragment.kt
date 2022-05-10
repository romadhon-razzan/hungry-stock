package id.co.ptn.hungrystock.ui.main.research.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.EmptyStateFragment
import id.co.ptn.hungrystock.databinding.FragmentStockDataBinding
import id.co.ptn.hungrystock.databinding.ResearchFragmentBinding
import id.co.ptn.hungrystock.models.main.research.*
import id.co.ptn.hungrystock.ui.main.research.adapters.ResearchReportPageAdapter
import id.co.ptn.hungrystock.ui.main.research.adapters.StockDataPageAdapter
import id.co.ptn.hungrystock.ui.main.research.viewmodel.ResearchViewModel
import id.co.ptn.hungrystock.ui.main.research.viewmodel.StockDataViewModel

@AndroidEntryPoint
class StockDataFragment : Fragment() {

    companion object {
        fun newInstance() = StockDataFragment()
    }

    private lateinit var binding: FragmentStockDataBinding
    private val viewModel: StockDataViewModel by viewModels()
    private lateinit var stockDaPageAdapter: StockDataPageAdapter
    private var items: MutableList<ResearchPage> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_stock_data, container, false)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
//        initData()
//        initList()
        emptyState()
    }

    private fun initList() {
        stockDaPageAdapter = StockDataPageAdapter(items)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = stockDaPageAdapter
        }
    }

    private fun initData() {
        items.clear()
        items.add(ResearchPage(ResearchPage.TYPE_SORTING, listOf(), listOf(), listOf(), ResearchSorting("n","Terbaru")))

        val filters: MutableList<ResearchFilter> = mutableListOf()
        filters.add(ResearchFilter("t","2022"))

        items.add(ResearchPage(ResearchPage.TYPE_FILTER, listOf(), listOf(), listOf(), ResearchSorting("n","Terbaru")))
        items.add(ResearchPage(ResearchPage.TYPE_FILTER, listOf(), listOf(), filters, ResearchSorting("n","Terbaru")))

        val researchReport: MutableList<StockData> = mutableListOf()
        researchReport.add(StockData("1","1"))
        researchReport.add(StockData("1","1"))
        researchReport.add(StockData("1","1"))
        researchReport.add(StockData("1","1"))
        researchReport.add(StockData("1","1"))
        items.add(ResearchPage(ResearchPage.TYPE_LIST, listOf(), researchReport, filters, ResearchSorting("n","Terbaru")))
    }

    private fun emptyState() {
        binding.frameContainer.visibility = View.VISIBLE
        childFragmentManager.commit {
            setReorderingAllowed(true)
            val bundle = Bundle()
            bundle.putString("title","Segera hadir")
            bundle.putString("message","")
            add<EmptyStateFragment>(R.id.frame_container, "", bundle)
        }

    }
}