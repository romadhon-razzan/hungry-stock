package id.co.ptn.hungrystock.ui.main.learning.viewmodel

import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.core.SessionManager
import id.co.ptn.hungrystock.models.Links
import id.co.ptn.hungrystock.models.auth.ResponseOtp
import id.co.ptn.hungrystock.models.main.home.ResponseEvent
import id.co.ptn.hungrystock.models.main.home.ResponseEvents
import id.co.ptn.hungrystock.models.main.home.ResponseEventsData
import id.co.ptn.hungrystock.models.main.learning.Learning
import id.co.ptn.hungrystock.models.main.learning.ResponseLearning
import id.co.ptn.hungrystock.models.main.learning.ResponseLearningDetail
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.repositories.EventRepository
import id.co.ptn.hungrystock.utils.HashUtils
import id.co.ptn.hungrystock.utils.Resource
import id.co.ptn.hungrystock.utils.currentYear
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearningViewModel @Inject constructor(private val repository: EventRepository) : BaseViewModel() {
    private val _sortingLabel = MutableLiveData("")
    val sortingLabel: LiveData<String> = _sortingLabel
    fun setSortingLabel(title: String) {
        _sortingLabel.value = title
    }

    private var learnings: MutableList<ResponseEventsData> = mutableListOf()
    fun setLearnings(learnings: MutableList<ResponseEventsData>) {
        this.learnings = learnings
    }
    fun getLearnings(): MutableList<ResponseEventsData> {
        return learnings
    }

    private var links: MutableList<Links> = mutableListOf()
    fun setLinks(totalPage: Int) {
        links.clear()
        links.add(Links("", Links.previous, false)) // previous
        for (i in 1..totalPage){
            links.add(Links("", "$i", i == 1))
        }
        links.add(Links("", Links.next, false)) // next
        lastPage = "$totalPage"
    }
    fun getLinks(): MutableList<Links> {
        return links
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

    private var monthId = ""
    fun getMonthId(): String = monthId
    fun setMonthId(mId: String) {
        monthId = mId
    }

    private var abjad = ""
    fun getAbjad(): String = abjad
    fun setAbjad(a: String) {
        abjad = a
    }

    private var _reqOtpResponse: MutableLiveData<Resource<ResponseOtp>> = MutableLiveData()
    fun reqOtpResponse(): MutableLiveData<Resource<ResponseOtp>> = _reqOtpResponse
    private var _reqLearningResponse: MutableLiveData<Resource<ResponseEvents>> = MutableLiveData()
    fun reqLearningResponse(): MutableLiveData<Resource<ResponseEvents>> = _reqLearningResponse

    private var _reqNextLearningResponse: MutableLiveData<Resource<ResponseEvents>> = MutableLiveData()
    fun reqNextLearningResponse(): MutableLiveData<Resource<ResponseEvents>> = _reqNextLearningResponse


    private var nextPage = ""
    fun getNextPage(): String = nextPage
    fun setNextPage(value: String) {
        nextPage = value
    }
    var lastPage = ""
    var currentPage ="1"
    var requesting = false


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

    fun apiGetLearnings(sessionManager: SessionManager?, otp: String) {
        viewModelScope.launch {
            try {
                val parameter = StringBuilder()
                parameter.append("customer_id=${sessionManager?.authData?.code ?: ""}")
                if (getKeyword().isNotEmpty()){
                    parameter.append("&title=${getKeyword()}")
                }
                if (getYear().isNotEmpty()){
                    parameter.append("&year=${getYear()}")
                }
                if (getAbjad().isNotEmpty()){
                    parameter.append("&order_by=title")
                    if (getAbjad() == "A-Z"){
                        parameter.append("&order_type=0") // ASC
                    } else {
                        parameter.append("&order_type=1") // DESC
                    }
                }
                TOKEN = "${HashUtils.hash256Events(parameter.toString())}.${ENV.userKey()}.$otp"
                Log.d("access_token", TOKEN)
                _reqLearningResponse.postValue(Resource.loading(null))
                repository.getEvent(parameter.toString()).let {
                    if (it.isSuccessful){
                        _reqLearningResponse.postValue(Resource.success(it.body()))
                    } else {
                        //
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun apiGetNextLearnings(parameter: String) {
        viewModelScope.launch {
            try {
                _reqNextLearningResponse.postValue(Resource.loading(null))
                repository.getEvent(parameter).let {
                    if (it.isSuccessful){
                        _reqNextLearningResponse.postValue(Resource.success(it.body()))
                    } else {
                        //
                        _reqNextLearningResponse.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}