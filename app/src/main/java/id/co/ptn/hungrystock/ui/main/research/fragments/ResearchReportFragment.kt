package id.co.ptn.hungrystock.ui.main.research.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.EmptyStateFragment
import id.co.ptn.hungrystock.core.SessionManager
import id.co.ptn.hungrystock.core.network.RunningServiceType
import id.co.ptn.hungrystock.core.network.running_service
import id.co.ptn.hungrystock.databinding.FragmentResearchReportBinding
import id.co.ptn.hungrystock.models.main.research.*
import id.co.ptn.hungrystock.ui.main.research.adapters.ResearchReportPageAdapter
import id.co.ptn.hungrystock.ui.main.research.viewmodel.ResearchReportViewModel
import id.co.ptn.hungrystock.ui.main.research.viewmodel.ResearchViewModel
import id.co.ptn.hungrystock.ui.main.viewmodel.MainViewModel
import id.co.ptn.hungrystock.utils.*

@AndroidEntryPoint
class ResearchReportFragment : Fragment() {

    companion object {
        fun newInstance() = ResearchReportFragment()
    }

    private var binding: FragmentResearchReportBinding? = null
    private var mainViewModel: MainViewModel? = null
    private var viewModel: ResearchReportViewModel? = null
    private var researchViewModel: ResearchViewModel? = null
    private var researchReportPageAdapter: ResearchReportPageAdapter? = null
    private var items: MutableList<ResearchPage> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        researchViewModel = ViewModelProvider(requireActivity())[ResearchViewModel::class.java]
        viewModel = ViewModelProvider(this)[ResearchReportViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_research_report, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.vm = viewModel
        init()
    }

    private fun init() {
        setObserve()
        initListener()
        viewModel?.setYear(currentYear())
        viewModel?.getFilters()?.clear()
        viewModel?.getFilters()?.add(ResearchFilter("","${monthLabel(viewModel?.getMonth()!!)} ${currentYear()}"))
    }

    private fun initListener() {
        binding?.swipeRefresh?.setOnRefreshListener {
            apiGetResearch()
        }
    }

    private fun initList() {
        researchReportPageAdapter = ResearchReportPageAdapter(
            childFragmentManager,
            items, object: ResearchReportPageAdapter.ResearchReportListener{
            override fun onFilterClick() {

            }

            override fun onSorting(value: String) {
                items.forEachIndexed { index, researchPage ->
                    when(researchPage.type){
                        ResearchPage.TYPE_SORTING -> {
                            researchPage.sorting = ResearchSorting(value,value)
                            researchReportPageAdapter?.notifyItemChanged(index)
                            viewModel?.setLabelSorting(value)
                            viewModel?.setType(value)
                            Handler(Looper.getMainLooper()).postDelayed({
                                apiGetResearch()
                            },500)

                        }
                        else -> {}
                    }
                }
            }

        })
        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = researchReportPageAdapter
        }
    }

    private fun emptyState() {
        researchViewModel?.researchTabTitle()?.value = "0"
        binding?.frameContainer?.visibility = View.VISIBLE
        childFragmentManager.commit {
            setReorderingAllowed(true)
            val bundle = Bundle()
            bundle.putString("title","Research tidak ditemukan")
            bundle.putString("message","")
            add<EmptyStateFragment>(R.id.frame_container, "", bundle)
        }

    }

    private fun setObserve() {
        viewModel?.reqOtpResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                    when(running_service){
                        RunningServiceType.RESEARCH -> {
                            viewModel?.apiResearch(SessionManager.getInstance(requireContext()), it.data?.data ?: "")
                        }
                        else -> {}
                    }
                }
                Status.LOADING -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding?.progressBar?.visibility = View.GONE
                }
            }
        }

        researchViewModel?.onFilter()?.observe(viewLifecycleOwner){
            if (it) {
                researchViewModel?.getYear()?.let { y -> viewModel?.setYear(y) }
                researchViewModel?.getMonth()?.let { m -> viewModel?.setMonth(m) }
                researchViewModel?.getInitial()?.let { i -> viewModel?.setInitial(i) }
                apiGetResearch()
            }
        }

        researchViewModel?.onSearch()?.observe(viewLifecycleOwner){
            if (it){
                researchViewModel?.getKeyword()?.let { k -> viewModel?.setKeyword(k) }
                apiGetResearch()
            }
        }

        viewModel?.reqResearchResponse()?.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    binding?.swipeRefresh?.isRefreshing = false
                    binding?.progressBar?.visibility = View.GONE
                    var total = 0

                    /*try {
                        binding?.frameContainer?.visibility = View.GONE
                        items.clear()
                        items.add(ResearchPage(ResearchPage.TYPE_SORTING, listOf(), listOf(), listOf(), ResearchSorting("n",viewModel?.getLabelSorting()!!)))
//                        items.add(ResearchPage(ResearchPage.TYPE_FILTER, listOf(), listOf(), viewModel?.getFilters()!!, ResearchSorting("n","Terbaru")))
                        val researchReport: MutableList<ResearchReport> = mutableListOf()
                        it.data?.getAsJsonObject("data")?.let { data ->
                            if (data.has("researchsCount"))
                                total = data.get("researchsCount").asInt

                            monthListDesc().forEach { month ->
                                val researchReportData: MutableList<ResearchReportData> = mutableListOf()
                                data.getAsJsonObject("researchs")?.
                                getAsJsonArray("$month ${viewModel?.getYear()}")?.forEachIndexed { index, jsonElement ->
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
                                    researchReportData.add(ResearchReportData(id, title, photoUrl, fileUrl, extension))
                                }
                                if (researchReportData.size > 0) {
                                    researchReport.add(ResearchReport("$month ${viewModel?.getYear()}", researchReportData))
                                }
                            }

                            items.add(ResearchPage(ResearchPage.TYPE_LIST, researchReport, listOf(), listOf(), ResearchSorting("n","Terbaru")))
                            initList()
                            researchViewModel?.researchTabTitle()?.value = total.toString()
                        }
                    }catch (e: Exception){
                        e.printStackTrace()
                        // empty state
                        emptyState()
                    }*/

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

        mainViewModel?.researchPressed?.observe(requireActivity()){

            if (it){
                if (viewModel?.pageFirstRequested == false) {
                    apiGetResearch()
                    viewModel?.pageFirstRequested = true
                }
            }
        }
    }

    /*
    * Api
    * */

    private fun apiGetOtp() {
        viewModel?.apiGetOtp()
    }

    private fun apiGetResearch() {
        running_service = RunningServiceType.RESEARCH
        apiGetOtp()
    }
}