package id.co.ptn.hungrystock.ui.main.research.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.repositories.AppRepository
import javax.inject.Inject

@HiltViewModel
class StockDataViewModel @Inject constructor(val appRepository: AppRepository) : BaseViewModel() {

}