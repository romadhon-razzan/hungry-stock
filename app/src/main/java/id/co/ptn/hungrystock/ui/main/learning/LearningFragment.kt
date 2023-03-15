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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseFragment
import id.co.ptn.hungrystock.bases.EmptyStateFragment
import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.core.network.RunningServiceType
import id.co.ptn.hungrystock.core.network.running_service
import id.co.ptn.hungrystock.databinding.LearningFragmentBinding
import id.co.ptn.hungrystock.models.Links
import id.co.ptn.hungrystock.models.User
import id.co.ptn.hungrystock.models.main.home.PastEvent
import id.co.ptn.hungrystock.models.main.home.ResponseEventData
import id.co.ptn.hungrystock.models.main.home.ResponseEvents
import id.co.ptn.hungrystock.models.main.home.ResponseEventsData
import id.co.ptn.hungrystock.models.main.learning.Learning
import id.co.ptn.hungrystock.ui.general.view_model.OtpViewModel
import id.co.ptn.hungrystock.ui.main.learning.adapters.LearningListAdapter
import id.co.ptn.hungrystock.ui.main.learning.adapters.LearningPaginationAdapter
import id.co.ptn.hungrystock.ui.main.learning.dialogs.FilteLearningPageDialog
import id.co.ptn.hungrystock.ui.main.learning.viewmodel.LearningViewModel
import id.co.ptn.hungrystock.utils.HashUtils
import id.co.ptn.hungrystock.utils.Status
import id.co.ptn.hungrystock.utils.getHHmm
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LearningFragment : BaseFragment() {

    companion object {
        fun newInstance() = LearningFragment()
    }

    private lateinit var binding: LearningFragmentBinding
    private var viewModel: LearningViewModel? = null
    private lateinit var learningListAdapter: LearningListAdapter
    private var paginationAdapter: LearningPaginationAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[LearningViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.learning_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                override fun itemClicked(learning: ResponseEventsData) {
                    if (!User.isExpired(childFragmentManager,sessionManager?.user?.membership_end_at ?: "")){
                        val intent =  router.toLearningDetail()
                        try {
                            val event = PastEvent(
                                learning.description ?: "",
                                learning.title ?: "",
                                learning.speakers ?: "", 0,
                                getHHmm((learning.date_from ?: 0) * 1000),
                                getHHmm((learning.date_from ?: 0) * 1000),
                                learning.video_file ?: "")
                            intent.putExtra("event", Gson().toJson(event))
                        }catch (e: Exception){
                            e.printStackTrace()
                        }
                        requireContext().startActivity(intent)
                    }
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
        paginationAdapter = LearningPaginationAdapter(viewModel?.getLinks() ?: mutableListOf(), object : LearningPaginationAdapter.LearningListener{
            override fun itemClicked(page: Links, position: Int) {
                if (viewModel?.requesting == false){
                    // for inactive page button
                    viewModel?.getLinks()?.forEachIndexed { index, links ->
                        if (links.active == true){
                            links.active = false
                            paginationAdapter?.notifyItemChanged(index)
                            return@forEachIndexed
                        }
                    }

                    val lastPage = viewModel?.lastPage?.toInt() ?: 0
                    val currentPage = viewModel?.currentPage ?: "1"
                    if (page.label?.lowercase()?.contains(Links.previous) == true) {
                        var prevPage = Links.previousPage(currentPage).toInt()
                        if (prevPage < 1){
                            prevPage = 1
                        }

                        viewModel?.getLinks()?.get(prevPage)?.active = true
                        paginationAdapter?.notifyItemChanged(prevPage)

                        viewModel?.setNextPage(prevPage.toString())
                    } else if (page.label?.lowercase()?.contains(Links.next) == true) {
                        var nextPage = Links.nextPage(currentPage).toInt()
                        if (nextPage > lastPage) {
                            nextPage = lastPage
                        }

                        viewModel?.getLinks()?.get(nextPage)?.active = true
                        paginationAdapter?.notifyItemChanged(nextPage)

                        viewModel?.setNextPage(nextPage.toString())
                    } else {
                        viewModel?.getLinks()?.get(position)?.active = true
                        paginationAdapter?.notifyItemChanged(position)
                        viewModel?.setNextPage(page.label ?: "0")
                    }
                    viewModel?.currentPage = viewModel?.getNextPage() ?: "1"
                    apiGetNextLearnings()
                }
            }
        })
        binding.rvPagination.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = paginationAdapter
        }
    }

    private fun initData() {
        viewModel?.getLearnings()?.clear()
        viewModel?.reqLearningResponse()?.value?.let {
            it.data?.data.let { learnings ->
                viewModel?.getLearnings()?.addAll(learnings ?: mutableListOf())
                initPagination()
            }
        } ?: emptyState()
        initList()
    }

    private fun setNextData() {
        viewModel?.getLearnings()?.clear()
        try {
            viewModel?.reqNextLearningResponse()?.value?.let {
                it.data?.data?.let { learnings ->
                    viewModel?.getLearnings()?.addAll(learnings)
                }
            }
            initList()
//            initPagination()
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
        viewModel?.reqOtpResponse()?.observe(viewLifecycleOwner){
            if (running_service == RunningServiceType.EVENT){
                TOKEN = "${HashUtils.hash256Events("customer_id=${sessionManager?.authData?.code ?: ""}")}.${ENV.userKey()}.${it.data?.data ?: ""}"
                Log.d("access_token", TOKEN)
                lifecycleScope.launch {
                    delay(500)
                    viewModel?.apiGetLearnings(sessionManager, viewModel?.getKeyword()!!,viewModel?.getCategory()!!,viewModel?.getYear()!!,viewModel?.getMonthId()!!,viewModel?.getAbjad()!!)
                }
            } else if (running_service == RunningServiceType.EVENT_NEXT) {
                TOKEN = "${HashUtils.hash256Events("customer_id=${sessionManager?.authData?.code ?: ""}&offset=${viewModel?.getNextPage()}")}.${ENV.userKey()}.${it.data?.data ?: ""}"
                Log.d("access_token", TOKEN)
                lifecycleScope.launch {
                    delay(500)
                    viewModel?.apiGetNextLearnings(sessionManager, viewModel?.getNextPage()!!, viewModel?.getKeyword()!!,viewModel?.getCategory()!!,viewModel?.getYear()!!,viewModel?.getMonthId()!!,viewModel?.getAbjad()!!)
                }
            }
        }
        viewModel?.reqLearningResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS ->{
                    binding.progressBar.visibility = View.GONE

                    it.data?.let { data ->
                        initData()
                        viewModel?.setLinks(data.total_pages ?: 0)
                        viewModel?.setNextPage(ResponseEvents.getNextPage(data).toString())
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
            viewModel?.requesting = false
            when(it.status) {
                Status.SUCCESS ->{
                    binding.progressBar.visibility = View.GONE
                    it.data?.data?.let {data ->
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
    private fun apiGetOtp() {
        viewModel?.apiGetOtp()
    }
    private fun apiGetLearnings() {
        running_service = RunningServiceType.EVENT
        apiGetOtp()
    }

    private fun apiGetNextLearnings() {
        viewModel?.requesting = true
        running_service = RunningServiceType.EVENT_NEXT
        apiGetOtp()
    }

}