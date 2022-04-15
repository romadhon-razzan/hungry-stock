package id.co.ptn.hungrystock.ui.main.learning.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.models.main.learning.Learning
import id.co.ptn.hungrystock.models.main.learning.ResponseLearning
import id.co.ptn.hungrystock.models.main.learning.ResponseLearningDetail
import id.co.ptn.hungrystock.repositories.AppRepository
import id.co.ptn.hungrystock.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearningViewModel @Inject constructor(private val repository: AppRepository) : BaseViewModel() {
    private val _sortingLabel = MutableLiveData("")
    val sortingLabel: LiveData<String> = _sortingLabel
    fun setSortingLabel(title: String) {
        _sortingLabel.value = title
    }

    private var learnings: MutableList<Learning> = mutableListOf()

    fun setLearnings(learnings: MutableList<Learning>) {
        this.learnings = learnings
    }

    fun getLearnings(): MutableList<Learning> {
        return learnings
    }

    private var category = ""
    fun getCategory(): String = category
    fun setCategory(c: String) {
        category = c
    }

    private var year = ""
    fun getYear(): String = year
    fun setYear(y: String) {
        year = y
    }

    private var month = ""
    fun getMonth(): String = month
    fun setMonth(m: String) {
        month = m
    }

    private var abjad = ""
    fun getAbjad(): String = abjad
    fun setAbjad(a: String) {
        abjad = a
    }

    private var _reqLearningResponse: MutableLiveData<Resource<ResponseLearning>> = MutableLiveData()
    fun reqLearningResponse(): MutableLiveData<Resource<ResponseLearning>> = _reqLearningResponse


    /**
     * Api
     * */

    fun apiGetLearnings(k: String, c: String, y: String, m: String, ot: String) {
        viewModelScope.launch {
            try {
                _reqLearningResponse.postValue(Resource.loading(null))
                repository.getLearnings(k, c, y, m, ot).let {
                    if (it.isSuccessful){
                        _reqLearningResponse.postValue(Resource.success(it.body()))
                    } else {
                        val type = object : TypeToken<ResponseLearning>() {}.type
                        var errorResponse: ResponseLearning? = null
                        try {
                            errorResponse = Gson().fromJson(it.errorBody()?.charStream(), type)
                        } catch(e: Exception) {
                            e.printStackTrace()
                        }
                        _reqLearningResponse.postValue(Resource.error(it.errorBody().toString(), errorResponse))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}