package org.various.player.ui.normal;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.various.player.R;
import org.various.player.ui.base.BaseControlView;
import org.various.player.ui.simple.SimpleBottomView;
import org.various.player.ui.simple.SimpleTopView;

public class NormalControlView extends BaseControlView<SimpleTopView, SimpleBottomView, NormalCenterView> {

    public NormalControlView(@NonNull Context context) {
        super(context);
    }

    public NormalControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NormalControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.various_normal_view_control;
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
