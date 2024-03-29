package id.co.ptn.hungrystock.ui.profile.view_model

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.models.auth.ResponseAuth
import id.co.ptn.hungrystock.models.auth.ResponseOtp
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.utils.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: AppRepository): BaseViewModel() {

    private val _userId = MutableStateFlow("")
    val userId: StateFlow<String> = _userId
    fun setUserId(userId: String) {
        viewModelScope.launch {
            _userId.value = userId
        }
    }

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username
    fun setUsername(userName: String) {
        viewModelScope.launch {
            _username.value = userName
        }
    }

    private val _validDate = MutableStateFlow("")
    val validDate: StateFlow<String> = _validDate
    fun setValidDate(validDate: Long) {
        viewModelScope.launch {
            val finalDate: String = getDateMMMMddyyyy(validDate*1000)
            _validDate.value = finalDate
        }
    }

    private val _joinDate = MutableStateFlow("")
    val joinDate: StateFlow<String> = _joinDate
    fun setJoinDate(joinDate: Long) {
        viewModelScope.launch {
            val finalDate: String = getDateMMMMddyyyy(joinDate*1000)
            _joinDate.value = finalDate
        }
    }

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    fun setEmail(email: String) {
        viewModelScope.launch {
            _email.value = email
        }
    }

    private val _noWa = MutableStateFlow("")
    val noWa: StateFlow<String> = _noWa
    fun setNoWa(noWa: String) {
        viewModelScope.launch {
            _noWa.value = noWa
        }
    }

    private var _reqOtpResponse: MutableLiveData<Resource<ResponseOtp>> = MutableLiveData()
    fun reqOtpResponse(): MutableLiveData<Resource<ResponseOtp>> = _reqOtpResponse

    private var _reqAuthResponse: MutableLiveData<Resource<ResponseAuth>> = MutableLiveData()
    fun reqAuthResponse(): MutableLiveData<Resource<ResponseAuth>> = _reqAuthResponse


    fun apiSave() {
        viewModelScope.launch {
            try {
//                _reqAuthResponse.postValue(Resource.loading(null))
//                repository.auth(_username.value,_password.value).let {
//                    if (it.isSuccessful){
//                        _reqAuthResponse.postValue(Resource.success(it.body()))
//                    } else {
//                        val type = object : TypeToken<ResponseAuth>() {}.type
//                        var errorResponse: ResponseAuth? = null
//                        try {
//                            errorResponse = Gson().fromJson(it.errorBody()?.charStream(), type)
//                        } catch(e: Exception) {
//                            e.printStackTrace()
//                        }
//                        _reqAuthResponse.postValue(Resource.error(it.errorBody().toString(), errorResponse))
//                    }
//                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

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

}