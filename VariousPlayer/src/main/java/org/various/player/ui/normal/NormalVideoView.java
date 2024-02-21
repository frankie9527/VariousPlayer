package org.various.player.ui.normal;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;

import org.various.player.NotificationCenter;
import org.various.player.R;
import org.various.player.listener.UserActionListener;
import org.various.player.ui.base.BaseVideoView;
import org.various.player.view.VariousTextureView;

/**
 * Created by 江雨寒 on 2020/9/15
 * Email：847145851@qq.com
 * func: 相比SimpleVideoView多了截图，锁屏，多次双击的快进快退
 */
public class NormalVideoView extends BaseVideoView<NormalControlView> implements NotificationCenter.NotificationCenterDelegate {
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
        player.setVideoEventListener(this);
        control.setOrientationListener(this);
    }


    public void setUserActionListener(UserActionListener listener) {
        control.setUserActionListener(listener);
    }


    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
        Bitmap bitmap = video_container.getBitmap();
        String str = "";
    }
}
