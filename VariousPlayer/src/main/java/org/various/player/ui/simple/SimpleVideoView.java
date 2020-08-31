package org.various.player.ui.simple;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import org.various.player.IVideoControl;
import org.various.player.PlayerConstants;
import org.various.player.R;
import org.various.player.listener.UserActionListener;
import org.various.player.listener.PlayerStatustListener;
import org.various.player.ui.base.BaseVideoView;

public class SimpleVideoView extends BaseVideoView implements PlayerStatustListener {
    IVideoControl control;


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
        control = findViewById(R.id.video_control);
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


}
