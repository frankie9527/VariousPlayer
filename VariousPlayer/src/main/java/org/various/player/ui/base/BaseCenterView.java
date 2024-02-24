package org.various.player.ui.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.various.player.PlayerConstants;
import org.various.player.core.PlayerManager;
import org.various.player.listener.UserProgressListener;
import org.various.player.utils.OrientationUtils;
import org.various.player.utils.UiUtils;

/**
 * Created by 江雨寒 on 2020/8/19
 * Email：847145851@qq.com
 * func:
 */
public abstract class BaseCenterView extends FrameLayout {
    private static final String TAG = "BaseCenterView";
    private float downX, downY;
    private int videoViewWidth, videoViewHeight;
    public ImageView img_status;
    /**
     * getScaledTouchSlop是ViewConfiguration类里的一个方法，返回值是int类型，其含义是一段滑动的距离，一般用在解决滑动冲突上，用法就是获取手指滑动的距离为X，如果X大于这个值就让控件去处理滑动事件，否则就不作滑动处理。
     */
    private int defaultMoveLength;

    /**
     * moveType :-1 初始化状态
     * moveType :0 左右移动
     * moveType ：1 上下移动
     */
    private int moveType = -1;
    /**
     * 判断action_down 的时候是在屏幕左边还是右边
     */
    private boolean firstDownIsLeft = false;

    /**
     * 上一次音量或者屏幕亮度值
     */
    private int lastChangPercent = -1;
    public float currentVolume = -1;
    public float currentBrightness = -1;
    public float lastDownX = -1;
    public long actionDownVideoTime = -1;
    public long videoDurationTime = -1;

    public float offsetDis;
    public long offsetTime;
    public int arrowDirection;
    /**
     * 上一次播放时间
     */
    public long lastVideoPlayTime;

    private UserProgressListener userProgressListener;

    public BaseCenterView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public BaseCenterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BaseCenterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void setUserProgressListener(UserProgressListener userProgressListener) {
        this.userProgressListener = userProgressListener;
    }

    protected abstract int setLayoutId();

    public void initView(Context context) {
        View.inflate(context, setLayoutId(), this);
        defaultMoveLength = ViewConfiguration.get(context).getScaledTouchSlop() + 24;

    }

    public abstract void showLoading();

    public abstract void hideLoadingAndPlayIcon();

    public abstract void showEnd();

    public abstract void showError();

    public abstract void hideAll();

    public  void hideAllExceptPlayIcon(){};

    public abstract void showStatus();

    @SuppressLint("Range")
    public void handleTouch(View view, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastDownX = downX = event.getX();
                downY = event.getY();
                Log.e(TAG, "lastDownX=" + lastDownX);
                Log.e(TAG, "downX=" + downX);
                Log.e(TAG, "downY=" + downY);
                videoViewWidth = view.getWidth();
                videoViewHeight = view.getHeight();
                moveType = -1;
                firstDownIsLeft = downX < (videoViewWidth / 2f);
                if (firstDownIsLeft) {
                    currentBrightness = OrientationUtils.getInstance().getActivity(getContext()).getWindow().getAttributes().screenBrightness;
                    if (currentBrightness <= 0.00f) {
                        currentBrightness = 0.50f;
                    } else if (currentBrightness < 0.01f) {
                        currentBrightness = 0.01f;
                    }
                }
                currentVolume = PlayerManager.getInstance().getPlayer().getVolume();
                actionDownVideoTime = PlayerManager.getInstance().getPlayer().getCurrentPosition();
                if (videoDurationTime == -1) {
                    videoDurationTime = PlayerManager.getInstance().getPlayer().getDuration();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float absDeltaX = Math.abs(event.getX() - downX);
                float absDeltaY = Math.abs(event.getY() - downY);
                if (absDeltaX < defaultMoveLength && absDeltaY < defaultMoveLength) {
                    return;
                }
                if (moveType == -1) {
                    Log.e(TAG, "event.getX=" + event.getX());
                    Log.e(TAG, "downX=" + downX);
                    Log.e(TAG, "absDeltaX=" + absDeltaX);

                    Log.e(TAG, "event.getY=" + event.getY());
                    Log.e(TAG, "downY=" + downY);
                    Log.e(TAG, "absDeltaY=" + absDeltaY);
                    moveType = (absDeltaX < absDeltaY) ? 0 : 1;
                    Log.e(TAG, "moveType=" + moveType);
                }
                //垂直滑动的时候
                if (moveType == 0) {
                    int changePercent = -(int) (((event.getY() - downY) / videoViewHeight) * 100);
                    if (lastChangPercent == changePercent) {
                        return;
                    }
                    if (firstDownIsLeft) {
                        showBrightnessChange(changePercent);
                        return;
                    }
                    showVolumeChange(changePercent);
                    return;
                }
                //快进或者快退
                offsetDis = event.getX() - downX;
                offsetTime = (long) ((offsetDis / videoViewWidth) * 1000 * 80);

                arrowDirection = (event.getX() - lastDownX) < 0 ? 0 : 1;
                lastDownX = event.getX();
                lastVideoPlayTime = offsetTime + actionDownVideoTime;
                if (lastVideoPlayTime < 0)
                    lastVideoPlayTime = 0;
                if (lastVideoPlayTime > videoDurationTime)
                    lastVideoPlayTime = videoDurationTime;
                showProgressChange(lastVideoPlayTime, arrowDirection, videoDurationTime);
                // 告知底部seek 跟随手指滑动改变seek 当前进度
                if (userProgressListener != null) {
                    userProgressListener.onUserProgress(PlayerConstants.USER_PROGRESS_START, lastVideoPlayTime);
                }
                //快进快退过程中，隐藏播放或者加载的按钮或者动画
                hideLoadingAndPlayIcon();
                break;
            case MotionEvent.ACTION_UP:
                lastChangPercent = -1;
                currentBrightness = -1;
                currentVolume = -1;
                dismissAllView();
                if (userProgressListener != null && moveType == 1) {
                    userProgressListener.onUserProgress(PlayerConstants.USER_PROGRESS_END, lastVideoPlayTime);
                }
                moveType = -1;
                break;
        }
    }

    public void handleDoubleTap(MotionEvent event) {
        float x = event.getRawX();
        int W = UiUtils.getWindowWidth();
        if (x < W * 0.4) {
            onLeftDoubleTop();
        } else if (x > W * 0.7) {//右边
            onRightDoubleTap();
        }
    }

    /**
     * 左边双击了
     */
    public void onLeftDoubleTop() {

    }

    /**
     * 右边双击了
     */
    public void onRightDoubleTap() {

    }


    public abstract void showBrightnessChange(int changePercent);

    public abstract void showVolumeChange(int changePercent);

    /**
     * arrowDirection :0 左边
     * arrowDirection ：1 右边
     */
    public abstract void showProgressChange(long lastVideoPlayTime, int arrowDirection, long videoDurationTime);


    protected abstract void dismissAllView();

    public abstract void setOnCenterClickListener(OnClickListener listener);

    public void onScreenOrientationChanged(int currentOrientation) {
        Log.e("BaseBottomView", "user ScreenOrientationChanged=" + currentOrientation);
    }

    public ImageView getCenterPlayView() {
        return img_status;
    }
    public void reset(){}
}
