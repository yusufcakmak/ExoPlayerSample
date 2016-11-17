package com.yusufcakmak.exoplayersample;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

public class RadioPlayerActivity extends AppCompatActivity {

    private Handler mainHandler;
    private BandwidthMeter bandwidthMeter;
    private TrackSelector trackSelector;
    private TrackSelection.Factory trackSelectionFactory;
    private LoadControl loadControl;
    private SimpleExoPlayer player;
    private DataSource.Factory dataSourceFactory;
    private ExtractorsFactory extractorsFactory;
    private MediaSource mediaSource;

    private String radioUrl = "http://136.243.200.177:9314";

    private Button stopButton;
    private Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_player);

        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);

        mainHandler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();
        loadControl = new DefaultLoadControl();
        extractorsFactory = new DefaultExtractorsFactory();

        trackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);

        trackSelector = new DefaultTrackSelector(mainHandler,
                trackSelectionFactory);


        dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "mediaPlayerSample"),
                (TransferListener<? super DataSource>) bandwidthMeter);


        mediaSource = new ExtractorMediaSource(Uri.parse(radioUrl),
                dataSourceFactory,
                extractorsFactory,
                null,
                null);

        player = ExoPlayerFactory.newSimpleInstance(getApplicationContext(),
                trackSelector,
                loadControl);

        player.prepare(mediaSource);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setPlayWhenReady(true);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setPlayWhenReady(false);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.setPlayWhenReady(false);
    }

}
