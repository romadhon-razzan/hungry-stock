package id.co.ptn.hungrystock.ui.main.research.fragments

import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
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
import id.co.ptn.hungrystock.utils.Status
import id.co.ptn.hungrystock.utils.currentMonth
import id.co.ptn.hungrystock.utils.currentYear

@AndroidEntryPoint
class ResearchReportFragment : Fragment() {

    companion object {
        fun newInstance() = ResearchReportFragment()
    }

    private var binding: FragmentResearchReportBinding? = null
    private var viewModel: ResearchReportViewModel? = null
    private var researchReportPageAdapter: ResearchReportPageAdapter? = null
    private var items: MutableList<ResearchPage> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_research_report, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ResearchReportViewModel::class.java)
        binding?.vm = viewModel
        init()
    }

    private fun init() {
        setObserve()
        initListener()
        viewModel?.setYear(currentYear())
        viewModel?.setMonth(currentMonth())
        apiGetResearch()
    }

    private fun initListener() {
        binding?.swipeRefresh?.setOnRefreshListener {
            apiGetResearch()
        }
    }

    private fun initList() {
        researchReportPageAdapter = ResearchReportPageAdapter(items, object: ResearchReportPageAdapter.ResearchReportListener{
            override fun onFilterClick() {

            }

        })
        binding?.recyclerView?.apply {
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

        /*val researchReport: MutableList<ResearchReport> = mutableListOf()
        researchReport.add(ResearchReport("1","1"))
        researchReport.add(ResearchReport("1","1"))
        researchReport.add(ResearchReport("1","1"))
        researchReport.add(ResearchReport("1","1"))
        researchReport.add(ResearchReport("1","1"))
        items.add(ResearchPage(ResearchPage.TYPE_LIST, researchReport, listOf(), filters, ResearchSorting("n","Terbaru")))*/
    }

    private fun setObserve() {
        viewModel?.reqResearchResponse()?.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    binding?.swipeRefresh?.isRefreshing = false
                    binding?.progressBar?.visibility = View.GONE
                    val researchReport: MutableList<ResearchReport> = mutableListOf()
                    it.data?.
                    getAsJsonObject("data")?.
                    getAsJsonObject("researchs")?.
                            getAsJsonArray("April 2022")?.forEachIndexed { index, jsonElement ->
                        var id = ""
                        var title = ""
                        var photoUrl = ""
                        var fileUrl = ""
                        var extension = ""

                        if (jsonElement.asJsonObject.has("id")){
                            id = jsonElement.asJsonObject.get("id").toString()
                        }

                        if (jsonElement.asJsonObject.has("title")){
                            title = jsonElement.asJsonObject.get("title").asString
                        }

                        if (jsonElement.asJsonObject.has("photo_url")){
                            photoUrl = jsonElement.asJsonObject.get("photo_url").asString
                        }

                        if (jsonElement.asJsonObject.has("file_url")){
                            fileUrl = jsonElement.asJsonObject.get("file_url").asString
                        }

                        if (jsonElement.asJsonObject.has("file_extension")){
                            extension = jsonElement.asJsonObject.get("file_extension").asString
                        }
                        researchReport.add(ResearchReport(id, title, photoUrl, fileUrl, extension))
                    }
                    items.add(ResearchPage(ResearchPage.TYPE_LIST, researchReport, listOf(), listOf(), ResearchSorting("n","Terbaru")))
                    initList()
                }
                Status.LOADING -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding?.swipeRefresh?.isRefreshing = false
                    binding?.progressBar?.visibility = View.GONE
                }
            }
        }
    }
    /*
    * Api
    * */

    private fun apiGetResearch() {
        viewModel?.apiResearch(
            viewModel?.getType().toString(),
            viewModel?.getKeyword().toString(),
            viewModel?.getCategory().toString(),
            viewModel?.getYear().toString(),
            viewModel?.getMonth().toString(),
            viewModel?.getInitial().toString())
    }
}