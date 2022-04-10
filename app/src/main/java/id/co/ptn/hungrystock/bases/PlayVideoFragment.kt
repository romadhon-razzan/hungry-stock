package id.co.ptn.hungrystock.bases

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.FragmentPlayVideoBinding
import id.co.ptn.hungrystock.ui.video_player.VideoPlayerViewModel
import id.co.ptn.hungrystock.utils.PLAYBACK_POSITION

private const val ARG_URL = "url"
private const val ARG_PLAYBACK_POSITION = "paramPlayBackPosition"

class PlayVideoFragment : BaseFragment(), Player.Listener {

    private var paramUrl: String? = null
    private var paramPlayBackPosition: Long? = 0

    private var binding: FragmentPlayVideoBinding? = null
    private var viewModel: VideoPlayerViewModel? = null
    private lateinit var simpleExoplayer: ExoPlayer
    private var mp4Url = ""

    private val dataSourceFactory: DataSource.Factory by lazy {
        DefaultDataSource.Factory(requireContext())
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String, playbackPosition: Long) =
            PlayVideoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URL, url)
                    putLong(ARG_PLAYBACK_POSITION, playbackPosition)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramUrl = it.getString(ARG_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_play_video, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paramPlayBackPosition?.let {
            Log.d("PLAYBACK", it.toString())
            viewModel?.setPlayBackPosition(it) }
        mp4Url = paramUrl.toString()
        initializePlayer()
        initListener()
    }

    private fun initListener() {
        binding?.btnFullscreen?.setOnClickListener {
            val intent = router.toPlayVideo()
            intent.putExtra("url", mp4Url)
            intent.putExtra("playbackPosition", viewModel?.playBackPosition?.value)
            requireActivity().startActivity(intent)
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun initializePlayer() {
        simpleExoplayer = ExoPlayer.Builder(requireContext()).build()
//        val randomUrl = urlList.random()
        preparePlayer(mp4Url, "")
        binding?.playerView?.player = simpleExoplayer
        viewModel?.playBackPosition?.value?.let { simpleExoplayer.seekTo(it) }
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
        viewModel?.setPlayBackPosition(simpleExoplayer.currentPosition)
        viewModel?.playBackPosition?.value?.let {
            PLAYBACK_POSITION = it.toInt()
        }
        simpleExoplayer.release()
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_BUFFERING) {
            binding?.textView?.visibility = View.GONE
            binding?.progressBar?.visibility = View.VISIBLE
        }
        else if (playbackState == Player.STATE_READY || playbackState == Player.STATE_ENDED) {
            binding?.textView?.visibility = View.VISIBLE
            binding?.progressBar?.visibility = View.INVISIBLE
        }
        else binding?.textView?.visibility = View.GONE
    }


}