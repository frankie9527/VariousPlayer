package org.various.player.core;

import android.net.Uri;
import android.view.Surface;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import org.various.player.PlayerConfig;



public class VariousExoPlayer extends AbstractBasePlayer implements Player.EventListener {
    String TAG = "VariousExoPlayer";
    MediaSource mediaSource;
    SimpleExoPlayer player;


    public VariousExoPlayer() {
        player = new SimpleExoPlayer.Builder(PlayerConfig.getContext()).build();
        player.addListener(this);
    }


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
        if (player != null && player.isPlaying()) {
            player.setPlayWhenReady(false);
        }
    }

    @Override
    public void release() {
        if (player != null) {
            player.removeListener(this);
            player.release();
        }
    }

    @Override
    public void resume() {
        if (player != null)
            player.setPlayWhenReady(true);
    }

    @Override
    public void seekTo(long milliseconds) {
        if (player != null)
            player.seekTo(milliseconds);
    }

    @Override
    public void setVideoUri(@Nullable String url) {
        this.mediaSource = createMediaSource(url, null);
    }

    @Override
    public void setVolume(float volume) {
        if (player != null)
            player.setVolume(volume);

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
        if (player != null) {
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }

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
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(PlayerConfig.getContext(), Util.getUserAgent(PlayerConfig.getContext(), "org.Various.player"));
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

    @Override
    public void onTimelineChanged(Timeline timeline, int reason) {
        Log.e(TAG, "onTimelineChanged");
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        Log.e(TAG, "onTracksChanged");
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
//        Log.e(TAG, "onLoadingChanged=" + isLoading);
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        Log.e(TAG, "onPlayerStateChanged=" + playWhenReady + " playbackState=" + playbackState);
        notifyStatus(playbackState);
    }

    @Override
    public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {
        Log.e(TAG, "onPlaybackSuppressionReasonChanged=" + playbackSuppressionReason);

    }

    @Override
    public void onIsPlayingChanged(boolean isPlaying) {
        Log.e(TAG, "onIsPlayingChanged=" + isPlaying);
    }


    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
        Log.e(TAG, "onShuffleModeEnabledChanged=" + shuffleModeEnabled);
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        Log.e(TAG, "ExoPlaybackException=");
        notifyPlayerError();
    }

    @Override
    public void onPositionDiscontinuity(int reason) {
        Log.e(TAG, "onPositionDiscontinuity=" + reason);
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        Log.e(TAG, "onPlaybackParametersChanged");
    }

    @Override
    public void onSeekProcessed() {
        Log.e(TAG, "onSeekProcessed");
    }
}
