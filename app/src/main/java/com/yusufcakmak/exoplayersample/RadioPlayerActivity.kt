package com.yusufcakmak.exoplayersample

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class RadioPlayerActivity : AppCompatActivity() {


    private val startButton: Button by lazy { findViewById<Button>(R.id.startButton) }
    private val stopButton: Button by lazy { findViewById<Button>(R.id.stopButton) }

    private lateinit var player: SimpleExoPlayer
    private lateinit var dataSourceFactory: DataSource.Factory
    private lateinit var mediaSource: MediaSource

    private val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
    private val extractorsFactory: ExtractorsFactory = DefaultExtractorsFactory()
    private val trackSelectionFactory: TrackSelection.Factory = AdaptiveTrackSelection.Factory(bandwidthMeter)
    private val trackSelector: TrackSelector = DefaultTrackSelector(trackSelectionFactory)
    private val defaultBandwidthMeter: DefaultBandwidthMeter = DefaultBandwidthMeter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radio_player)


        dataSourceFactory = DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "mediaPlayerSample"), defaultBandwidthMeter)

        mediaSource = ExtractorMediaSource(Uri.parse("http://uk7.internet-radio.com:8226"), dataSourceFactory, extractorsFactory, null, null)

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

        with(player) {
            prepare(mediaSource)
            startButton.setOnClickListener { playWhenReady = true }
            stopButton.setOnClickListener { playWhenReady = false }
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        player.playWhenReady = false
    }
}
