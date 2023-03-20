package id.co.ptn.hungrystock.ui.main.research.fragments

import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.EmptyStateFragment
import id.co.ptn.hungrystock.bases.view_model.PaginationViewModel
import id.co.ptn.hungrystock.core.network.RunningServiceType
import id.co.ptn.hungrystock.core.network.running_service
import id.co.ptn.hungrystock.databinding.FragmentResearchReportBinding
import id.co.ptn.hungrystock.models.Links
import id.co.ptn.hungrystock.models.main.research.*
import id.co.ptn.hungrystock.ui.main.learning.adapters.LearningPaginationAdapter
import id.co.ptn.hungrystock.ui.main.research.adapters.ResearchReportListAdapter
import id.co.ptn.hungrystock.ui.main.research.viewmodel.ResearchReportViewModel
import id.co.ptn.hungrystock.ui.main.research.viewmodel.ResearchViewModel
import id.co.ptn.hungrystock.ui.main.viewmodel.MainViewModel
import id.co.ptn.hungrystock.ui.main.viewmodel.ReferenceViewModel
import id.co.ptn.hungrystock.utils.*

@AndroidEntryPoint
class ResearchReportFragment : Fragment() {

    companion object {
        fun newInstance() = ResearchReportFragment()
    }

    private var binding: FragmentResearchReportBinding? = null
    private var mainViewModel: MainViewModel? = null
    private var viewModel: ResearchReportViewModel? = null
    private var referenceViewModel: ReferenceViewModel? = null
    private var paginationViewModel: PaginationViewModel? = null
    private var researchViewModel: ResearchViewModel? = null
    private var researchReportPageAdapter: ResearchReportListAdapter? = null
    private var paginationAdapter: LearningPaginationAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        researchViewModel = ViewModelProvider(requireActivity())[ResearchViewModel::class.java]
        viewModel = ViewModelProvider(this)[ResearchReportViewModel::class.java]
        paginationViewModel = ViewModelProvider(this)[PaginationViewModel::class.java]
        referenceViewModel = ViewModelProvider(this)[ReferenceViewModel::class.java]
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
        binding?.pageVm = paginationViewModel
        binding?.lifecycleOwner = this
        init()
    }

    private fun init() {
        setObserve()
        initListener()
        viewModel?.setYear(currentYear())
        viewModel?.setSortingLabel(resources.getString(R.string.label_pilih_kategori))
    }

    private fun initListener() {
        binding?.swipeRefresh?.setOnRefreshListener {
            apiGetResearch()
        }
        binding?.btSorting?.setOnClickListener {
            sortingPressed()
        }
    }

    private fun initList() {
        researchReportPageAdapter = ResearchReportListAdapter(childFragmentManager, viewModel?.researchData ?: mutableListOf())
        binding?.recyclerView?.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = researchReportPageAdapter
        }
        if (viewModel?.researchData?.isEmpty() == true){
            emptyState()
        }
    }

    private fun initPagination() {
        paginationAdapter = LearningPaginationAdapter(paginationViewModel?.getLinks() ?: mutableListOf(), object : LearningPaginationAdapter.LearningListener{
            override fun itemClicked(page: Links, position: Int) {
                if (paginationViewModel?.requesting == false){
                    // for inactive page button
                    paginationViewModel?.getLinks()?.forEachIndexed { index, links ->
                        if (links.active == true){
                            links.active = false
                            paginationAdapter?.notifyItemChanged(index)
                            return@forEachIndexed
                        }
                    }

                    val lastPage = paginationViewModel?.lastPage?.toInt() ?: 0
                    val currentPage = paginationViewModel?.currentPage ?: "1"
                    if (page.label?.lowercase()?.contains(Links.previous) == true) {
                        var prevPage = Links.previousPage(currentPage).toInt()
                        if (prevPage < 1){
                            prevPage = 1
                        }

                        paginationViewModel?.getLinks()?.get(prevPage)?.active = true
                        paginationAdapter?.notifyItemChanged(prevPage)

                        paginationViewModel?.setNextPage(prevPage.toString())
                    } else if (page.label?.lowercase()?.contains(Links.next) == true) {
                        var nextPage = Links.nextPage(currentPage).toInt()
                        if (nextPage > lastPage) {
                            nextPage = lastPage
                        }

                        paginationViewModel?.getLinks()?.get(nextPage)?.active = true
                        paginationAdapter?.notifyItemChanged(nextPage)

                        paginationViewModel?.setNextPage(nextPage.toString())
                    } else {
                        paginationViewModel?.getLinks()?.get(position)?.active = true
                        paginationAdapter?.notifyItemChanged(position)
                        paginationViewModel?.setNextPage(page.label ?: "0")
                    }
                    paginationViewModel?.currentPage = paginationViewModel?.getNextPage() ?: "1"
                    apiGetNextResearch()
                }
            }
        })
        binding?.recyclerViewPagination?.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = paginationAdapter
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

    private fun sortingPressed() {
        val popup = PopupMenu(requireContext(), binding?.btSorting!!)
        popup.menu.add(resources.getString(R.string.sorting_semua))
        referenceViewModel?.refResearchCategories?.forEach {
            popup.menu.add(it.name)
        }
        popup.setOnMenuItemClickListener { item: MenuItem? ->
            item?.let {
                val title = it.title?.toString() ?: ""
                viewModel?.setSortingLabel(title)
                if (title == resources.getString(R.string.sorting_semua)){
                    viewModel?.setCategory("")
                } else {
                    referenceViewModel?.refResearchCategories?.forEach {ref ->
                        if (title == ref.name) {
                            viewModel?.setCategory(ref.code ?: "")
                            return@forEach
                        }
                    }
                }
                apiGetResearch()
            }
            true
        }
        popup.show()
    }

    private fun setObserve() {
        viewModel?.reqOtpResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                    when(running_service){
                        RunningServiceType.RESEARCH -> {
                            viewModel?.apiResearch(it.data?.data ?: "")
                        }
                        RunningServiceType.RESEARCH_NEXT -> {
                            viewModel?.apiGetNextLearnings(it.data?.data ?: "", paginationViewModel?.getNextPage() ?: "1")
                        }
                        else -> {}
                    }
                }
                Status.LOADING -> {
                    if (binding?.swipeRefresh?.isRefreshing == false) {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
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
                    it.data?.let { responseResearch ->
                        researchViewModel?.researchTabTitle()?.value = (responseResearch.total_rows ?: 0).toString()
                        viewModel?.researchData?.clear()
                        viewModel?.researchData?.addAll(responseResearch.data as MutableList<ResponseResearchData>)
                        initList()
                        paginationViewModel?.setLinks(responseResearch.total_pages ?: 0)
                        paginationViewModel?.setNextPage(ResponseResearch.getNextPage(responseResearch).toString())
                        initPagination()
                        if (referenceViewModel?.refResearchCategories?.size == 0) {
                            apiGetResearchCategories()
                        }
                    }
                }
                Status.LOADING -> {
                    if (binding?.swipeRefresh?.isRefreshing == false) {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    binding?.swipeRefresh?.isRefreshing = false
                    binding?.progressBar?.visibility = View.GONE
                }
            }
        }

        viewModel?.reqNextResearchResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                    paginationViewModel?.requesting = false
                    it?.data?.data?.let {data ->
                        viewModel?.researchData?.addAll(data)
                        initList()
                        binding?.nestedScrollView?.smoothScrollTo(0,0)
                    }
                }
                Status.LOADING -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                    paginationViewModel?.requesting = true
                }
                Status.ERROR -> {
                    binding?.progressBar?.visibility = View.GONE
                    paginationViewModel?.requesting = false
                }
            }
        }

        referenceViewModel?.reqOtpResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                    when(running_service){
                        RunningServiceType.RESEARCH_CATEGORIES -> {
                            referenceViewModel?.apiResearchCategories(it.data?.data ?: "")
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

        referenceViewModel?.reqResearchCategoriesResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                }
                Status.ERROR -> {
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
    private fun apiGetNextResearch() {
        paginationViewModel?.requesting = true
        running_service = RunningServiceType.RESEARCH_NEXT
        apiGetOtp()
    }

    private fun apiGetResearchCategories() {
        running_service = RunningServiceType.RESEARCH_CATEGORIES
        referenceViewModel?.apiGetOtp()
    }
}