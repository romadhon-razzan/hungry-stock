package id.co.ptn.hungrystock.ui.main.learning.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.models.main.home.ResponseEvent
import id.co.ptn.hungrystock.models.main.learning.Learning
import id.co.ptn.hungrystock.models.main.learning.ResponseLearningDetail
import id.co.ptn.hungrystock.models.main.learning.SimiliarLearnings
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearningDetailViewModel @Inject constructor(private val repository: AppRepository) : BaseViewModel() {

    private val _played = MutableLiveData(false)
    val played: LiveData<Boolean> = _played

    fun setPlayed(value: Boolean) {
        _played.value = value
    }

    private val _title = MutableLiveData("")
    val title: LiveData<String> = _title
    fun setTitle(title: String) {
        _title.value = title
    }

    private val _subTitle = MutableLiveData("")
    val subTitle: LiveData<String> = _subTitle
    fun setSubTitle(subTitle: String) {
        _subTitle.value = subTitle
    }

    private val _loadingReqDetail = MutableLiveData(true)
    val loadingReqDetail: LiveData<Boolean> = _loadingReqDetail
    fun setLoadingReqDetail(value: Boolean) {
        _loadingReqDetail.value = value
    }

    private var learnings: MutableList<SimiliarLearnings> = mutableListOf()

    fun setLearnings(learnings: MutableList<SimiliarLearnings>) {
        this.learnings = learnings
    }

    fun getLearnings(): MutableList<SimiliarLearnings> {
        return learnings
    }

    private var _reqLearningDetailResponse: MutableLiveData<Resource<ResponseLearningDetail>> = MutableLiveData()
    fun reqLearningDetailResponse(): MutableLiveData<Resource<ResponseLearningDetail>> = _reqLearningDetailResponse


    /**
     * Api
     * */

    fun apiGetLearningDetail(s: String) {
        viewModelScope.launch {
            try {
                _reqLearningDetailResponse.postValue(Resource.loading(null))
                repository.getLearningDetail(s).let {
                    if (it.isSuccessful){
                        _reqLearningDetailResponse.postValue(Resource.success(it.body()))
                    } else {
                        val type = object : TypeToken<ResponseLearningDetail>() {}.type
                        var errorResponse: ResponseLearningDetail? = null
                        try {
                            errorResponse = Gson().fromJson(it.errorBody()?.charStream(), type)
                        } catch(e: Exception) {
                            e.printStackTrace()
                        }
                        _reqLearningDetailResponse.postValue(Resource.error(it.errorBody().toString(), errorResponse))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}