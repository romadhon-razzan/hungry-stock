package id.co.ptn.hungrystock.ui.main.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.repositories.AppRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val appRepository: AppRepository): BaseViewModel() {

    private val _homePressed = MutableLiveData(true)
    private val _learningPressed = MutableLiveData(false)
    private val _researchPressed = MutableLiveData(false)
    private val _enginePressed = MutableLiveData(false)
    private val _hsroPressed = MutableLiveData(false)

    val homePressed: LiveData<Boolean> = _homePressed
    val learningPressed: LiveData<Boolean> = _learningPressed
    val researchPressed: LiveData<Boolean> = _researchPressed
    val enginePressed: LiveData<Boolean> = _enginePressed
    val hsroPressed: LiveData<Boolean> = _hsroPressed

    private val _title = MutableLiveData("")
    val title: LiveData<String> = _title


    fun homePressed(title: String) {
        _title.value = title
        _homePressed.value = true
        _learningPressed.value = false
        _researchPressed.value = false
        _enginePressed.value = false
        _hsroPressed.value = false
    }

    fun learningPressed(title: String) {
        _title.value = title
        _homePressed.value = false
        _learningPressed.value = true
        _researchPressed.value = false
        _enginePressed.value = false
        _hsroPressed.value = false
    }

    fun researchPressed(title: String) {
        _title.value = title
        _homePressed.value = false
        _learningPressed.value = false
        _researchPressed.value = true
        _enginePressed.value = false
        _hsroPressed.value = false
    }

    fun enginePressed(title: String) {
        _title.value = title
        _homePressed.value = false
        _learningPressed.value = false
        _researchPressed.value = false
        _enginePressed.value = true
        _hsroPressed.value = false
    }

    fun hsroPressed(title: String) {
        _title.value = title
        _homePressed.value = false
        _learningPressed.value = false
        _researchPressed.value = false
        _enginePressed.value = false
        _hsroPressed.value = true
    }
}