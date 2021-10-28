package id.co.ptn.hungrystock.ui.privacy_police.view_model

import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.repositories.AppRepository
import javax.inject.Inject

@HiltViewModel
class PrivacyPoliceViewModel @Inject constructor(private val appRepository: AppRepository): BaseViewModel() {


}