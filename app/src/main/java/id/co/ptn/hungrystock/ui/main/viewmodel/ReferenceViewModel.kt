package id.co.ptn.hungrystock.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.models.auth.ResponseOtp
import id.co.ptn.hungrystock.models.reference.ResponseEventCategories
import id.co.ptn.hungrystock.models.reference.ResponseEventCategoriesData
import id.co.ptn.hungrystock.models.reference.ResponseResearchCategories
import id.co.ptn.hungrystock.models.reference.ResponseResearchCategoriesData
import id.co.ptn.hungrystock.repositories.ReferenceRepository
import id.co.ptn.hungrystock.utils.HashUtils
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReferenceViewModel @Inject constructor(private val repository: ReferenceRepository): BaseViewModel() {
    private var _reqOtpResponse: MutableLiveData<Resource<ResponseOtp>> = MutableLiveData()
    fun reqOtpResponse(): MutableLiveData<Resource<ResponseOtp>> = _reqOtpResponse

    private var _reqEventCategoriesResponse: MutableLiveData<Resource<ResponseEventCategories>> = MutableLiveData()
    fun reqEventCategoriesResponse(): MutableLiveData<Resource<ResponseEventCategories>> = _reqEventCategoriesResponse

    private var _reqResearchCategoriesResponse: MutableLiveData<Resource<ResponseResearchCategories>> = MutableLiveData()
    fun reqResearchCategoriesResponse(): MutableLiveData<Resource<ResponseResearchCategories>> = _reqResearchCategoriesResponse

    var refEventCategories: MutableList<ResponseEventCategoriesData> = mutableListOf()
    var refResearchCategories: MutableList<ResponseResearchCategoriesData> = mutableListOf()
/**
 * Api
 * */
fun apiGetOtp() {
    viewModelScope.launch {
        try {
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

    fun apiEventCategories(otp: String) {
        viewModelScope.launch {
            try {
                TOKEN = "${HashUtils.hash256EventCategories()}.${ENV.userKey()}.$otp"
                _reqEventCategoriesResponse.postValue(Resource.loading(null))
                repository.getEventCategories().let {
                    if (it.isSuccessful){
                        refEventCategories.clear()
                        refEventCategories.addAll(it.body()?.data ?: mutableListOf())
                        _reqEventCategoriesResponse.postValue(Resource.success(it.body()))
                    } else {
                        //
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun apiResearchCategories(otp: String) {
        viewModelScope.launch {
            try {
                TOKEN = "${HashUtils.hash256ResearchCategories()}.${ENV.userKey()}.$otp"
                _reqResearchCategoriesResponse.postValue(Resource.loading(null))
                repository.getResearchCategories().let {
                    if (it.isSuccessful){
                        refResearchCategories.clear()
                        refResearchCategories.addAll(it.body()?.data ?: mutableListOf())
                        _reqResearchCategoriesResponse.postValue(Resource.success(it.body()))
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