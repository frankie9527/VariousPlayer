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

    boolean setVolume(@FloatRange(from = 0.0d, to = 1.0d) float volume);


    @IntRange(from = 0)
    long getDuration();

    @IntRange(from = 0)
    long getCurrentPosition();
    @IntRange(from = 0, to = 100)
    int getBufferedPercent();

    default void startSyncPlay(){};


    default void setVideoSurface(@Nullable Surface surface){

    }

    default void clearVideoSurface(){}

}
