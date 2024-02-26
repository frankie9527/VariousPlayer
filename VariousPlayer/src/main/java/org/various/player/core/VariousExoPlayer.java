package org.various.player.core;

import android.net.Uri;
import android.text.TextUtils;
import android.view.Surface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;

import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.util.Util;

import org.various.player.NotificationCenter;
import org.various.player.PlayerConfig;
import org.various.player.PlayerConstants;
import org.various.player.utils.LogUtils;

public class VariousExoPlayer extends AbstractBasePlayer implements Player.Listener, NotificationCenter.NotificationCenterDelegate {
    private final String TAG = "VariousExoPlayer";
    private MediaSource mediaSource;
    private ExoPlayer player;
    private DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    private String url;

    public VariousExoPlayer() {
        trackSelector = new DefaultTrackSelector(PlayerConfig.getContext(), new AdaptiveTrackSelection.Factory());
        trackSelectorParameters = new DefaultTrackSelector.Parameters.Builder(PlayerConfig.getContext()).build();
        trackSelector.setParameters(trackSelectorParameters);
        player = new ExoPlayer.Builder(PlayerConfig.getContext()).setTrackSelector(trackSelector).build();
        player.addListener(this);

    }

    public DefaultTrackSelector getTrackSelector() {
        return trackSelector;
    }

    public DefaultTrackSelector.Parameters getTrackSelectorParameters() {
        return trackSelectorParameters;
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
        LogUtils.e(TAG, "pause");
        if (player != null && player.isPlaying()) {
            player.setPlayWhenReady(false);
        }
    }

    @Override
    public void release() {
        url = "";
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
        this.url = url;
        this.mediaSource = createMediaSource(url, null);
    }

    @Override
    public String getVideoUrl() {
        return url;
    }

    @Override
    public void setVolume(float volume) {
        if (player != null)
            player.setVolume(volume);

    }

    @Override
    public void setSpeed(float speed) {
        PlaybackParameters params = new PlaybackParameters(speed, 1.0f);
        player.setPlaybackParameters(params);

    }

    @Override
    public float getSpeed() {
        PlaybackParameters params = player.getPlaybackParameters();
        return params.speed;
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
            player.setMediaSource(mediaSource);
            player.prepare();
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
        int type = TextUtils.isEmpty(overrideExtension)
                ? Util.inferContentType(Uri.parse(url))
                : Util.inferContentTypeForExtension(overrideExtension);
        ;
        if (type == C.CONTENT_TYPE_DASH) {
            return new DashMediaSource.Factory(PlayerConfig.buildDataSourceFactory()).createMediaSource(MediaItem.fromUri(url));
        }
        if (type == C.CONTENT_TYPE_SS) {
            return new SsMediaSource.Factory(PlayerConfig.buildDataSourceFactory()).createMediaSource(MediaItem.fromUri(url));
        }
        if (type == C.CONTENT_TYPE_HLS) {
            return new HlsMediaSource.Factory(PlayerConfig.buildDataSourceFactory()).createMediaSource(MediaItem.fromUri(url));
        }
        return new ProgressiveMediaSource.Factory(PlayerConfig.buildDataSourceFactory()).createMediaSource(MediaItem.fromUri(url));
    }

    @Override
    public void onTimelineChanged(@NonNull Timeline timeline, int reason) {
        LogUtils.e(TAG, "onTimelineChanged");
    }


    @Override
    public void onIsLoadingChanged(boolean isLoading) {
        Player.Listener.super.onIsLoadingChanged(isLoading);
        LogUtils.e(TAG, "onIsLoadingChanged isLoading=" + isLoading);
        notifyStatus(PlayerConstants.EXO_CORE, isLoading?Player.STATE_BUFFERING:Player.STATE_READY);
    }

    @Override
    public void onPlaybackStateChanged(int playbackState) {
        Player.Listener.super.onPlaybackStateChanged(playbackState);
        LogUtils.e(TAG, "onPlaybackStateChanged playbackState=" + playbackState);
        notifyStatus(PlayerConstants.EXO_CORE, playbackState);
    }

    @Override
    public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {
        Player.Listener.super.onPlayWhenReadyChanged(playWhenReady, reason);
        LogUtils.e(TAG, "onPlayWhenReadyChanged=" + playWhenReady + " reason=" + reason);
        notifyStatus(PlayerConstants.EXO_CORE, reason);

    }

    @Override
    public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {
        LogUtils.e(TAG, "onPlaybackSuppressionReasonChanged=" + playbackSuppressionReason);

    }

    @Override
    public void onIsPlayingChanged(boolean isPlaying) {
        LogUtils.e(TAG, "onIsPlayingChanged=" + isPlaying);
    }


    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
        LogUtils.e(TAG, "onShuffleModeEnabledChanged=" + shuffleModeEnabled);
    }

    @Override
    public void onPlayerError(@NonNull PlaybackException error) {
        LogUtils.e(TAG, "onPlayerError =" + error.toString());
        Player.Listener.super.onPlayerError(error);
        notifyPlayerError();
    }


    @Override
    public void onPlaybackParametersChanged(@NonNull PlaybackParameters playbackParameters) {
        LogUtils.e(TAG, "onPlaybackParametersChanged");
    }


    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
        LogUtils.e(TAG, "didReceivedNotification");
    }

    @Override
    public void playRetry() {
        LogUtils.e(TAG, "playRetry");
        player.prepare();
    }
}
