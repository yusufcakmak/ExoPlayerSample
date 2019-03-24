package com.yusufcakmak.exoplayersample

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_radio_player.*


class RadioPlayerActivity : AppCompatActivity() {

    private lateinit var player: SimpleExoPlayer
    private lateinit var mediaSource: MediaSource
    private lateinit var dataSourceFactory: DefaultDataSourceFactory
    private val BANDWIDTH_METER = DefaultBandwidthMeter()

    private val trackSelectionFactory: TrackSelection.Factory = AdaptiveTrackSelection.Factory()
    private val trackSelector: TrackSelector = DefaultTrackSelector(trackSelectionFactory)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radio_player)

        dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "exoPlayerSample"), BANDWIDTH_METER)

        mediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(RADIO_URL))

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

        with(player) {
            prepare(mediaSource)
            btnStart.setOnClickListener {
                playWhenReady = true
            }

            btnStop.setOnClickListener {
                playWhenReady = false
            }
        }

    }

    override fun onDestroy() {
        player.playWhenReady = false
        super.onDestroy()
    }

    companion object {
        const val RADIO_URL = "http://uk7.internet-radio.com:8226"
    }
}
