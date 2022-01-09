package id.co.ptn.hungrystock.ui.main.research.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseBottomSheetModal
import id.co.ptn.hungrystock.databinding.DialogFilterResearchPageBinding
import id.co.ptn.hungrystock.ui.main.research.adapters.FilterEmitenListAdapter
import id.co.ptn.hungrystock.ui.main.research.adapters.FilterYearListAdapter
import java.util.*

class FilterResearchPageDialog: BaseBottomSheetModal() {

    private lateinit var binding: DialogFilterResearchPageBinding
    private lateinit var filterYearListAdapter: FilterYearListAdapter
    private var years: MutableList<Int> = mutableListOf()
    private lateinit var filterEmitenListAdapter: FilterEmitenListAdapter
    private var filterEmiten: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.dialog_filter_research_page, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initListener()

        initYearData()
        initListYear()

        initSpinnerMonth()

        initDataFilterEmiten()
        initFilterEmiten()
    }

    private fun initListener() {
        binding.btClose.setOnClickListener { dismiss() }
        binding.btApply.setOnClickListener {  }
        binding.btSpinnerMonth.setOnClickListener {  }
    }


    private fun initYearData() {
        years.clear()
        val startYear = 2014
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        var year = startYear
        for (i in 0..( currentYear - startYear )) {
            year += 1
            years.add( year)
        }
    }

    private fun initListYear() {
        filterYearListAdapter = FilterYearListAdapter(years)
        binding.recyclerYear.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = filterYearListAdapter
        }
    }

    private fun initSpinnerMonth() {

    }

    private fun initDataFilterEmiten() {
        filterEmiten.clear()
        filterEmiten.add("Terbaru")
        val value = "A"
        val charValue = value[0].code
        for (i in 0..21) {
            val next: String = ((charValue + 1).toString())
            filterEmiten.add(next)
        }

    }

    private fun initFilterEmiten() {
        filterEmitenListAdapter = FilterEmitenListAdapter(filterEmiten)
        binding.recyclerEmiten.apply {
            layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
            adapter = filterEmitenListAdapter
        }
    }
}