package id.co.ptn.hungrystock.ui.main.learning.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseBottomSheetModal
import id.co.ptn.hungrystock.bases.popup.AbjadPopupMenu
import id.co.ptn.hungrystock.bases.popup.MonthPopupMenu
import id.co.ptn.hungrystock.databinding.DialogFilterLearningPageBinding
import id.co.ptn.hungrystock.databinding.DialogFilterResearchPageBinding
import id.co.ptn.hungrystock.ui.main.research.adapters.FilterEmitenListAdapter
import id.co.ptn.hungrystock.ui.main.research.adapters.FilterYearListAdapter
import java.util.*

class FilteLearningPageDialog(private val listener: Listener): BaseBottomSheetModal() {

    private lateinit var binding: DialogFilterLearningPageBinding
    private lateinit var filterYearListAdapter: FilterYearListAdapter
    private var years: MutableList<Int> = mutableListOf()
    private var yearSelected = "1900"
    private var monthSelected = ""
    private var monthIdSelected = ""
    private var abjadSelected = ""
    private lateinit var filterEmitenListAdapter: FilterEmitenListAdapter
    private var filterEmiten: MutableList<String> = mutableListOf()

    fun setYearSelected(year: String) {
        yearSelected = year
    }

    fun setMontSelected(month: String, id: String) {
        monthSelected = month
        monthIdSelected = id
    }

    fun setAbjadSelected(abjad: String) {
        abjadSelected = abjad
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.dialog_filter_learning_page, container, false)
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
        if (yearSelected.isNullOrEmpty())
            yearSelected = "0"

        if (monthSelected.isNotEmpty())
            binding.tvMonth.text = monthSelected

        if (abjadSelected.isNotEmpty())
            binding.tvAbjad.text = abjadSelected
    }

    private fun initListener() {
        binding.btClose.setOnClickListener { dismiss() }
        binding.btApply.setOnClickListener {
            if (yearSelected == "0") yearSelected = ""
            listener.onFilter(yearSelected, monthSelected, monthIdSelected, abjadSelected)
            dismiss()
        }
        binding.btSpinnerMonth.setOnClickListener { monthPressed() }
        binding.btSpinnerAbjad.setOnClickListener { abjadPressed() }
        binding.btReset.setOnClickListener { resetPressed() }
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
        filterYearListAdapter = FilterYearListAdapter(years, yearSelected.toInt(), object : FilterYearListAdapter.Listener{
            override fun onClick(year: String) {
                yearSelected = year
                initListYear()
            }
        })
        binding.recyclerYear.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = filterYearListAdapter
        }
    }

    /**
     * OnPressed
     * */

    private fun monthPressed() {
        val monthPopup = MonthPopupMenu(requireContext(), object : MonthPopupMenu.Listener{
            override fun onSelected(month: String, id: String) {
                monthSelected = month
                monthIdSelected = id
                binding.tvMonth.text = month
            }
        })
        monthPopup.show(binding.btSpinnerMonth)
    }

    private fun abjadPressed() {
        val abjadPopup = AbjadPopupMenu(requireContext(), object : AbjadPopupMenu.Listener{
            override fun onSelected(abjad: String) {
                abjadSelected = abjad
                binding.tvAbjad.text = abjad
            }
        })
        abjadPopup.show(binding.btSpinnerAbjad)
    }

    private fun resetPressed() {
        yearSelected = ""
        initListYear()
        monthSelected = ""
        monthIdSelected = ""
        binding.tvMonth.text = requireActivity().resources.getString(R.string.hint_pilih_bulan)
        abjadSelected = ""
        binding.tvAbjad.text = requireActivity().resources.getString(R.string.label_pilih)
    }


    public interface Listener{
        fun onFilter(year: String, month: String, monthId: String, abjad: String)
    }
}