package id.co.ptn.hungrystock.ui.main.research.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
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
        val value = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        for (element in value) {
            filterEmiten.add(element.toString())
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