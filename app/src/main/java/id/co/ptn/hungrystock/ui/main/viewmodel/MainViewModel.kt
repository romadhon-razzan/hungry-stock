package id.co.ptn.hungrystock.ui.main.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.core.SessionManager
import id.co.ptn.hungrystock.models.auth.ResponseCheckUserLogin
import id.co.ptn.hungrystock.models.auth.ResponseOtp
import id.co.ptn.hungrystock.models.user.ResponseProfile
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.utils.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: AppRepository): BaseViewModel() {

    private val _homePressed = MutableLiveData(true)
    private val _learningPressed = MutableLiveData(false)
    private val _researchPressed = MutableLiveData(false)
    private val _enginePressed = MutableLiveData(false)
    private val _hsroPressed = MutableLiveData(false)

    val homePressed: LiveData<Boolean> = _homePressed
    val learningPressed: LiveData<Boolean> = _learningPressed
    val researchPressed: LiveData<Boolean> = _researchPressed
    val enginePressed: LiveData<Boolean> = _enginePressed
    val hsroPressed: LiveData<Boolean> = _hsroPressed

    private val _title = MutableLiveData("")
    val title: LiveData<String> = _title


    fun homePressed(title: String) {
        _title.value = title
        _homePressed.value = true
        _learningPressed.value = false
        _researchPressed.value = false
        _enginePressed.value = false
        _hsroPressed.value = false
    }

    fun learningPressed(title: String) {
        _title.value = title
        _homePressed.value = false
        _learningPressed.value = true
        _researchPressed.value = false
        _enginePressed.value = false
        _hsroPressed.value = false
    }

    fun researchPressed(title: String) {
        _title.value = title
        _homePressed.value = false
        _learningPressed.value = false
        _researchPressed.value = true
        _enginePressed.value = false
        _hsroPressed.value = false
    }

    fun enginePressed(title: String) {
        _title.value = title
        _homePressed.value = false
        _learningPressed.value = false
        _researchPressed.value = false
        _enginePressed.value = true
        _hsroPressed.value = false
    }

    fun hsroPressed(title: String) {
        _title.value = title
        _homePressed.value = false
        _learningPressed.value = false
        _researchPressed.value = false
        _enginePressed.value = false
        _hsroPressed.value = true
    }

    private var _reqOtpResponse: MutableLiveData<Resource<ResponseOtp>> = MutableLiveData()
    fun reqOtpResponse(): MutableLiveData<Resource<ResponseOtp>> = _reqOtpResponse

    private var _reqProfileResponse: MutableLiveData<Resource<ResponseProfile>> = MutableLiveData()
    fun reqProfileResponse(): MutableLiveData<Resource<ResponseProfile>> = _reqProfileResponse
    private var _reqUserLoginResponse: MutableLiveData<Resource<ResponseCheckUserLogin>> = MutableLiveData()
    fun reqUserLoginResponse(): MutableLiveData<Resource<ResponseCheckUserLogin>> = _reqUserLoginResponse

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

    fun apiGetProfile(sessionManager: SessionManager?, otp: String) {
        viewModelScope.launch {
            try {
                val parameter = "code=${sessionManager?.authData?.code ?: ""}"
                TOKEN = "${HashUtils.hash256Profile(parameter)}.${ ENV.userKey()}.$otp"
                _reqProfileResponse.postValue(Resource.loading(null))
                repository.profile(parameter).let {
                    if (it.isSuccessful){
                        _reqProfileResponse.postValue(Resource.success(it.body()))
                    } else {
                        //
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun apiCheckUserLogin(sessionManager: SessionManager?, otp: String, activity: Activity) {
        viewModelScope.launch {
            try {
                val parameter = "customer_id=${sessionManager?.authData?.code ?: ""}&deviceid=${DeviceUtils.getDeviceId(activity)}"
                TOKEN = "${HashUtils.hash256CheckUserLogin(parameter)}.${ ENV.userKey()}.$otp"
                _reqProfileResponse.postValue(Resource.loading(null))
                repository.checkUserLogin(parameter).let {
                    if (it.isSuccessful){
                        _reqUserLoginResponse.postValue(Resource.success(it.body()))
                    } else {
                        _reqUserLoginResponse.postValue(Resource.error("",null))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}