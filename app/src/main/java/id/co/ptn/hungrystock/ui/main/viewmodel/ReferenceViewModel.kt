package id.co.ptn.hungrystock.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.models.auth.ResponseOtp
import id.co.ptn.hungrystock.models.reference.ResponseEventCategories
import id.co.ptn.hungrystock.models.reference.ResponseEventCategoriesData
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

    private var _reqResearchCategoriesResponse: MutableLiveData<Resource<ResponseEventCategories>> = MutableLiveData()
    fun reqResearchCategoriesResponse(): MutableLiveData<Resource<ResponseEventCategories>> = _reqResearchCategoriesResponse

    var refEventCategories: MutableList<ResponseEventCategoriesData> = mutableListOf()
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

    fun apiEventCategories() {
        viewModelScope.launch {
            try {
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

    fun apiResearchCategories() {
        viewModelScope.launch {
            try {
                _reqResearchCategoriesResponse.postValue(Resource.loading(null))
                repository.getResearchCategories().let {
                    if (it.isSuccessful){
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