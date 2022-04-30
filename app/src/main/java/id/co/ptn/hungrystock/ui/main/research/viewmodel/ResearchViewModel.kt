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
}