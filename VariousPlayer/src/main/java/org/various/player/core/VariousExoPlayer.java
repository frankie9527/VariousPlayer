package org.various.player.core;

import android.net.Uri;
import android.view.Surface;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.various.player.PlayerConfig;

public class VariousExoPlayer implements IPlayer {
    MediaSource mediaSource;
    SimpleExoPlayer player = new SimpleExoPlayer.Builder(PlayerConfig.getContext()).build();
    long totalTime = 0;

    @Override
    public float getVolume() {
        return player.getVolume();
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public void pause() {

    }

    @Override
    public void release() {
        player.release();
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
    public boolean setVolume(float volume) {
        return false;
    }

    @Override
    public long getDuration() {
        return player.getDuration();
    }

    @Override
    public long getCurrentPosition() {
        return player.getCurrentPosition();
    }

    @Override
    public int getBufferedPercent() {
        return player.getBufferedPercentage();
    }

    @Override
    public void startSyncPlay() {
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);

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
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(PlayerConfig.getContext(), Util.getUserAgent(PlayerConfig.getContext(), "yourApplicationName"));
        Uri uri = Uri.parse(url);
        if (type == C.TYPE_DASH) {
            return new DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        }
        if (type == C.TYPE_SS) {
            return new SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        }
        if (type == C.TYPE_HLS) {
            return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        }
        return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }
}
