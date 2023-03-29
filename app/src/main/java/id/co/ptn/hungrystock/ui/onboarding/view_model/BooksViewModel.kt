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
import id.co.ptn.hungrystock.models.landing.ResponseBooksData
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.utils.HashUtils
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(private val repository: AppRepository) : BaseViewModel() {
    var books: MutableList<ResponseBooksData> = mutableListOf()

    private var _reqOtpResponse: MutableLiveData<Resource<ResponseOtp>> = MutableLiveData()
    fun reqOtpResponse(): MutableLiveData<Resource<ResponseOtp>> = _reqOtpResponse

    private var _reqBooksResponse: MutableLiveData<Resource<ResponseBooks>> = MutableLiveData()
    fun reqBooksResponse(): MutableLiveData<Resource<ResponseBooks>> = _reqBooksResponse


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

    fun apiGetBooks(otp: String) {
        viewModelScope.launch {
            try {
                TOKEN = "${HashUtils.hash256Books()}.${ENV.userKey()}.$otp"
                Log.d("book_access_token", TOKEN)
                _reqBooksResponse.postValue(Resource.loading(null))
                repository.books().let {
                    if (it.isSuccessful){
                        _reqBooksResponse.postValue(Resource.success(it.body()))
                    } else {
                        _reqBooksResponse.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}