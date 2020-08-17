package org.various.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import org.various.player.listener.UserActionListener;

public class SimpleVideoView extends RelativeLayout {
    IVideoControl control;
    VariousSurfacePlayer video_container;

    public SimpleVideoView(Context context) {
        super(context);
        initView(context);
    }

    public SimpleVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SimpleVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {
        View.inflate(context, R.layout.various_simple_view, this);
        video_container = findViewById(R.id.video_container);
        control = findViewById(R.id.video_control);

    }

    public void setPlayData(String url, String title) {
        video_container.setVideoUri(url);
        control.setTitle(title);
    }

    public void startSyncPlay() {
        setKeepScreenOn(true);
        control.showLoading();
        video_container.startSyncPlay();
    }
    public void setUserActionListener(UserActionListener listener){
        control.setUserActionListener(listener);
    }
}
