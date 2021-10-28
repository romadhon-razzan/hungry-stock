package id.co.ptn.hungrystock.ui.watchlist.view_model

import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.repositories.AppRepository
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(private val repository: AppRepository): BaseViewModel() {
}