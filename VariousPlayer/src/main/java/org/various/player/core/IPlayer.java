package org.various.player.core;


import android.view.Surface;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;


public interface IPlayer {
    @FloatRange(from = 0.0d, to = 1.0d)
    float getVolume();

    boolean isPlaying();

    void pause();

    void release();

    void resume();

    void seekTo(@IntRange(from = 0) long milliseconds);

    void setVideoUri(@Nullable String url);

    default String getVideoUrl() {
        return "";
    }

    ;

    void setVolume(@FloatRange(from = 0.0d, to = 1.0d) float volume);


    void setSpeed(float speed);

    float getSpeed();

    @IntRange(from = 0)
    long getDuration();

    @IntRange(from = 0)
    long getCurrentPosition();

    @IntRange(from = 0, to = 100)
    int getBufferedPercent();

    default void startSyncPlay() {
    }


    default void setVideoSurface(@Nullable Surface surface) {

    }

    default boolean isReleased() {
        return false;
    }

    default void clearVideoSurface() {
    }
    default void playRetry() {
    }
}
