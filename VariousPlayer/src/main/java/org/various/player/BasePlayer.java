package org.various.player;

import android.net.Uri;
import android.view.Surface;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class BasePlayer  implements IPlayer{
    MediaSource mediaSource;
    SimpleExoPlayer player = new SimpleExoPlayer.Builder(VariousSDK.getContext()).build();
    @Override
    public float getVolume() {
        return 0;
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void pause() {

    }

    @Override
    public void release() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void seekTo(long milliseconds) {
          player.seekTo(milliseconds);
    }

    @Override
    public void setVideoUri(@Nullable String url) {
        this.mediaSource = createMediaSource(url, null);
    }

    @Override
    public boolean setVolume(float f) {
        return false;
    }

    @Override
    public void startSyncPlay() {

    }

    @Override
    public void setVideoSurface(@Nullable Surface surface) {
        player.setVideoSurface(surface);
    }

    @Override
    public void clearVideoSurface() {
        player.clearVideoSurface();
    }
    public MediaSource createMediaSource(@Nullable String url, @Nullable String overrideExtension) {
        int type = Util.inferContentType(Uri.parse(url), overrideExtension);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(VariousSDK.getContext(), Util.getUserAgent(VariousSDK.getContext(), "yourApplicationName"));
        Uri uri = Uri.parse(url);
        if (type == 0) {
            return new DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        }
        if (type == 1) {
            return new SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        }
        if (type == 2) {
            return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        }
        return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }
}
