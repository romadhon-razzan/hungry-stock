package id.co.ptn.hungrystock.ui.onboarding.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.models.onboard.ResponseOnboard
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(private val repository: AppRepository) : BaseViewModel() {

    private val _title = MutableLiveData("")
    val title: LiveData<String> = _title
    fun setTitle(title: String) {
        _title.value = title
    }

    private val _date = MutableLiveData("")
    val date: LiveData<String> = _date
    fun setDate(date: String) {
        _date.value = date
    }

    private val _speaker = MutableLiveData("")
    val speaker: LiveData<String> = _speaker
    fun setSpeaker(speaker: String) {
        _speaker.value = speaker
    }

    private val _content = MutableLiveData("")
    val content: LiveData<String> = _content
    fun setContent(content: String) {
        _content.value = content
    }

    private var _reqOnboardResponse: MutableLiveData<Resource<ResponseOnboard>> = MutableLiveData()
    fun reqOnboardResponse(): MutableLiveData<Resource<ResponseOnboard>> = _reqOnboardResponse


    /**
     * Api
     * */

    fun apiGetOnboard() {
        viewModelScope.launch {
            try {
                _reqOnboardResponse.postValue(Resource.loading(null))
                repository.getOnBoard().let {
                    if (it.isSuccessful){
                        _reqOnboardResponse.postValue(Resource.success(it.body()))
                    } else {
                        val type = object : TypeToken<ResponseOnboard>() {}.type
                        var errorResponse: ResponseOnboard? = null
                        try {
                            errorResponse = Gson().fromJson(it.errorBody()?.charStream(), type)
                        } catch(e: Exception) {
                            e.printStackTrace()
                        }
                        _reqOnboardResponse.postValue(Resource.error(it.errorBody().toString(), errorResponse))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}