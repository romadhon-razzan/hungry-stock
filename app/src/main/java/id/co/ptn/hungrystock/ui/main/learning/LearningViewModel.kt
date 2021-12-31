package id.co.ptn.hungrystock.ui.main.learning

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.models.main.learning.Learning
import id.co.ptn.hungrystock.repositories.AppRepository
import javax.inject.Inject

@HiltViewModel
class LearningViewModel @Inject constructor(private val appRepository: AppRepository) : BaseViewModel() {
    private var learnings: MutableList<Learning> = mutableListOf()

    fun setLearnings(learnings: MutableList<Learning>) {
        this.learnings = learnings
    }

    fun getLearnings(): MutableList<Learning> {
        return learnings
    }
}