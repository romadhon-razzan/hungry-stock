package id.co.ptn.hungrystock.bases.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.models.Links
import id.co.ptn.hungrystock.models.auth.ResponseOtp
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.repositories.EventRepository
import id.co.ptn.hungrystock.utils.Resource
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(private val repository: AppRepository): BaseViewModel() {

    var _onPageStarted: MutableLiveData<String> = MutableLiveData()
    fun onPageStarted(): MutableLiveData<String> = _onPageStarted

}