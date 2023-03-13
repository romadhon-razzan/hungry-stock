package id.co.ptn.hungrystock.ui.general.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.models.auth.ResponseAuthV2
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AppRepository): BaseViewModel() {

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
    fun apiAuth() {
        viewModelScope.launch {
            try {
                _reqAuthResponse.postValue(Resource.loading(null))
                repository.authV2(_username.value,_password.value).let {
                    if (it.isSuccessful){
                        _reqAuthResponse.postValue(Resource.success(it.body()))
                    } else {
//                        val type = object : TypeToken<ResponseAuth>() {}.type
//                        var errorResponse: ResponseAuth? = null
//                        try {
//                            errorResponse = Gson().fromJson(it.errorBody()?.charStream(), type)
//                        } catch(e: Exception) {
//                            e.printStackTrace()
//                        }
//                        _reqAuthResponse.postValue(Resource.error(it.errorBody().toString(), errorResponse))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}