package org.various.player.ui.simple;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import org.various.player.R;
import org.various.player.listener.UserActionListener;
import org.various.player.ui.base.BaseVideoView;
import org.various.player.utils.OrientationUtils;

public class SimpleVideoView extends BaseVideoView<SimpleControlView> {
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


    protected void initView(Context context) {
        View.inflate(context, R.layout.various_simple_view, this);
        control = findViewById(R.id.video_control);
        player.setVideoEventListener(this);
        control.setOrientationListener(this);
    }



    public void setUserActionListener(UserActionListener listener) {
        control.setUserActionListener(listener);
    }


}
