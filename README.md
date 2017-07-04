# ExoPlayer2 Sample Project

You can play videos and musics with ExoPlayer. This demo shows you how to use ExoPlayer. You can play mp3s or radio stream links with RadioActivity.

This repo updated for r2.4.2 version. I will update the documentation soon for version r2.4.2.

The following document is still valid for version r2.0.4.

```
        player = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector, loadControl);

        bandwidthMeter = new DefaultBandwidthMeter();
        dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
        extractorsFactory = new DefaultExtractorsFactory();
        mediaSource = new ExtractorMediaSource(Uri.parse(radioUrl), dataSourceFactory, extractorsFactory, null, null);
        player.prepare(mediaSource);
```

You can play and pause audio with this method setPlayWhenReady(boolean);

play audio 
```
player.setPlayWhenReady(true);
```

pause audio
```
player.setPlayWhenReady(false);
```

### Playing Video

We need to SimpleExoPlayerView for playing videos.

```
        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
        simpleExoPlayerView.requestFocus();

        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, new DefaultLoadControl(),
                null, false);
        simpleExoPlayerView.setPlayer(player);
        player.setPlayWhenReady(shouldAutoPlay);
        MediaSource mediaSource = new HlsMediaSource(Uri.parse("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"),
                mediaDataSourceFactory, mainHandler, null);
        player.prepare(mediaSource);
```

 If you use hls or dash formats, you need to use HlsMediaSource as a MediaSource.
 
 For hls

```
MediaSource mediaSource = new HlsMediaSource(Uri.parse("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"),
                mediaDataSourceFactory, mainHandler, null);
player.prepare(mediaSource);
```

For dash

```
MediaSource mediaSource = new DashMediaSource(uri, buildDataSourceFactory(false),
            new DefaultDashChunkSource.Factory(mediaDataSourceFactory), mainHandler, null);
player.prepare(mediaSource);
```

If you use mp4, flv, mkv or other formats, you need to use ExtractorMediaSource as a MediaSource. Also we are using the ExtractorMediaSource for playing audio formats.Supported formats; mkv, mp4, mp3, ogg, ac3, flv, wav, flac.

```
extractorsFactory = new DefaultExtractorsFactory();
MediaSource mediaSource = new ExtractorMediaSource(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"),
                mediaDataSourceFactory, extractorsFactory, null, null);
player.prepare(mediaSource);
```

### Custom VideoPlayer View and PlaybackControls 

I created CustomExoPlayerView and CustomPlaybackControlView classes. You can use custom playback controls easily. 

simpleExoPlayerView = (CustomExoPlayerView) findViewById(R.id.custom_player_view);

```
   <com.yusufcakmak.exoplayersample.customview.CustomExoPlayerView
        android:id="@+id/custom_player_view"
        android:focusable="true"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

If you want to use custom playback, you need to 2 xml file in layout folder. 

exo_simple_player_view.xml
```
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_height="match_parent"
    android:layout_width="match_parent">

    <com.google.android.exoplayer2.ui.AspectRatioFrameLayout android:id="@+id/video_frame"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_gravity="center">

        <View android:id="@+id/shutter"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@android:color/black"/>

        <com.google.android.exoplayer2.ui.SubtitleView android:id="@+id/subtitles"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>

    </com.google.android.exoplayer2.ui.AspectRatioFrameLayout>

    <com.yusufcakmak.exoplayersample.customview.CustomPlaybackControlView
        android:id="@+id/control"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</FrameLayout>
```

exo_playback_control_view.xml

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="56dp"
    android:layout_gravity="bottom"
    android:background="#fff"
    android:orientation="vertical"
    android:layoutDirection="ltr">

        <TextView
            android:id="@+id/time_current"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textSize="14sp"
            android:textStyle="bold"
            android:layout_centerVertical="true"
            android:paddingStart="16dp"
            android:text="00:00"
            android:paddingEnd="4dp"
            android:textColor="#000"/>


        <SeekBar
            android:id="@+id/mediacontroller_progress"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:minHeight="2dp"
            android:layout_toRightOf="@id/time_current"
            android:layout_centerVertical="true"
            android:layout_toLeftOf="@+id/play"
            android:maxHeight="2dp"
            android:thumbOffset="2dp"
            android:progressDrawable="@drawable/progressbar"
            android:thumb="@drawable/progressbar_thumb" />



        <ImageView
            android:id="@+id/play"
            android:layout_height="36dp"
            android:layout_centerVertical="true"
            android:layout_alignParentRight="true"
            android:layout_marginRight="16dp"
            android:layout_width="36dp" />

</RelativeLayout>
```



