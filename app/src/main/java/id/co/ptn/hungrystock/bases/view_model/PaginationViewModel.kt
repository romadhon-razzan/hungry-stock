package id.co.ptn.hungrystock.bases.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.models.Links
import id.co.ptn.hungrystock.models.auth.ResponseOtp
import id.co.ptn.hungrystock.repositories.EventRepository
import id.co.ptn.hungrystock.utils.Resource
import javax.inject.Inject

@HiltViewModel
class PaginationViewModel @Inject constructor(private val repository: EventRepository) : BaseViewModel() {

    private var links: MutableList<Links> = mutableListOf()
    fun setLinks(totalPage: Int) {
        _singlePage.postValue(totalPage == 1)
        links.clear()
        links.add(Links("", Links.previous, false)) // previous
        for (i in 1..totalPage){
            links.add(Links("", "$i", i == 1))
        }
        links.add(Links("", Links.next, false)) // next
        lastPage = "$totalPage"
    }
    fun getLinks(): MutableList<Links> {
        return links
    }

    private var _reqOtpResponse: MutableLiveData<Resource<ResponseOtp>> = MutableLiveData()
    fun reqOtpResponse(): MutableLiveData<Resource<ResponseOtp>> = _reqOtpResponse


    private var nextPage = ""
    fun getNextPage(): String = nextPage
    fun setNextPage(value: String) {
        nextPage = value
    }
    var lastPage = ""
    var currentPage ="1"
    var requesting = false
    private val _singlePage = MutableLiveData(false)
    val singlePage: LiveData<Boolean> = _singlePage
}