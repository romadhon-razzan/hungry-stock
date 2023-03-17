package id.co.ptn.hungrystock.ui.main.research.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.core.SessionManager
import id.co.ptn.hungrystock.models.auth.ResponseOtp
import id.co.ptn.hungrystock.models.main.research.ResearchFilter
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.repositories.ResearchRepository
import id.co.ptn.hungrystock.utils.HashUtils
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResearchReportViewModel @Inject constructor(val repository: ResearchRepository) : BaseViewModel() {
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

    private var type = "Terbaru"
    fun getType(): String = type
    fun setType(t: String) {
        type = t
    }

    private var labelSorting = "Terbaru"
    fun getLabelSorting(): String = labelSorting
    fun setLabelSorting(t: String) {
        labelSorting = t
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

    private var _reqOtpResponse: MutableLiveData<Resource<ResponseOtp>> = MutableLiveData()
    fun reqOtpResponse(): MutableLiveData<Resource<ResponseOtp>> = _reqOtpResponse

    private var _reqResearchResponse: MutableLiveData<Resource<JsonObject>> = MutableLiveData()
    fun reqResearchResponse(): MutableLiveData<Resource<JsonObject>> = _reqResearchResponse

    /**
     * Api
     * */

    fun apiGetOtp() {
        viewModelScope.launch {
            try {
                TOKEN = HashUtils.hash256Otp()
                _reqOtpResponse.postValue(Resource.loading(null))
                repository.otp().let {
                    if (it.isSuccessful){
                        _reqOtpResponse.postValue(Resource.success(it.body()))
                    } else {
                        //
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
    fun apiResearch(sessionManager: SessionManager?, otp: String) {
        viewModelScope.launch {
            try {
                val parameter = StringBuilder()
                parameter.append("order_by=category_name")
                TOKEN = "${HashUtils.hash256Research(parameter.toString())}.${ENV.userKey()}.$otp"
                _reqResearchResponse.postValue(Resource.loading(null))
                repository.getResearch(parameter.toString()).let {
                    if (it.isSuccessful){
                        _reqResearchResponse.postValue(Resource.success(it.body()))
                    } else {
                        //
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}