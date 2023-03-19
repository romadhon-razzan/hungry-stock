package id.co.ptn.hungrystock.ui.onboarding.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.config.ENV
import id.co.ptn.hungrystock.config.TOKEN
import id.co.ptn.hungrystock.models.auth.ResponseOtp
import id.co.ptn.hungrystock.models.landing.ResponseBooks
import id.co.ptn.hungrystock.models.landing.ResponseWebinar
import id.co.ptn.hungrystock.models.onboard.ResponseOnboard
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.utils.HashUtils
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(private val repository: AppRepository) : BaseViewModel() {

    private val _showPrivacyPolicy = MutableLiveData(false)
    val showPrivacyPolicy: LiveData<Boolean> = _showPrivacyPolicy
    fun setShowPrivacyPolicy(value: Boolean) {
        _showPrivacyPolicy.value = value
    }

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

    /**
     * OnPageViewPager Changed
     * */
    private val _pageLatestEvent = MutableLiveData(false)
    val pageLatestEvent: LiveData<Boolean> = _pageLatestEvent
    fun setPageLatestEvent(selected: Boolean) {
        _pageLatestEvent.postValue(selected)
    }
    private val _pageWebinar = MutableLiveData(false)
    val pageWebinar: LiveData<Boolean> = _pageWebinar
    fun setPageWebinar(selected: Boolean) {
        _pageWebinar.postValue(selected)
    }
    private val _pageBooks = MutableLiveData(false)
    val pageBooks: LiveData<Boolean> = _pageBooks
    fun setPageBooks(selected: Boolean) {
        _pageBooks.postValue(selected)
    }

    private var _reqOtpResponse: MutableLiveData<Resource<ResponseOtp>> = MutableLiveData()
    fun reqOtpResponse(): MutableLiveData<Resource<ResponseOtp>> = _reqOtpResponse

    private var _reqOnboardResponse: MutableLiveData<Resource<ResponseOnboard>> = MutableLiveData()
    fun reqOnboardResponse(): MutableLiveData<Resource<ResponseOnboard>> = _reqOnboardResponse
    private var _reqWebinarResponse: MutableLiveData<Resource<ResponseWebinar>> = MutableLiveData()
    fun reqWebinarResponse(): MutableLiveData<Resource<ResponseWebinar>> = _reqWebinarResponse
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
    fun apiGetWebinar(otp: String) {
        viewModelScope.launch {
            try {
                TOKEN = "${HashUtils.hash256Webinar()}.${ENV.userKey()}.$otp"
                _reqWebinarResponse.postValue(Resource.loading(null))
                repository.webinar().let {
                    if (it.isSuccessful){
                        _reqWebinarResponse.postValue(Resource.success(it.body()))
                    } else {
                        _reqWebinarResponse.postValue(Resource.error(it.errorBody().toString(), null))
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