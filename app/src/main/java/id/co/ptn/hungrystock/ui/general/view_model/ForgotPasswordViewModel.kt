package id.co.ptn.hungrystock.ui.general.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.models.auth.ResponseAuth
import id.co.ptn.hungrystock.models.password.ResponsePassword
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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

    fun apiResetPassword() {
        viewModelScope.launch {
            try {
                _reqResetPasswordResponse.postValue(Resource.loading(null))
                repository.resetPassword(_email.value).let {
                    if (it.isSuccessful){
                        _reqResetPasswordResponse.postValue(Resource.success(it.body()))
                    } else _reqResetPasswordResponse.postValue(Resource.error(it.errorBody().toString(), null))
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}