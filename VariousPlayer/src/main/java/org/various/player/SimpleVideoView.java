package org.various.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class SimpleVideoView extends RelativeLayout {
    VideoControl control;
    VariousSurfacePlayer play_view;

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
        play_view = findViewById(R.id.play_view);
        control = new VideoControl();
    }

    public void setPlayData(String url, String title) {
        play_view.setVideoUri(url);
        control.setTitle(title);
    }

    public void startSyncPlay() {
         play_view.startSyncPlay();
    }
}
