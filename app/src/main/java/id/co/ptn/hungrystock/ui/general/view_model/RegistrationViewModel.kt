package id.co.ptn.hungrystock.ui.general.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.models.auth.ResponseAuth
import id.co.ptn.hungrystock.models.registration.ResponseRegister
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val repository: AppRepository): BaseViewModel() {

    private var _reqRegisterResponse: MutableLiveData<Resource<ResponseRegister>> = MutableLiveData()
    fun reqRegisterResponse(): MutableLiveData<Resource<ResponseRegister>> = _reqRegisterResponse

    fun apiRegistration(
        s: RequestBody,
        fp: MultipartBody.Part,
        nl: RequestBody,
        tl: RequestBody,
        nw: RequestBody,
        e: RequestBody,
        p: RequestBody,
        cp: RequestBody
    ) {
        viewModelScope.launch {
            try {
                _reqRegisterResponse.postValue(Resource.loading(null))
                repository.registerStepOne(s,fp, nl, tl, nw, e, p, cp).let {
                    if (it.isSuccessful){
                        _reqRegisterResponse.postValue(Resource.success(it.body()))
                    } else {
                        val type = object : TypeToken<ResponseRegister>() {}.type
                        var errorResponse: ResponseRegister? = null
                        try {
                            errorResponse = Gson().fromJson(it.errorBody()?.charStream(), type)
                        } catch(e: Exception) {
                            e.printStackTrace()
                        }
                        _reqRegisterResponse.postValue(Resource.error(it.errorBody().toString(), errorResponse))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}