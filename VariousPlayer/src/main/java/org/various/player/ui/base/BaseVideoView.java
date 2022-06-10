package org.various.player.ui.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.SurfaceTexture;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.PlaybackParameters;

import org.various.player.PlayerConstants;
import org.various.player.R;
import org.various.player.core.AbstractBasePlayer;
import org.various.player.core.IPlayer;
import org.various.player.core.PlayerManager;
import org.various.player.listener.PlayerStatusListener;
import org.various.player.listener.UserChangeOrientationListener;
import org.various.player.ui.base.impl.IVideoControl;
import org.various.player.utils.OrientationUtils;

/**
 * Created by 江雨寒 on 2020/8/19
 * Email：847145851@qq.com
 * func:
 */
public abstract class BaseVideoView<T extends BaseControlView> extends FrameLayout implements IPlayer, UserChangeOrientationListener, PlayerStatusListener {
    protected AbstractBasePlayer player;
    OrientationUtils orientationUtils;
    private int initHeight;
    protected T control;
    public int playStatus = -1;
    private FrameLayout video_container;
    private TextureView textureView;
    private Context context;
    private String url;
    private String title;
    private boolean inRecycle = false;

    public BaseVideoView(@NonNull Context context) {
        super(context);
        initView(context, null);
    }

    public void setPlayData(String url, String title) {
        this.url = url;
        this.title = title;
        if (!inRecycle) {
            player.setVideoUri(url);
        }
        control.topView.setTitle(this.title);
    }

    public BaseVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        initView(context, attrs);
    }

    public BaseVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    protected void initView(Context context, AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable") final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PlayerType);
        inRecycle = a.getBoolean(R.styleable.PlayerType_in_recycle, false);
        a.recycle();
        this.context = context;
        View.inflate(context, setLayoutId(), this);
        control = findViewById(initControlView());
        video_container = findViewById(R.id.video_container);
        control.centerView.getCenterPlayView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((PlayerManager.getPlayer().isPlaying() && playStatus != PlayerConstants.BUFFERING)
                        || (!PlayerManager.getPlayer().isPlaying() && playStatus == PlayerConstants.IDLE)) {
                    initPlayer();
                    return;
                }
                if (PlayerManager.getPlayer().isPlaying()) {
                    PlayerManager.getPlayer().pause();
                    return;
                }
                int currentStatus = PlayerManager.getCurrentStatus();
                long currentPosition = PlayerManager.getPlayer().getCurrentPosition();
                String url = PlayerManager.getPlayer().getVideoUrl();
                if (currentStatus == PlayerConstants.IDLE && currentPosition == 0 && !TextUtils.isEmpty(url)) {
                    PlayerManager.getPlayer().startSyncPlay();
                    return;
                }
                PlayerManager.getPlayer().resume();
            }
        });
        if (!inRecycle) {
            initPlayer();
            if (orientationUtils == null) {
                orientationUtils = new OrientationUtils(context);
            }
        } else {
            orientationUtils = new OrientationUtils(context);
        }
        if (player == null) {
            player = PlayerManager.getPlayer();
        }
        player.setVideoEventListener(BaseVideoView.this);
    }


    protected abstract int setLayoutId();

    protected abstract int initControlView();

    private void initPlayer() {
        if (textureView != null) {
            video_container.removeView(textureView);
        }
        textureView = new TextureView(context);
        listener = new TextureListener();
        textureView.setSurfaceTextureListener(listener);
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER);
        video_container.addView(textureView, layoutParams);
    }

    TextureListener listener;

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
        if (player != null)
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
        control.stateBuffering();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        orientationUtils.release();
    }

    @Override
    public void changeOrientation() {
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        if (initHeight == 0) {
            initHeight = lp.height;
        }
        int currentOrientation = orientationUtils.getOrientation();
        if (currentOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT || currentOrientation == -1) {
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            orientationUtils.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            orientationUtils.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            lp.height = initHeight;
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

    @Override
    public void statusChange(int status) {
        playStatus = status;
        if (status == PlayerConstants.READY) {
            control.stateReady();
        } else if (status == PlayerConstants.BUFFERING) {
            control.stateBuffering();
        } else if (status == PlayerConstants.END) {
            control.showComplete();
        } else if (status == PlayerConstants.ERROR) {
            control.showError();
        }
    }

    protected class TextureListener implements TextureView.SurfaceTextureListener {
        @Override
        public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
            player.setVideoEventListener(BaseVideoView.this);
            player.setVideoSurface(new Surface(surface));
            if (inRecycle) {
                player.setVideoUri(url);
                player.startSyncPlay();
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
            player.clearVideoSurface();
            if (inRecycle) {
                PlayerManager.releasePlayer();
                player = null;
            }
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {

        }
    }
}
