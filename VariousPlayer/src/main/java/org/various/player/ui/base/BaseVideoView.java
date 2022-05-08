package org.various.player.ui.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.PlaybackParameters;

import org.various.player.core.AbstractBasePlayer;
import org.various.player.core.IPlayer;
import org.various.player.core.PlayerManager;
import org.various.player.listener.PlayerStatusListener;
import org.various.player.listener.UserChangeOrientationListener;
import org.various.player.utils.OrientationUtils;

/**
 * Created by 江雨寒 on 2020/8/19
 * Email：847145851@qq.com
 * func:
 */
public abstract class BaseVideoView extends FrameLayout implements IPlayer , UserChangeOrientationListener {
    protected AbstractBasePlayer player;
    OrientationUtils orientationUtils;
    private  int initHeight;
    public BaseVideoView(@NonNull Context context) {
        super(context);
        init();
    }

    public BaseVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        player = PlayerManager.init();
        orientationUtils=new OrientationUtils(getContext());
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
        setKeepScreenOn(true);
        player.startSyncPlay();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        orientationUtils.release();
    }

    @Override
    public void changeOrientation() {
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.width=ViewGroup.LayoutParams.MATCH_PARENT;
        if (initHeight==0){
            initHeight=lp.height;
        }
        int  currentOrientation=orientationUtils.getOrientation();
        if (currentOrientation== ActivityInfo.SCREEN_ORIENTATION_PORTRAIT||currentOrientation==-1){
            lp.height=ViewGroup.LayoutParams.MATCH_PARENT;
            orientationUtils.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else {
            orientationUtils.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            lp.height=initHeight;
        }
        setLayoutParams(lp);
    }
    @Override
    public void setSpeed(float speed) {
        player.setSpeed(speed);
    }

    @Override
    public float getSpeed() {
        return player.getSpeed();
    }

}
