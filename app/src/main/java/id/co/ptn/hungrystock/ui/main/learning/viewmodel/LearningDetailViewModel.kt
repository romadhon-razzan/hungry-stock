package id.co.ptn.hungrystock.ui.main.learning.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.models.auth.ResponseOtp
import id.co.ptn.hungrystock.models.main.home.ResponseEvents
import id.co.ptn.hungrystock.models.main.home.ResponseEventsData
import id.co.ptn.hungrystock.models.main.home.ResponseEventsRelated
import id.co.ptn.hungrystock.models.main.home.ResponseEventsRelatedData
import id.co.ptn.hungrystock.repositories.EventRepository
import id.co.ptn.hungrystock.utils.HashUtils
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearningDetailViewModel @Inject constructor(private val repository: EventRepository) : BaseViewModel() {

    private val _played = MutableLiveData(false)
    val played: LiveData<Boolean> = _played

    fun setPlayed(value: Boolean) {
        _played.value = value
    }

    private val _title = MutableLiveData("")
    val title: LiveData<String> = _title
    fun setTitle(title: String) {
        _title.value = title
    }

    private val _subTitle = MutableLiveData("")
    val subTitle: LiveData<String> = _subTitle
    fun setSubTitle(subTitle: String) {
        _subTitle.value = subTitle
    }

    private val _loadingReqDetail = MutableLiveData(true)
    val loadingReqDetail: LiveData<Boolean> = _loadingReqDetail
    fun setLoadingReqDetail(value: Boolean) {
        _loadingReqDetail.value = value
    }

    private var eventsRelated: MutableList<ResponseEventsData> = mutableListOf()

    fun setEventsRelated(learnings: MutableList<ResponseEventsData>) {
        this.eventsRelated = learnings
    }

    fun getEventsRelated(): MutableList<ResponseEventsData> {
        return eventsRelated
    }

    private var _reqOtpResponse: MutableLiveData<Resource<ResponseOtp>> = MutableLiveData()
    fun reqOtpResponse(): MutableLiveData<Resource<ResponseOtp>> = _reqOtpResponse
    private var _reqEventsRelatedResponse: MutableLiveData<Resource<ResponseEventsRelated>> = MutableLiveData()
    fun reqEventsRelatedResponse(): MutableLiveData<Resource<ResponseEventsRelated>> = _reqEventsRelatedResponse


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
                        _reqOtpResponse.postValue(Resource.error(it.body()?.message ?: "", null))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
    fun apiGetEventsRelated(id: String?, otp: String) {
        viewModelScope.launch {
            try {
                val parameter = StringBuilder()
                parameter.append("id=${id ?: ""}")
                TOKEN = "${HashUtils.hash256EventsRelated(parameter.toString())}.${ENV.userKey()}.$otp"
                Log.d("access_token", TOKEN)
                _reqEventsRelatedResponse.postValue(Resource.loading(null))
                repository.getEventRelated(parameter.toString()).let {
                    if (it.isSuccessful){
                        _reqEventsRelatedResponse.postValue(Resource.success(it.body()))
                    } else {
                        _reqEventsRelatedResponse.postValue(Resource.error( "", null))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}