package id.co.ptn.hungrystock.ui.main.learning

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseFragment
import id.co.ptn.hungrystock.bases.EmptyStateFragment
import id.co.ptn.hungrystock.databinding.LearningFragmentBinding
import id.co.ptn.hungrystock.models.Links
import id.co.ptn.hungrystock.models.main.home.PastEvent
import id.co.ptn.hungrystock.models.main.home.ResponseEventData
import id.co.ptn.hungrystock.models.main.learning.Learning
import id.co.ptn.hungrystock.ui.main.learning.adapters.LearningListAdapter
import id.co.ptn.hungrystock.ui.main.learning.adapters.LearningPaginationAdapter
import id.co.ptn.hungrystock.ui.main.learning.dialogs.FilteLearningPageDialog
import id.co.ptn.hungrystock.ui.main.learning.viewmodel.LearningViewModel
import id.co.ptn.hungrystock.utils.Status

@AndroidEntryPoint
class LearningFragment : BaseFragment() {

    companion object {
        fun newInstance() = LearningFragment()
    }

    private lateinit var binding: LearningFragmentBinding
    private var viewModel: LearningViewModel? = null
    private lateinit var learningListAdapter: LearningListAdapter
    private var paginationAdapter: LearningPaginationAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.learning_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[LearningViewModel::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = this
        init()
    }

    private fun init() {
        viewModel?.setSortingLabel(resources.getString(R.string.sorting_terbaru))
        initListener()
        initSearch()
        setObserve()
        apiGetLearnings()
    }

    private fun initListener() {
        binding.btSorting.setOnClickListener { sortingPressed() }
        binding.btFilter.setOnClickListener { filterPressed() }
        binding.btNext.setOnClickListener { apiGetNextLearnings() }
    }

