package id.co.ptn.hungrystock.ui.main.research.viewmodel

import androidx.lifecycle.LiveData
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
import id.co.ptn.hungrystock.helper.extension.printToLog
import id.co.ptn.hungrystock.models.auth.ResponseOtp
import id.co.ptn.hungrystock.models.main.research.ResearchFilter
import id.co.ptn.hungrystock.models.main.research.ResponseResearch
import id.co.ptn.hungrystock.models.main.research.ResponseResearchData
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.repositories.ResearchRepository
import id.co.ptn.hungrystock.utils.HashUtils
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResearchReportViewModel @Inject constructor(val repository: ResearchRepository) : BaseViewModel() {
    private val _sortingLabel = MutableLiveData("")
    val sortingLabel: LiveData<String> = _sortingLabel
    fun setSortingLabel(title: String) {
        _sortingLabel.value = title
    }

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

    var pageFirstRequested = false

    private var _reqOtpResponse: MutableLiveData<Resource<ResponseOtp>> = MutableLiveData()
    fun reqOtpResponse(): MutableLiveData<Resource<ResponseOtp>> = _reqOtpResponse

    private var _reqResearchResponse: MutableLiveData<Resource<ResponseResearch>> = MutableLiveData()
    fun reqResearchResponse(): MutableLiveData<Resource<ResponseResearch>> = _reqResearchResponse
    private var _reqNextResearchResponse: MutableLiveData<Resource<ResponseResearch>> = MutableLiveData()
    fun reqNextResearchResponse(): MutableLiveData<Resource<ResponseResearch>> = _reqNextResearchResponse
    var researchData: MutableList<ResponseResearchData> = mutableListOf()

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
    fun apiResearch(otp: String) {
        viewModelScope.launch {
            try {
                val parameter = StringBuilder()
                parameter.append("order_by=category_name")
                if (getYear().isNotEmpty()){
                    parameter.append("&year=${getYear()}")
                }
                if (getMonth().isNotEmpty()){
                    parameter.append("&month=${getMonth()}")
                }
                if (getInitial().isNotEmpty()){
                    parameter.append("&initial=${getInitial()}")
                }
                if (getCategory().isNotEmpty()){
                    parameter.append("&category_id=${getCategory()}")
                }
                TOKEN = "${HashUtils.hash256Research(parameter.toString())}.${ENV.userKey()}.$otp"
                TOKEN.printToLog("access_token_research")
                _reqResearchResponse.postValue(Resource.loading(null))
                repository.getResearch(parameter.toString()).let {
                    if (it.isSuccessful){
                        _reqResearchResponse.postValue(Resource.success(it.body()))
                    } else {
                        _reqResearchResponse.postValue(Resource.error(it.body()?.message ?: "",it.body()))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun apiGetNextLearnings(otp: String, nextPage: String) {
        viewModelScope.launch {
            try {
                val parameter = StringBuilder()
                parameter.append("order_by=category_name&offset=${nextPage}")
                TOKEN = "${HashUtils.hash256Research(parameter.toString())}.${ENV.userKey()}.$otp"
                _reqNextResearchResponse.postValue(Resource.loading(null))
                repository.getResearch(parameter.toString()).let {
                    if (it.isSuccessful){
                        _reqNextResearchResponse.postValue(Resource.success(it.body()))
                    } else {
                        //
                        _reqNextResearchResponse.postValue(Resource.error(it.body()?.message ?: "", null))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}