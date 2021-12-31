package id.co.ptn.hungrystock.ui.main.learning.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.models.main.learning.Learning
import id.co.ptn.hungrystock.repositories.AppRepository
import javax.inject.Inject

@HiltViewModel
class LearningDetailViewModel @Inject constructor(private val appRepository: AppRepository) : BaseViewModel() {

    private val _title = MutableLiveData("")
    private val _subTitle = MutableLiveData("")
    private val _description = MutableLiveData("")

    val title: LiveData<String> = _title
    val subTitle: LiveData<String> = _subTitle
    val description: LiveData<String> = _description

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setSubTitle(subTitle: String) {
        _subTitle.value = subTitle
    }

    fun setDescription(description: String) {
        _description.value = description
    }

    private var learnings: MutableList<Learning> = mutableListOf()

    fun setLearnings(learnings: MutableList<Learning>) {
        this.learnings = learnings
    }

    fun getLearnings(): MutableList<Learning> {
        return learnings
    }
}