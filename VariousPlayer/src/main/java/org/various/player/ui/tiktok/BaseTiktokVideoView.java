package org.various.player.ui.tiktok;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.various.player.PlayerConstants;
import org.various.player.R;
import org.various.player.core.AbstractBasePlayer;
import org.various.player.core.IPlayer;
import org.various.player.core.PlayerManager;
import org.various.player.core.VariousPlayerManager;
import org.various.player.listener.PlayerStatusListener;
import org.various.player.listener.UserChangeOrientationListener;
import org.various.player.utils.LogUtils;
import org.various.player.utils.OrientationUtils;
import org.various.player.utils.UiUtils;
import org.various.player.view.CanDragSeekBar;

/**
 * Created by Frankie on 2020/8/19
 * Email：847145851@qq.com
 * func:
 */
public abstract class BaseTiktokVideoView extends FrameLayout implements IPlayer, UserChangeOrientationListener, PlayerStatusListener {
    private final String TAG = "BaseTiktokVideoView";
    public FrameLayout video_container;
    public TextureView textureView = null;
    public ImageView img_status, img_back_ground;
    public CanDragSeekBar video_seek;
    public TouchListener touchListener;
    protected AbstractBasePlayer player;

    public void setPlayer(AbstractBasePlayer player) {
        this.player = player;
    }




    public BaseTiktokVideoView(@NonNull Context context) {
        super(context);
        init();
    }

    public BaseTiktokVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseTiktokVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        touchListener = new TouchListener(getContext());
        setOnTouchListener(touchListener);
    }

    public void setDataAndPlay(String url) {
        setPlayer(PlayerManager.getInstance().getPlayer());
        player.setVideoEventListener(this);
        player.setVideoUri(url);
        initTextureView();
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
        OrientationUtils.getInstance().release();
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
        LogUtils.d(TAG, "statusChange status=" + status);
        if (status == PlayerConstants.READY || status == PlayerConstants.IDLE) {
            img_status.setImageDrawable(ContextCompat.getDrawable(getContext(), VariousPlayerManager.isPlaying() ? R.drawable.video_pause : R.drawable.video_play));
        } else if (status == PlayerConstants.BUFFERING) {

        } else if (status == PlayerConstants.END) {
            player.playRetry();
        } else if (status == PlayerConstants.ERROR) {

        }
    }

    @Override
    public void onRenderedFirstFrame(int width, int height) {
        img_back_ground.setVisibility(GONE);
        int sw = UiUtils.getWindowWidth();
        int sh = UiUtils.getWindowHeight();

        double result = (double) height / width;
        int vh = (int) (result * sw);

        LayoutParams layoutParams = (LayoutParams) video_container.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = vh;
        video_container.setLayoutParams(layoutParams);
    }

    @Override
    public void changeOrientation() {

    }


    public void initTextureView() {
        if (textureView != null) {
            textureView = null;
        }
        LayoutParams layoutParams =
                new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER);
        textureView = VariousPlayerManager.getTextureView(getContext());
        ViewGroup parent = (ViewGroup) textureView.getParent();
        if (parent != null) {
            parent.removeView(textureView);
        }
        video_container.addView(textureView, layoutParams);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                player.startSyncPlay();
            }
        }, 50);

    }

    protected class TouchListener extends GestureDetector.SimpleOnGestureListener implements OnTouchListener {
        protected GestureDetector gestureDetector;

        public TouchListener(Context context) {
            gestureDetector = new GestureDetector(context, this);

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            gestureDetector.onTouchEvent(event);
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
            if (player.isPlaying()) {
                player.pause();
            } else {
                player.resume();
            }
            return true;
        }
    }
}
