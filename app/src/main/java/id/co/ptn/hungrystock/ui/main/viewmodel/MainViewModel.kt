package id.co.ptn.hungrystock.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun homePressed() {
        _homePressed.value = true
        _learningPressed.value = false
        _researchPressed.value = false
        _enginePressed.value = false
        _hsroPressed.value = false
    }

    fun learningPressed() {
        _homePressed.value = false
        _learningPressed.value = true
        _researchPressed.value = false
        _enginePressed.value = false
        _hsroPressed.value = false
    }

    fun researchPressed() {
        _homePressed.value = false
        _learningPressed.value = false
        _researchPressed.value = true
        _enginePressed.value = false
        _hsroPressed.value = false
    }

    fun enginePressed() {
        _homePressed.value = false
        _learningPressed.value = false
        _researchPressed.value = false
        _enginePressed.value = true
        _hsroPressed.value = false
    }

    fun hsroPressed() {
        _homePressed.value = false
        _learningPressed.value = false
        _researchPressed.value = false
        _enginePressed.value = false
        _hsroPressed.value = true
    }
}