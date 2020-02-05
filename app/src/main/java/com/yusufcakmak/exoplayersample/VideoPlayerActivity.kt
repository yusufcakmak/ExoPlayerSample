package com.yusufcakmak.exoplayersample

import android.app.Activity
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoPlayerActivity : Activity() {

    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private lateinit var mediaDataSourceFactory: DataSource.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
    }

    private fun initializePlayer() {

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this)

        mediaDataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"))

        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(Uri.parse(STREAM_URL))

        simpleExoPlayer.prepare(mediaSource, false, false)
        simpleExoPlayer.playWhenReady = true

        playerView.setShutterBackgroundColor(Color.TRANSPARENT)
        playerView.player = simpleExoPlayer
        playerView.requestFocus()
    }

    private fun releasePlayer() {
        simpleExoPlayer.release()
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

    companion object {
        const val STREAM_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
    }
}