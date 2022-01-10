package id.co.ptn.hungrystock.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.repositories.AppRepository
import javax.inject.Inject

@HiltViewModel
class HsroDetailViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

}