package org.various.player.ui.base.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.various.player.PlayerConstants;
import org.various.player.core.AbstractBasePlayer;
import org.various.player.core.IPlayer;
import org.various.player.listener.PlayerStatusListener;
import org.various.player.listener.UserChangeOrientationListener;

/**
 * Created by Frankie on 2020/8/19
 * Email：847145851@qq.com
 * func:
 */
public abstract class BaseRecyclerVideoView<T extends BaseRecyclerControlView> extends FrameLayout implements IPlayer, UserChangeOrientationListener, PlayerStatusListener {
    protected AbstractBasePlayer player;
    protected T control;


    public BaseRecyclerVideoView(@NonNull Context context) {
        super(context);
    }

    public BaseRecyclerVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public BaseRecyclerVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    public T getControlView() {
        return control;
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
        player.pause();
    }

    @Override
    public void release() {
        if (player != null) {
            player.release();
        }
    }


    @Override
    public void resume() {
        player.resume();
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
    public void setVolume(float volume) {
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
        return player.getBufferedPercent();
    }

    @Override
    public void startSyncPlay() {

    }

    @Override
    public void changeOrientation() {

    }

    @Override
    public void setSpeed(float speed) {
        player.setSpeed(speed);
    }

    @Override
    public float getSpeed() {
        return player.getSpeed();
    }

    @Override
    public void statusChange(int status) {
        if (status == PlayerConstants.READY || status == PlayerConstants.IDLE) {
            control.stateReady();
        } else if (status == PlayerConstants.BUFFERING) {
            control.stateBuffering();
        } else if (status == PlayerConstants.END) {
            control.showComplete();
        } else if (status == PlayerConstants.ERROR) {
            control.showError();
        }
    }

    @Override
    public void onRenderedFirstFrame(int width,int height) {

    }
}