    private fun initSearch() {
        binding.etSearch.addTextChangedListener( object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel?.setKeyword(s.toString())
                Handler(Looper.getMainLooper()).postDelayed({
                    apiGetLearnings()
                },1500)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun initList() {
        viewModel?.getLearnings()?.let { learnings ->
            learningListAdapter = LearningListAdapter(learnings, object : LearningListAdapter.LearningListener{
                override fun itemClicked(learning: Learning) {
                    val intent =  router.toLearningDetail()
                    try {
                        val event = PastEvent(learning.slug!!, learning.title!!, learning.speaker!!, learning.event_date!!, learning.event_hour_start!!, learning.event_hour_end!!, learning.video_url!!)
                        intent.putExtra("event", Gson().toJson(event))
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                    requireContext().startActivity(intent)
                }

            })
            binding.recyclerView.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = learningListAdapter
            }
            binding.frameContainer.visibility = View.GONE
        }
    }

    private fun initPagination() {
        viewModel?.getLinks()?.let { links ->
            paginationAdapter = LearningPaginationAdapter(links, object : LearningPaginationAdapter.LearningListener{
                override fun itemClicked(page: Links, position: Int) {
                    val lastPage = viewModel?.lastPage?.toInt() ?: 0
                    val currentPage = viewModel?.getNextPage()?.toInt() ?: 0
                    if (page.label?.lowercase()?.contains("sebelumnya") == true) {
                        var prevPage = Links.previousPage(currentPage.toString()).toInt()
                        if (prevPage < 1){
                            prevPage = 1
                        }
                        viewModel?.setNextPage(prevPage.toString())
                    } else if (page.label?.lowercase()?.contains("berikutnya") == true) {
                        var nextPage = Links.nextPage(currentPage.toString()).toInt()
                        if (nextPage > lastPage) {
                            nextPage = lastPage
                        }
                        viewModel?.setNextPage(nextPage.toString())
                    } else {
                        viewModel?.setNextPage(page.label ?: "0")
                    }
                    apiGetNextLearnings()
                }
            })
            binding.rvPagination.apply {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                adapter = paginationAdapter
            }
        }
    }

    private fun initData() {
        viewModel?.getLearnings()?.clear()
        viewModel?.reqLearningResponse()?.value?.let {
            it.data?.data?.learnings?.data?.let { learnings ->
                viewModel?.getLearnings()?.addAll(learnings)
                initPagination()
            } ?: emptyState()
        } ?: emptyState()
        initList()
    }

    private fun setNextData() {
        viewModel?.getLearnings()?.clear()
        try {
            viewModel?.reqNextLearningResponse()?.value?.let {
                it.data?.data?.learnings?.data?.let { learnings ->
                    viewModel?.getLearnings()?.addAll(learnings)
                }
            }
            initList()
            initPagination()
            binding.nestedScrollView.smoothScrollTo(0,0)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun emptyState() {
        binding.frameContainer.visibility = View.VISIBLE
        childFragmentManager.commit {
            setReorderingAllowed(true)
            val bundle = Bundle()
            bundle.putString("title","Event belum ditemukan")
            bundle.putString("message","")
            add<EmptyStateFragment>(R.id.frame_container, "", bundle)
        }

    }

    /**
     * On pressed
     * */
    private fun sortingPressed() {
        val popup = PopupMenu(requireContext(), binding.btSorting)
        popup.inflate(R.menu.sorting_learning_menu)

        popup.setOnMenuItemClickListener { item: MenuItem? ->
            item?.let {
                when (it.itemId) {
                    R.id.terbaru -> {
                        viewModel?.setSortingLabel(resources.getString(R.string.sorting_terbaru))
                        viewModel?.setCategory("")
                    }
                    R.id.topup -> {
                        viewModel?.setSortingLabel(resources.getString(R.string.sorting_top_up_knowledge_amp_wisdom))
                        viewModel?.setCategory(viewModel?.sortingLabel?.value.toString())
                    }
                    R.id.temu -> {
                        viewModel?.setSortingLabel(resources.getString(R.string.sorting_temu_emiten))
                        viewModel?.setCategory(viewModel?.sortingLabel?.value.toString())
                    }
                    R.id.bedah -> {
                        viewModel?.setSortingLabel(resources.getString(R.string.sorting_bedah_emiten))
                        viewModel?.setCategory(viewModel?.sortingLabel?.value.toString())
                    }
                    R.id.stockScope -> {
                        viewModel?.setSortingLabel(resources.getString(R.string.sorting_stockscope))
                        viewModel?.setCategory(viewModel?.sortingLabel?.value.toString())
                    }
                    R.id.stock_discovery -> {
                        viewModel?.setSortingLabel(resources.getString(R.string.stock_discovery))
                        viewModel?.setCategory(viewModel?.sortingLabel?.value.toString())
                    }
                    else -> {}
                }
            }
            apiGetLearnings()
            true
        }

        popup.show()

    }

    private fun filterPressed() {
        val dialog = FilteLearningPageDialog(object : FilteLearningPageDialog.Listener{
            override fun onFilter(year: String, month: String, monthId: String, abjad: String) {
                viewModel?.setYear(year)
                viewModel?.setMonth(month)
                viewModel?.setMonthId(monthId)
                viewModel?.setAbjad(abjad)

                binding.tvFilterValue.text = "Filter berdasarkan: "
                binding.tvFilterValue.visibility = View.VISIBLE

                val filterValues: MutableList<String> = mutableListOf()
                viewModel?.getYear()?.let { y ->
                    if (y.isNotEmpty())
                        filterValues.add(y)
                }

                viewModel?.getMonth()?.let { m ->
                    if (m.isNotEmpty())
                        filterValues.add(m)
                }

                viewModel?.getAbjad()?.let { a ->
                    if (a.isNotEmpty())
                        filterValues.add(a)
                }

                if (filterValues.size < 1) {
                    binding.tvFilterValue.visibility = View.GONE
                    binding.lblFilter.text = requireActivity().resources.getString(R.string.button_filter)
                }
                else {
                    filterValues.forEachIndexed { index, s ->
                        binding.tvFilterValue.append(s)
                        if (index < filterValues.size - 1 ) {
                            binding.tvFilterValue.append(", ")
                        }
                    }
                    binding.lblFilter.text = requireActivity().resources.getString(R.string.button_filter)
                    binding.lblFilter.append("(${filterValues.size})")
                }

                apiGetLearnings()
            }
        })
        viewModel?.getYear()?.let { y -> dialog.setYearSelected(y) }
        dialog.setMontSelected(viewModel?.getMonth()!!, viewModel?.getMonthId()!!)
        viewModel?.getAbjad()?.let { a ->
            dialog.setAbjadSelected(a)
        }
        dialog.show(childFragmentManager,"filter_dialog")
    }


    private fun setObserve() {
        viewModel?.reqLearningResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS ->{
                    binding.progressBar.visibility = View.GONE

                    it.data?.data?.let { data ->
                        viewModel?.lastPage = data.learnings.last_page?.toString() ?: "0"
                        data.learnings.links.let { links ->
                            viewModel?.setLinks(links as MutableList<Links>)
                        }
                        data.learnings.next_page_url?.let { _ ->
                            data.learnings.current_page?.let { cp -> viewModel?.setNextPage((cp+1).toString()) }
                            viewModel?.setCanLoadNext(true)
                        } ?: viewModel?.setCanLoadNext(false)

                        initData()
                    } ?: emptyState()
                }
                Status.LOADING ->{ binding.progressBar.visibility = View.VISIBLE}
                Status.ERROR ->{
                    binding.progressBar.visibility = View.GONE
                    emptyState()
                }
            }
        }

        viewModel?.reqNextLearningResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS ->{
                    binding.progressBar.visibility = View.GONE
                    viewModel?.setLoadingNext(false)
                    it.data?.data?.let {data ->
                        data.learnings.links.let { links ->
                            viewModel?.setLinks(links as MutableList<Links>)
                        }
                        setNextData()
                    }
                }
                Status.LOADING ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR ->{
                    binding.progressBar.visibility = View.GONE
                    showSnackBar(binding.container,"Something wrong")
                }
            }
        }
    }

    /**
     * Api
     * */

    private fun apiGetLearnings() {
        viewModel?.apiGetLearnings(viewModel?.getKeyword()!!,viewModel?.getCategory()!!,viewModel?.getYear()!!,viewModel?.getMonthId()!!,viewModel?.getAbjad()!!)
    }

    private fun apiGetNextLearnings() {
        viewModel?.apiGetNextLearnings(viewModel?.getNextPage()!!, viewModel?.getKeyword()!!,viewModel?.getCategory()!!,viewModel?.getYear()!!,viewModel?.getMonthId()!!,viewModel?.getAbjad()!!)
    }

}