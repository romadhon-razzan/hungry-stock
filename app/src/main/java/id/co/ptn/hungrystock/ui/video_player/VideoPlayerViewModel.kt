package id.co.ptn.hungrystock.ui.video_player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.ptn.hungrystock.bases.BaseViewModel
import id.co.ptn.hungrystock.repositories.AppRepository
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(private val appRepository: AppRepository): BaseViewModel() {

    private val _playBackPosition = MutableLiveData<Long>(0)

    val playBackPosition: LiveData<Long> = _playBackPosition

    fun setPlayBackPosition(value: Long) {
        _playBackPosition.value = value
    }


}