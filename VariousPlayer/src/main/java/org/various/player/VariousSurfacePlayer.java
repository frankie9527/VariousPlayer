package org.various.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.various.player.core.PlayerManager;
import org.various.player.core.VariousExoPlayer;
import org.various.player.core.IPlayer;

public class VariousSurfacePlayer extends SurfaceView implements SurfaceHolder.Callback, IPlayer {
    IPlayer player;

    public VariousSurfacePlayer(Context context) {
        super(context);
        init();
    }

    public VariousSurfacePlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VariousSurfacePlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        player = PlayerManager.getPlayer();
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        player.setVideoSurface(surfaceHolder.getSurface());
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        player.clearVideoSurface();
    }

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
        player.setVideoUri(url);
    }

    @Override
    public boolean setVolume(float volume) {
        return player.setVolume(volume);
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
        player.startSyncPlay();
    }
}
