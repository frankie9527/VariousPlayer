package org.various.player.ui.normal;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;

import org.various.player.NotificationCenter;
import org.various.player.PlayerConstants;
import org.various.player.R;
import org.various.player.listener.PlayerStatusListener;
import org.various.player.listener.UserActionListener;
import org.various.player.ui.base.BaseVideoView;
import org.various.player.ui.base.impl.IVideoControl;
import org.various.player.view.VariousTextureView;

/**
 * Created by 江雨寒 on 2020/9/15
 * Email：847145851@qq.com
 * func: 相比SimpleVideoView多了截图，锁屏，多次双击的快进快退
 */
public class NormalVideoView extends BaseVideoView implements PlayerStatusListener, NotificationCenter.NotificationCenterDelegate {
    IVideoControl control;
    VariousTextureView video_container;

    public NormalVideoView(Context context) {
        super(context);
        initView(context);
    }

    public NormalVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public NormalVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {
        View.inflate(context, R.layout.various_normal_view, this);
        control = findViewById(R.id.video_control);
        video_container=findViewById(R.id.video_container);
        player.setVideoEventListener(this);
        control.setOrientationListener(this);

    }

    public void setPlayData(String url, String title) {
        player.setVideoUri(url);
        control.setTitle(title);
    }

    @Override
    public void startSyncPlay() {
        super.startSyncPlay();
        control.stateBuffering();
    }

    public void setUserActionListener(UserActionListener listener) {
        control.setUserActionListener(listener);
    }
    @Override
    public void statusChange(int status) {
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

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
      Bitmap bitmap= video_container.getBitmap();
      String str="";
    }
}
