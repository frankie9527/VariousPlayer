package org.various.player.ui.recycler;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.nfc.Tag;
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

import org.various.player.NotificationCenter;
import org.various.player.PlayerConstants;
import org.various.player.R;

import org.various.player.core.PlayerManager;
import org.various.player.core.VariousPlayerManager;
import org.various.player.ui.base.recycler.BaseRecyclerVideoView;
import org.various.player.utils.LogUtils;
import org.various.player.utils.OrientationUtils;


/**
 * author: Frankie
 * Date: 2024/2/20
 * Description:
 */
public class RecyclerItemVideoView extends BaseRecyclerVideoView<RecyclerItemControlView> implements NotificationCenter.NotificationCenterDelegate {
    private static final String TAG = "RecyclerItemVideoView";
    private FrameLayout video_container;
    private TextureView textureView=null;

    public RecyclerItemVideoView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public RecyclerItemVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RecyclerItemVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    protected void initView(Context context) {
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.recycler_video_view_reset);
        View.inflate(context, R.layout.various_recycler_item_view, this);
        control = findViewById(R.id.video_control);
        video_container = findViewById(R.id.video_container);
        control.setOrientationListener(this);
        control.getCentView().getCenterPlayView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.d(TAG, "startItemPlay");
                startItemPlay();
            }
        });
    }

    int currentProgress = 0;

    public void startItemPlay() {
        currentProgress = control.bottomView.video_seek.getProgress();
        LogUtils.d(TAG, "currentProgress=" + currentProgress);
        if (currentProgress == 0 && textureView != null) {
            LogUtils.d(TAG, "currentProgress step0");
            VariousPlayerManager.startSyncPlay();
        } else if (currentProgress == 100) {
            LogUtils.d(TAG, "currentProgress step1");
            VariousPlayerManager.startSyncPlay();

        } else if (currentProgress != 0 && VariousPlayerManager.isPlaying()) {
            LogUtils.d(TAG, "currentProgress step2");
            VariousPlayerManager.pause();

        } else if (currentProgress != 0 && !VariousPlayerManager.isPlaying()) {
            LogUtils.d(TAG, "currentProgress step3");
            VariousPlayerManager.resume();

        } else if (currentProgress == 0 && textureView == null) {
            LogUtils.d(TAG, "currentProgress step4");
            control.initUiOrientationListener();
            initPlayer();
        }
    }

    private void initPlayer() {
        OrientationUtils.getInstance().init(getContext());
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.recycler_video_view_reset, control.getUrl());
        postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (textureView != null) {
//                    video_container.removeView(textureView);
//                }

                if (textureView == null) {
                    textureView = new TextureView(getContext());
                    listener = new TextureListener();
                    textureView.setSurfaceTextureListener(listener);
                    FrameLayout.LayoutParams layoutParams =
                            new FrameLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    Gravity.CENTER);
                    video_container.addView(textureView, layoutParams);
                }
                player = PlayerManager.getInstance().init();
            }
        }, 200);
    }

    TextureListener listener;

    public void setPlayData(String url, String title) {
        control.setUrl(url);
    }

    protected class TextureListener implements TextureView.SurfaceTextureListener {
        @Override
        public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
            setKeepScreenOn(true);
            player.setVideoEventListener(RecyclerItemVideoView.this);
            player.setVideoSurface(new Surface(surface));
            player.setVideoUri(control.getUrl());
            if (System.currentTimeMillis() - control.userChangeOrientationTime < 200) {
                return;
            }
            player.startSyncPlay();
        }

        @Override
        public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
            LogUtils.d(TAG, "currentProgress onSurfaceTextureDestroyed");
            if (System.currentTimeMillis() - control.userChangeOrientationTime < 200) {
                return false;
            }
            control.resetVideoView();
            player.clearVideoSurface();
            player.release();
            textureView=null;
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {

        }
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
        if (id == NotificationCenter.recycler_video_view_reset) {
            String playUrl = (String) args[0];
            if (!TextUtils.isEmpty(playUrl) && playUrl.equals(control.getUrl())) {
                return;
            }
            control.resetVideoView();
            VariousPlayerManager.release();
            if (textureView != null) {
                video_container.removeView(textureView);
            }
        }
    }

}
