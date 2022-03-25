package id.co.ptn.hungrystock.ui.general.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.models.auth.ResponseAuth
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

    private var _reqAuthResponse: MutableLiveData<Resource<ResponseAuth>> = MutableLiveData()
    fun reqAuthResponse(): MutableLiveData<Resource<ResponseAuth>> = _reqAuthResponse

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

    fun apiAuth() {
        viewModelScope.launch {
            try {
                _reqAuthResponse.postValue(Resource.loading(null))
                repository.auth(_username.value,_password.value).let {
                    if (it.isSuccessful){
                        _reqAuthResponse.postValue(Resource.success(it.body()))
                    } else _reqAuthResponse.postValue(Resource.error(it.errorBody().toString(), null))
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}