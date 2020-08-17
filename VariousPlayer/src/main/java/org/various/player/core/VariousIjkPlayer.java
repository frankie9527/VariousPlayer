package org.various.player.core;

import android.view.Surface;

import androidx.annotation.Nullable;

/**
 * Created by 江雨寒 on 2020/8/17
 * Email：847145851@qq.com
 * func:
 */
public class VariousIjkPlayer implements IPlayer {
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

    }

    @Override
    public void setVideoUri(@Nullable String url) {

    }

    @Override
    public boolean setVolume(float volume) {
        return false;
    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public long getCurrentPosition() {
        return 0;
    }

    @Override
    public int getBufferedPercent() {
        return 0;
    }

    @Override
    public void startSyncPlay() {

    }

    @Override
    public void setVideoSurface(@Nullable Surface surface) {

    }

    @Override
    public void clearVideoSurface() {

    }
}
