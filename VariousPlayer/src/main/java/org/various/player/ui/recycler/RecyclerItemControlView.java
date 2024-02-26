package org.various.player.ui.recycler;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.various.player.R;

import org.various.player.ui.base.recycler.BaseRecyclerControlView;
import org.various.player.utils.OrientationUtils;


/**
 * author: Frankie
 * Date: 2024/2/20
 * Description:
 */
public class RecyclerItemControlView extends BaseRecyclerControlView<RecyclerTopView, RecyclerBottomView, RecyclerCenterView> {
    public RecyclerItemControlView(@NonNull Context context) {
        super(context);
    }

    public RecyclerItemControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerItemControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.various_recycler_item_control;
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
