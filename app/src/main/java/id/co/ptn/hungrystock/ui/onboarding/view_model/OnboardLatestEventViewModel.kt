package id.co.ptn.hungrystock.ui.onboarding.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.models.auth.ResponseOtp
import id.co.ptn.hungrystock.models.landing.ResponseBooks
import id.co.ptn.hungrystock.models.main.home.ResponseEvents
import id.co.ptn.hungrystock.models.main.home.ResponseEventsData
import id.co.ptn.hungrystock.models.onboard.ResponseCodeOfConduct
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.repositories.EventRepository
import id.co.ptn.hungrystock.utils.HashUtils
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardLatestEventViewModel @Inject constructor(private val repository: EventRepository) : BaseViewModel() {

    var latestEvent: ResponseEventsData? = null

    private var _reqOtpResponse: MutableLiveData<Resource<ResponseOtp>> = MutableLiveData()
    fun reqOtpResponse(): MutableLiveData<Resource<ResponseOtp>> = _reqOtpResponse

    private var _reqEventsResponse: MutableLiveData<Resource<ResponseEvents>> = MutableLiveData()
    fun reqEventsResponse(): MutableLiveData<Resource<ResponseEvents>> = _reqEventsResponse

    private var _reqCodeOfConductResponse: MutableLiveData<Resource<ResponseCodeOfConduct>> = MutableLiveData()
    fun reqCodeOfConductResponse(): MutableLiveData<Resource<ResponseCodeOfConduct>> = _reqCodeOfConductResponse


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

    fun apiGetLatestEvent(otp: String) {
        viewModelScope.launch {
            try {
                val parameter = StringBuilder()
                parameter.append("&order_by=event_date")
                parameter.append("&order_type=2") // DESC
                TOKEN = "${HashUtils.hash256Events(parameter.toString())}.${ENV.userKey()}.$otp"
                Log.d("event_access_token", TOKEN)
                _reqEventsResponse.postValue(Resource.loading(null))
                repository.getEvent(parameter.toString()).let {
                    if (it.isSuccessful){
                        _reqEventsResponse.postValue(Resource.success(it.body()))
                    } else {
                        _reqEventsResponse.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun apiGetCodeOfConduct(otp: String) {
        viewModelScope.launch {
            try {
                TOKEN = "${HashUtils.hash256CodeOfConduct()}.${ENV.userKey()}.$otp"
                _reqCodeOfConductResponse.postValue(Resource.loading(null))
                repository.codeOfConduct().let {
                    if (it.isSuccessful){
                        _reqCodeOfConductResponse.postValue(Resource.success(it.body()))
                    } else {
                        _reqCodeOfConductResponse.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}