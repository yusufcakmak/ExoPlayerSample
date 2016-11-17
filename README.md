# ExoPlayer2 Sample Project

You can play videos and musics with ExoPlayer. This demo shows you how to use ExoPlayer. You can play mp3s or radio stream links with RadioActivity.

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

You can use VideoPlayerActivity for playing videos. If you use hls or dash formats, you need to use HlsMediaSource as a MediaSource.

```
MediaSource mediaSource = new HlsMediaSource(Uri.parse("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"),
                mediaDataSourceFactory, mainHandler, null);
player.prepare(mediaSource);
```

If you use mp4,mp3, flv, ogg, mkv or other formats, you . need to use ExtractorMediaSource as a MediaSource

```
extractorsFactory = new DefaultExtractorsFactory();
MediaSource mediaSource = new ExtractorMediaSource(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"),
                mediaDataSourceFactory, extractorsFactory, null, null);
player.prepare(mediaSource);
```
