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

import org.various.player.PlayerConstants;
import org.various.player.R;
import org.various.player.ui.base.BaseControlView;
import org.various.player.ui.simple.VideoBottomView;
import org.various.player.ui.simple.VideoCenterView;
import org.various.player.ui.simple.VideoTopView;
import org.various.player.utils.OrientationUtils;


/**
 * author: Frankie
 * Date: 2024/2/20
 * Description:
 */
public class RecyclerItemControlView extends BaseControlView<VideoTopView, VideoBottomView, VideoCenterView> {
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

    @Override
    public void onClick(View view) {
        if (topView != null && view == topView.getBackView()) {
            if (OrientationUtils.Orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                quitFullScreen();
                topView.onScreenOrientationChanged(OrientationUtils.Orientation,true);
                centerView.onScreenOrientationChanged(OrientationUtils.Orientation,true);
                bottomView.onScreenOrientationChanged(OrientationUtils.Orientation,true);
            }

        }
        if (bottomView != null && view == bottomView.getImgSwitchScreen()) {
            if (OrientationUtils.Orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                quitFullScreen();
            } else {
                startFullScreen();
            }
            topView.onScreenOrientationChanged(OrientationUtils.Orientation,true);
            centerView.onScreenOrientationChanged(OrientationUtils.Orientation,true);
            bottomView.onScreenOrientationChanged(OrientationUtils.Orientation,true);
        }
    }


    protected ViewGroup.LayoutParams blockLayoutParams;
    protected int blockIndex;
    protected int blockWidth;
    protected int blockHeight;
    protected ViewGroup parent;
    protected ViewGroup videoView;
    protected long userChangeOrientationTime;

    public void startFullScreen() {
        userChangeOrientationTime = System.currentTimeMillis();
        Activity activity = OrientationUtils.getInstance().getActivity(getContext());
        if (activity == null) {
            return;
        }
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        //从当前视图中移除播放器视图
        videoView = (ViewGroup) getParent();
        parent= (ViewGroup) videoView.getParent();
        blockLayoutParams = getLayoutParams();
        blockIndex = parent.indexOfChild(this);
        blockWidth = getWidth();
        blockHeight = getHeight();
        parent.removeView(videoView);
        ViewGroup.LayoutParams fullLayout = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        decorView.addView(videoView, fullLayout);
        hideSysBar(getContext());
        OrientationUtils.getInstance().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }


    public void quitFullScreen() {
        userChangeOrientationTime = System.currentTimeMillis();
        Activity activity = OrientationUtils.getInstance().getActivity(getContext());
        if (activity == null) {
            return;
        }
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        decorView.removeView(videoView);
        parent.addView(videoView, blockIndex, blockLayoutParams);
        OrientationUtils.getInstance().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

}
