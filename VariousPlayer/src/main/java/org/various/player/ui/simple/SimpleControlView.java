package org.various.player.ui.simple;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.various.player.R;

import org.various.player.ui.base.BaseControlView;

public class SimpleControlView extends BaseControlView<SimpleTopView, SimpleBottomView, SimpleCenterView> {

    public SimpleControlView(@NonNull Context context) {
        super(context);
    }

    public SimpleControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    protected int setCenterViewId() {
        return R.id.video_center_view;
    }
}
