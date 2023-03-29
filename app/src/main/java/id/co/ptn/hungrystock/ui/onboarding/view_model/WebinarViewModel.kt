package id.co.ptn.hungrystock.ui.onboarding.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.models.auth.ResponseOtp
import id.co.ptn.hungrystock.models.landing.ResponseWebinar
import id.co.ptn.hungrystock.models.landing.ResponseWebinarData
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.utils.HashUtils
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebinarViewModel @Inject constructor(private val repository: AppRepository) : BaseViewModel() {

    var webinar : ResponseWebinarData? = null

    private var _reqOtpResponse: MutableLiveData<Resource<ResponseOtp>> = MutableLiveData()
    fun reqOtpResponse(): MutableLiveData<Resource<ResponseOtp>> = _reqOtpResponse

    private var _reqWebinarResponse: MutableLiveData<Resource<ResponseWebinar>> = MutableLiveData()
    fun reqWebinarResponse(): MutableLiveData<Resource<ResponseWebinar>> = _reqWebinarResponse


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
    fun apiGetWebinar(otp: String) {
        viewModelScope.launch {
            try {
                TOKEN = "${HashUtils.hash256Webinar()}.${ENV.userKey()}.$otp"
                Log.d("webinar_access_token", TOKEN)
                _reqWebinarResponse.postValue(Resource.loading(null))
                repository.webinar().let {
                    if (it.isSuccessful){
                        _reqWebinarResponse.postValue(Resource.success(it.body()))
                    } else {
                        _reqWebinarResponse.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}