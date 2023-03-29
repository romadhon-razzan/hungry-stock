package id.co.ptn.hungrystock.ui.main.research.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.repositories.AppRepository
import javax.inject.Inject

@HiltViewModel
class ResearchViewModel @Inject constructor(val repository: AppRepository) : BaseViewModel() {
    private val _titleTabResearch = MutableLiveData("")
    val titleTabResearch: LiveData<String> = _titleTabResearch

    private val _titleTabStock = MutableLiveData("")
    val titleTabStock: LiveData<String> = _titleTabStock

    fun setTitleTabResearchReport(s: String) {
        _titleTabResearch.value = s
    }

    fun getTabTitleTabResearchReport(): MutableLiveData<String> {
        return _titleTabResearch
    }

    fun setTitleTabStockData(s: String) {
        _titleTabStock.value = s
    }

    fun getTabTitleTabStockData(): MutableLiveData<String> {
        return _titleTabStock
    }

    private var _researchTabTitle: MutableLiveData<String> = MutableLiveData()
    fun researchTabTitle(): MutableLiveData<String> = _researchTabTitle

    private var _stockTabTitle: MutableLiveData<String> = MutableLiveData()
    fun stockTabTitle(): MutableLiveData<String> = _stockTabTitle


    private var keyword = ""
    fun getKeyword(): String = keyword
    fun setKeyword(k: String) {
        keyword = k
    }

    private var year = ""
    fun getYear(): String = year
    fun setYear(y: String) {
        year = y
    }

    private var month = ""
    fun getMonth(): String = month
    fun setMonth(m: String) {
        month = m
    }

    var monthName = ""

    private var initial = ""
    fun getInitial(): String = initial
    fun setInitial(i: String) {
        initial = i
    }

    private val _filterValues = MutableLiveData("")
    val filterValues: LiveData<String> = _filterValues
    fun setFilterValues() {
        val parameter = StringBuilder()
        parameter.append("Filter berdasarkan:")
        if (getYear().isNotEmpty()){
            parameter.append(" [Tahun ${getYear()}]")
        }
        if (monthName.isNotEmpty()){
            parameter.append(" [Bulan ${monthName}]")
        }
        if (getInitial().isNotEmpty()){
            parameter.append(" [Inisial: ${getInitial()}]")
        }
        _filterValues.postValue(parameter.toString())
    }

    private var _onFilter: MutableLiveData<Boolean> = MutableLiveData()
    fun onFilter(): MutableLiveData<Boolean> = _onFilter

    private var _onSearch: MutableLiveData<Boolean> = MutableLiveData()
    fun onSearch(): MutableLiveData<Boolean> = _onSearch
}