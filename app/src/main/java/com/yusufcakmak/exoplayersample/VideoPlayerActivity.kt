package com.yusufcakmak.exoplayersample

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.TrackSelectionView
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoPlayerActivity : Activity(), View.OnClickListener {

    companion object {
        private const val KEY_PLAY_WHEN_READY = "play_when_ready"
        private const val KEY_WINDOW = "window"
        private const val KEY_POSITION = "position"
    }

    private lateinit var player: SimpleExoPlayer
    private lateinit var mediaDataSourceFactory: DataSource.Factory
    
    private var trackSelector: DefaultTrackSelector? = null
    private var lastSeenTrackGroupArray: TrackGroupArray? = null
    private var playWhenReady: Boolean = false
    private var currentWindow: Int = 0
    private var playbackPosition: Long = 0
    private val ivHideControllerButton: ImageView by lazy { findViewById<ImageView>(R.id.exo_controller) }
    private val ivSettings: ImageView by lazy { findViewById<ImageView>(R.id.settings) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        if (savedInstanceState != null) {

            with(savedInstanceState) {
                playWhenReady = getBoolean(KEY_PLAY_WHEN_READY)
                currentWindow = getInt(KEY_WINDOW)
                playbackPosition = getLong(KEY_POSITION)
            }
        }

    }



    override fun onClick(v: View) {

        if (v.id == R.id.settings) {

            val mappedTrackInfo = trackSelector?.currentMappedTrackInfo

            if (mappedTrackInfo != null) {

                val title = getString(R.string.video)
                val rendererIndex = ivSettings.tag as Int
                val dialogPair = TrackSelectionView.getDialog(this, title, trackSelector, rendererIndex)
                dialogPair.second.setShowDisableOption(false)
                dialogPair.second.setAllowAdaptiveSelections(true)
                dialogPair.first.show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        updateStartPosition()

        with(outState) {
            putBoolean(KEY_PLAY_WHEN_READY, playWhenReady)
            putInt(KEY_WINDOW, currentWindow)
            putLong(KEY_POSITION, playbackPosition)
        }

        super.onSaveInstanceState(outState)
    }


    private fun initializePlayer() {
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory()
        trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        mediaDataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"))

        val haveStartPosition = currentWindow != C.INDEX_UNSET
        val mediaSource = ExtractorMediaSource.Factory(mediaDataSourceFactory)
                .createMediaSource(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))

        playerView.requestFocus()

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

        playerView.player = player


        with(player) {
            prepare(mediaSource, !haveStartPosition, false)
            playWhenReady = true
        }

        updateButtonVisibilities()

        ivHideControllerButton.setOnClickListener { playerView.hideController() }

        lastSeenTrackGroupArray = null
    }


    private fun updateStartPosition() {

        with(player) {
            playbackPosition = currentPosition
            currentWindow = currentWindowIndex
            playWhenReady = playWhenReady
        }
    }

    private fun updateButtonVisibilities() {
        ivSettings.visibility = View.GONE

        val mappedTrackInfo = trackSelector!!.currentMappedTrackInfo ?: return

        for (i in 0 until mappedTrackInfo.rendererCount) {
            val trackGroups = mappedTrackInfo.getTrackGroups(i)
            if (trackGroups.length != 0) {
                if (player.getRendererType(i) == C.TRACK_TYPE_VIDEO) {
                    ivSettings.visibility = View.VISIBLE
                    ivSettings.setOnClickListener(this)
                    ivSettings.tag = i
                }
            }
        }
    }


    private fun releasePlayer() {
        updateStartPosition()
        player.release()
        trackSelector = null
    }

    public override fun onStart() {
        super.onStart()

        if (Util.SDK_INT > 23) initializePlayer()
    }

    public override fun onResume() {
        super.onResume()

        if (Util.SDK_INT <= 23) initializePlayer()
    }

    public override fun onPause() {
        super.onPause()

        if (Util.SDK_INT <= 23) releasePlayer()
    }

    public override fun onStop() {
        super.onStop()

        if (Util.SDK_INT > 23) releasePlayer()
    }

}