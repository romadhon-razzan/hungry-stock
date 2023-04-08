package id.co.ptn.hungrystock.ui.general.view_model

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.models.auth.ResponseAuthV2
import id.co.ptn.hungrystock.models.auth.ResponseOtp
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.utils.DeviceUtils
import id.co.ptn.hungrystock.utils.HashUtils
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AppRepository): BaseViewModel() {
    private var _reqOtpResponse: MutableLiveData<Resource<ResponseOtp>> = MutableLiveData()
    fun reqOtpResponse(): MutableLiveData<Resource<ResponseOtp>> = _reqOtpResponse

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _username

//    private var _reqAuthResponse: MutableLiveData<Resource<ResponseAuth>> = MutableLiveData()
//    fun reqAuthResponse(): MutableLiveData<Resource<ResponseAuth>> = _reqAuthResponse
    private var _reqAuthResponse: MutableLiveData<Resource<ResponseAuthV2>> = MutableLiveData()
    fun reqAuthResponse(): MutableLiveData<Resource<ResponseAuthV2>> = _reqAuthResponse

    fun setUsername(userName: String) {
        viewModelScope.launch {
            _username.value = userName
        }
    }

    fun setPassword(password: String) {
        viewModelScope.launch {
            _password.value = password
        }
    }

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
    fun apiAuth(activity: Activity) {
        viewModelScope.launch {
            try {
                _reqAuthResponse.postValue(Resource.loading(null))
                repository.authV2(_username.value,_password.value, DeviceUtils.getDeviceId(activity)).let {
                    if (it.isSuccessful){
                        _reqAuthResponse.postValue(Resource.success(it.body()))
                    } else {
                        val type = object : TypeToken<ResponseAuthV2>() {}.type
                        val errorResponse: ResponseAuthV2?
                        try {
                            errorResponse = Gson().fromJson(it.errorBody()?.charStream(), type)
                            errorResponse?.failed_data?.forEach { failedData ->
                            _reqAuthResponse.postValue(Resource.error(failedData.message ?: "", null))
                            return@forEach
                            }
                        } catch(e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}