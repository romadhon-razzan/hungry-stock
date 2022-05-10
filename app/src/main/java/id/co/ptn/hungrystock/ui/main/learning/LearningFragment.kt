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
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseFragment
import id.co.ptn.hungrystock.bases.EmptyStateFragment
import id.co.ptn.hungrystock.databinding.LearningFragmentBinding
import id.co.ptn.hungrystock.models.main.home.PastEvent
import id.co.ptn.hungrystock.models.main.home.ResponseEventData
import id.co.ptn.hungrystock.models.main.learning.Learning
import id.co.ptn.hungrystock.ui.main.learning.adapters.LearningListAdapter
import id.co.ptn.hungrystock.ui.main.learning.dialogs.FilteLearningPageDialog
import id.co.ptn.hungrystock.ui.main.learning.viewmodel.LearningViewModel
import id.co.ptn.hungrystock.utils.Status

@AndroidEntryPoint
class LearningFragment : BaseFragment() {

    companion object {
        fun newInstance() = LearningFragment()
    }

    private lateinit var binding: LearningFragmentBinding
    private val viewModel: LearningViewModel by activityViewModels()
    private lateinit var learningListAdapter: LearningListAdapter

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
        viewModel.setSortingLabel(resources.getString(R.string.sorting_terbaru))
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
                viewModel.setKeyword(s.toString())
                Handler(Looper.getMainLooper()).postDelayed({
                    apiGetLearnings()
                },1500)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun initList() {
        learningListAdapter = LearningListAdapter(viewModel.getLearnings(), object : LearningListAdapter.LearningListener{
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

    private fun initData() {
        viewModel.getLearnings().clear()
        viewModel.reqLearningResponse().value?.let {
            it.data?.data?.learnings?.data?.let { learnings ->
                viewModel.getLearnings().addAll(learnings)
            } ?: emptyState()
        } ?: emptyState()
        initList()
    }

    private fun setNextData() {
        try {
            viewModel.reqNextLearningResponse().value?.let {
                it.data?.data?.learnings?.data?.let { learnings ->
                    viewModel.getLearnings().addAll(learnings)
                }
            }
            learningListAdapter.notifyItemRangeInserted(learningListAdapter.itemCount, viewModel.getLearnings().size)
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
                        viewModel.setSortingLabel(resources.getString(R.string.sorting_terbaru))
                        viewModel.setCategory("")
                    }
                    R.id.topup -> {
                        viewModel.setSortingLabel(resources.getString(R.string.sorting_top_up_knowledge_amp_wisdom))
                        viewModel.setCategory(viewModel.sortingLabel.value.toString())
                    }
                    R.id.temu -> {
                        viewModel.setSortingLabel(resources.getString(R.string.sorting_temu_emiten))
                        viewModel.setCategory(viewModel.sortingLabel.value.toString())
                    }
                    R.id.bedah -> {
                        viewModel.setSortingLabel(resources.getString(R.string.sorting_bedah_emiten))
                        viewModel.setCategory(viewModel.sortingLabel.value.toString())
                    }
                    R.id.stockScope -> {
                        viewModel.setSortingLabel(resources.getString(R.string.sorting_stockscope))
                        viewModel.setCategory(viewModel.sortingLabel.value.toString())
                    }
                    R.id.stock_discovery -> {
                        viewModel.setSortingLabel(resources.getString(R.string.stock_discovery))
                        viewModel.setCategory(viewModel.sortingLabel.value.toString())
                    }
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
                viewModel.setYear(year)
                viewModel.setMonth(month)
                viewModel.setMonthId(monthId)
                viewModel.setAbjad(abjad)

                binding.tvFilterValue.text = "Filter berdasarkan: "
                binding.tvFilterValue.visibility = View.VISIBLE

                val filterValues: MutableList<String> = mutableListOf()
                if (viewModel.getYear().isNotEmpty())
                    filterValues.add(viewModel.getYear())

                if (viewModel.getMonth().isNotEmpty())
                    filterValues.add(viewModel.getMonth())

                if (viewModel.getAbjad().isNotEmpty())
                    filterValues.add(viewModel.getAbjad())

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
        dialog.setYearSelected(viewModel.getYear())
        dialog.setMontSelected(viewModel.getMonth(), viewModel.getMonthId())
        dialog.setAbjadSelected(viewModel.getAbjad())
        dialog.show(childFragmentManager,"filter_dialog")
    }


    private fun setObserve() {
        viewModel.reqLearningResponse().observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS ->{
                    binding.progressBar.visibility = View.GONE
                    Log.d("NEXT PAGE", "00000")
                    it.data?.data?.let { data ->
                        data.learnings.next_page_url?.let { _ ->
                            data.learnings.current_page?.let { cp -> viewModel.setNextPage((cp+1).toString()) }
                            viewModel.setCanLoadNext(true)
                        } ?: viewModel.setCanLoadNext(false)
                        Log.d("NEXT PAGE", viewModel.getNextPage())
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

        viewModel.reqNextLearningResponse().observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS ->{
                    viewModel.setLoadingNext(false)
                    it.data?.data?.let { data ->
                        data.learnings.next_page_url?.let { _ ->
                            data.learnings.current_page?.let { cp -> viewModel.setNextPage((cp+1).toString()) }
                            viewModel.setCanLoadNext(true)
                        } ?: viewModel.setCanLoadNext(false)
                        setNextData()
                    }
                }
                Status.LOADING ->{
                    viewModel.setLoadingNext(true)
                    viewModel.setCanLoadNext(false)
                }
                Status.ERROR ->{
                    viewModel.setLoadingNext(false)
                    showSnackBar(binding.container,"Something wrong")
                }
            }
        }
    }

    /**
     * Api
     * */

    private fun apiGetLearnings() {
        viewModel.apiGetLearnings(viewModel.getKeyword(),viewModel.getCategory(),viewModel.getYear(),viewModel.getMonthId(),viewModel.getAbjad())
    }

    private fun apiGetNextLearnings() {
        viewModel.apiGetNextLearnings(viewModel.getNextPage(), viewModel.getKeyword(),viewModel.getCategory(),viewModel.getYear(),viewModel.getMonthId(),viewModel.getAbjad())
    }

}