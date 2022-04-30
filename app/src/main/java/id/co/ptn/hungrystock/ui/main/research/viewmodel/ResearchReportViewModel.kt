package id.co.ptn.hungrystock.ui.main.research.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.models.main.research.ResearchFilter
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResearchReportViewModel @Inject constructor(val repository: AppRepository) : BaseViewModel() {
    private var keyword = ""
    fun getKeyword(): String = keyword
    fun setKeyword(k: String) {
        keyword = k
    }

    private var category = ""
    fun getCategory(): String = category
    fun setCategory(c: String) {
        category = c
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

    private var type = ""
    fun getType(): String = type
    fun setType(t: String) {
        type = t
    }

    private var initial = ""
    fun getInitial(): String = initial
    fun setInitial(i: String) {
        initial = i
    }

    private var filters = mutableListOf<ResearchFilter>()
    fun getFilters(): MutableList<ResearchFilter> = filters
    fun setFilter(v: MutableList<ResearchFilter>) {
        filters.clear()
        filters.addAll(v)
    }

    private var _reqResearchResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    fun reqResearchResponse(): MutableLiveData<Resource<JsonObject>> = _reqResearchResponse

    /**
     * Api
     * */

    fun apiResearch(t: String, k: String, c: String, y: String, m: String, i: String) {
        viewModelScope.launch {
            try {
                _reqResearchResponse.postValue(Resource.loading(null))
                repository.getResearch(t,k, c, y, m, i).let {
                    if (it.isSuccessful){
                        _reqResearchResponse.postValue(Resource.success(it.body()))
                    } else {
                        val type = object : TypeToken<JsonObject>() {}.type
                        var errorResponse: JsonObject? = null
                        try {
                            errorResponse = Gson().fromJson(it.errorBody()?.charStream(), type)
                        } catch(e: Exception) {
                            e.printStackTrace()
                        }
                        _reqResearchResponse.postValue(Resource.error(it.errorBody().toString(), errorResponse))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}