package org.various.player.ui.simple;

import android.content.Context;
import android.util.AttributeSet;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.various.player.R;

import org.various.player.ui.base.BaseControlView;

public class VideoControlView extends BaseControlView<VideoTopView,VideoBottomView, VideoCenterView> {

    public VideoControlView(@NonNull Context context) {
        super(context);
    }

    public VideoControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.various_simple_view_control;
    }

    @Override
    protected int setTopViewId() {
        return R.id.video_top_view;
    }

    @Override
    protected int setBottomViewId() {
        return R.id.video_bottom_view;
    }

    @Override
    protected int setLoaindViewId() {
        return R.id.video_center_view;
    }
}
