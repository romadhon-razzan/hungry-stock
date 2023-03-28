package id.co.ptn.hungrystock.ui.general.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.models.auth.ResponseAuth
import id.co.ptn.hungrystock.models.auth.ResponseAuthV2
import id.co.ptn.hungrystock.models.auth.ResponseOtp
import id.co.ptn.hungrystock.models.password.ResponsePassword
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.utils.HashUtils
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val repository: AppRepository): BaseViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email


    private var _reqResetPasswordResponse: MutableLiveData<Resource<ResponsePassword>> = MutableLiveData()
    fun reqResetPasswordResponse(): MutableLiveData<Resource<ResponsePassword>> = _reqResetPasswordResponse

    fun setEmail(email: String) {
        viewModelScope.launch {
            _email.value = email
        }
    }

    private var _reqOtpResponse: MutableLiveData<Resource<ResponseOtp>> = MutableLiveData()
    fun reqOtpResponse(): MutableLiveData<Resource<ResponseOtp>> = _reqOtpResponse

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
    fun apiResetPassword(otp: String) {
        viewModelScope.launch {
            try {
                TOKEN = "${HashUtils.hash256ForgotPassword()}.${ENV.userKey()}.$otp"
                _reqResetPasswordResponse.postValue(Resource.loading(null))

                val jsonObject = JSONObject()
                jsonObject.put("email", _email.value)
                val jsonArray = JSONArray(listOf(jsonObject))
                val jsonArrayString = jsonArray.toString()
                val requestBody = jsonArrayString.toRequestBody("application/json".toMediaTypeOrNull())

                repository.forgotPassword(requestBody).let {
                    if (it.isSuccessful){
                        _reqResetPasswordResponse.postValue(Resource.success(it.body()))
                    } else {
                        val type = object : TypeToken<ResponsePassword>() {}.type
                        val errorResponse: ResponsePassword?
                        try {
                            errorResponse = Gson().fromJson(it.errorBody()?.charStream(), type)
                            errorResponse?.failed_data?.forEach { failedData ->
                                failedData.messages.forEach {message ->
                                    _reqResetPasswordResponse.postValue(Resource.error(message, null))
                                    return@forEach
                                }
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