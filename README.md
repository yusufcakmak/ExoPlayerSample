# ExoPlayer2 Sample Project

#### This repo updated for r2.10.4 version.

You can play videos and musics with ExoPlayer. This demo shows you how to use ExoPlayer. You can play mp3s or radio stream links with RadioActivity.

I wrote a article about ExoPlayer. 

[https://medium.com/@yusufcakmak/android-exoplayer-starters-guide-6350433f256c](https://medium.com/@yusufcakmak/android-exoplayer-starters-guide-6350433f256c)

I changed playback controls for custom ui demo. If you want to use default controls, you can delete exoplayer layout files. You can find more details under Custom UI heading.


### Playing Audio

```
  player = ExoPlayerFactory.newSimpleInstance(this)

  dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "exoPlayerSample"))

  mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(RADIO_URL))

  player.prepare(mediaSource)
        
  player.playWhenReady = true
  
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
        player = ExoPlayerFactory.newSimpleInstance(this)

        mediaDataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"))

        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory)
                .createMediaSource(Uri.parse(STREAM_URL))


        with(player) {
            prepare(mediaSource, false, false)
            playWhenReady = true
        }


        playerView.setShutterBackgroundColor(Color.TRANSPARENT)
        playerView.player = player
        playerView.requestFocus()
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


License
--------


    Copyright 2017 Yusuf Çakmak.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
