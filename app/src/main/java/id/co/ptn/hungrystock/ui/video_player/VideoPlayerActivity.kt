package id.co.ptn.hungrystock.ui.video_player

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.ActivityVideoPlayerBinding

@AndroidEntryPoint
class VideoPlayerActivity : AppCompatActivity(), Player.Listener {
    private lateinit var binding: ActivityVideoPlayerBinding
    private val viewModel: VideoPlayerViewModel by viewModels()
    private lateinit var simpleExoplayer: ExoPlayer
    private var playbackPosition: Long = 0
    private var mp4Url = ""

    private val dataSourceFactory: DataSource.Factory by lazy {
        DefaultDataSource.Factory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        init()
    }

    private fun init() {
        initIntent()
    }

    private fun initIntent() {
        intent?.extras?.let {
            if (it.containsKey("url")){
                mp4Url = it.getString("url","")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun initializePlayer() {
        simpleExoplayer = ExoPlayer.Builder(this).build()
//        val randomUrl = urlList.random()
        preparePlayer(mp4Url, "")
        binding.playerView.player = simpleExoplayer
        viewModel.playBackPosition.value?.let { simpleExoplayer.seekTo(it) }
//        simpleExoplayer.
        simpleExoplayer.playWhenReady = true
        simpleExoplayer.addListener(this)
    }

    private fun buildMediaSource(uri: Uri, type: String): MediaSource {
        return if (type == "dash") {
            DashMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri))
        } else {
            ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri))
        }
    }

    private fun preparePlayer(videoUrl: String, type: String) {
        val uri = Uri.parse(videoUrl)
        val mediaSource = buildMediaSource(uri, type)
        simpleExoplayer.prepare(mediaSource, true, true)
    }

    private fun releasePlayer() {
        playbackPosition = simpleExoplayer.currentPosition
        viewModel.setPlayBackPosition(playbackPosition)
        simpleExoplayer.release()
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_BUFFERING) {
            binding.textView.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }
        else if (playbackState == Player.STATE_READY || playbackState == Player.STATE_ENDED) {
            binding.textView.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
        else binding.textView.visibility = View.GONE
    }


    @Deprecated("Deprecated in Java", ReplaceWith("finish()"))
    override fun onBackPressed() {
        finish()
    }

}