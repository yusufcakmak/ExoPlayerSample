package com.yusufcakmak.exoplayersample

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
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


    private var startButton: Button? = null
    private var stopButton: Button? = null

    private var player: SimpleExoPlayer? = null
    private var bandwidthMeter: BandwidthMeter? = null
    private var extractorsFactory: ExtractorsFactory? = null
    private var trackSelectionFactory: TrackSelection.Factory? = null
    private var trackSelector: TrackSelector? = null
    private var defaultBandwidthMeter: DefaultBandwidthMeter? = null
    private var dataSourceFactory: DataSource.Factory? = null
    private var mediaSource: MediaSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radio_player)

        startButton = findViewById<View>(R.id.startButton) as Button
        stopButton = findViewById<View>(R.id.stopButton) as Button

        bandwidthMeter = DefaultBandwidthMeter()
        extractorsFactory = DefaultExtractorsFactory()

        trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)

        trackSelector = DefaultTrackSelector(trackSelectionFactory)

        /*        dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "mediaPlayerSample"),
                (TransferListener<? super DataSource>) bandwidthMeter);*/

        defaultBandwidthMeter = DefaultBandwidthMeter()
        dataSourceFactory = DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "mediaPlayerSample"), defaultBandwidthMeter)


        mediaSource = ExtractorMediaSource(Uri.parse("http://radio.compinche.net:7662/gypsy.ogg"), dataSourceFactory, extractorsFactory, null, null)

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)


        player!!.prepare(mediaSource)


        startButton!!.setOnClickListener { player!!.playWhenReady = true }

        stopButton!!.setOnClickListener { player!!.playWhenReady = false }

    }

    override fun onDestroy() {
        super.onDestroy()
        player!!.playWhenReady = false
    }
}
