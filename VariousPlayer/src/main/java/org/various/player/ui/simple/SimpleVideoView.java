package org.various.player.ui.simple;

import android.content.Context;
import android.util.AttributeSet;

import org.various.player.R;
import org.various.player.listener.UserActionListener;
import org.various.player.ui.base.BaseVideoView;

public class SimpleVideoView extends BaseVideoView<VideoControlView> {
    public SimpleVideoView(Context context) {
        super(context);

    }

    public SimpleVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public SimpleVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void initView(Context context, AttributeSet attrs) {
        super.initView(context, attrs);
        control.setOrientationListener(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.various_simple_view;
    }

    @Override
    protected int initControlView() {
        return R.id.video_control;
    }


    public void setUserActionListener(UserActionListener listener) {
        control.setUserActionListener(listener);
    }


}
