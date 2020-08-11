package org.various.player;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;

public interface ISimpleVideoView {
    @FloatRange(from = 0.0d, to = 1.0d)
    float getVolume();

    boolean isPlaying();

    void pause();

    void release();

    void resume();

    void seekTo(@IntRange(from = 0) long j);

    void setVideoUri(@Nullable String str);

    boolean setVolume(@FloatRange(from = 0.0d, to = 1.0d) float f);

    void startSyncPlay();
}
