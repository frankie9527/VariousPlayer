package org.various.player.ui.recycler;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.various.player.R;
import org.various.player.ui.base.BaseControlView;
import org.various.player.ui.base.BaseTopView;
import org.various.player.ui.simple.VideoBottomView;
import org.various.player.ui.simple.VideoCenterView;
import org.various.player.utils.OrientationUtils;
import org.various.player.utils.ToastUtils;
import org.various.player.utils.UiUtils;

/**
 * author: Frankie
 * Date: 2024/2/20
 * Description:
 */
public class RecyclerItemControlView extends BaseControlView<BaseTopView, VideoBottomView, VideoCenterView> {
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
        return 0;
    }

    @Override
    protected int setBottomViewId() {
        return R.id.video_bottom_view;
    }

    @Override
    protected int setCenterViewId() {
        return R.id.video_center_view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_status) {
            ToastUtils.show("播放");
        }
        if (view.getId() == R.id.img_switch_screen) {
            startFullScreen();
        }
    }
    public void quitFullScreen() {

    }
    public void startFullScreen(){

    }
}
