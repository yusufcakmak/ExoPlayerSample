package com.yusufcakmak.exoplayersample;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.yusufcakmak.exoplayersample.model.VideoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 30/09/2017.
 */

public class PlayListActivity extends Activity {

    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;

    private Timeline.Window window;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private Handler mainHandler;
    private BandwidthMeter bandwidthMeter;
    private List<VideoModel> videos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        createDummyList();
        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
        mainHandler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this,null));
    }

    private void initializePlayer() {
        simpleExoPlayerView.requestFocus();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        simpleExoPlayerView.setPlayer(player);

        MediaSource[] mediaSources = new MediaSource[videos.size()];
        for (int i = 0; i < videos.size(); i++) {
            mediaSources[i] = buildMediaSource(Uri.parse(videos.get(i).videoUrl));
        }
        MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0]
                : new ConcatenatingMediaSource(mediaSources);
        player.prepare(mediaSource);
    }

    public void createDummyList(){
        VideoModel video = new VideoModel();
        for (int i = 0; i < 5; i++) {
            video.videoUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
            videos.add(video);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri, mediaDataSourceFactory, new DefaultExtractorsFactory(),
                mainHandler, null);
    }

    private void releasePlayer() {
        if (player != null) {
            shouldAutoPlay = player.getPlayWhenReady();
            player.release();
            player = null;
            trackSelector = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
}
