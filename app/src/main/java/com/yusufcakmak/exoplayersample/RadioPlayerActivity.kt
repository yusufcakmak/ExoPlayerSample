package com.yusufcakmak.exoplayersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.yusufcakmak.exoplayersample.databinding.ActivityRadioPlayerBinding

class RadioPlayerActivity : AppCompatActivity() {

    private lateinit var simpleExoPlayer: ExoPlayer

    private lateinit var binding: ActivityRadioPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRadioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareMediaPlayer()
        initListeners()
    }

    private fun prepareMediaPlayer() {

        val mediaDataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(this)

        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(MediaItem.fromUri(RADIO_URL))

        val mediaSourceFactory: MediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)

        simpleExoPlayer = ExoPlayer.Builder(this)
                .setMediaSourceFactory(mediaSourceFactory)
                .build()

        simpleExoPlayer.addMediaSource(mediaSource)
        simpleExoPlayer.prepare()
    }

    private fun initListeners() {
        binding.btnStart.setOnClickListener {
            simpleExoPlayer.playWhenReady = true
        }

        binding.btnStop.setOnClickListener {
            simpleExoPlayer.playWhenReady = false
        }
    }

    override fun onDestroy() {
        simpleExoPlayer.playWhenReady = false
        super.onDestroy()
    }

    companion object {
        const val RADIO_URL = "http://kastos.cdnstream.com/1345_32"
    }
}
