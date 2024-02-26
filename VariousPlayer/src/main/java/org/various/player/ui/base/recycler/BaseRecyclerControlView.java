package org.various.player.ui.base.recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.various.player.PlayerConstants;

import org.various.player.core.VariousPlayerManager;
import org.various.player.listener.UiOrientationListener;
import org.various.player.listener.UserActionListener;
import org.various.player.listener.UserChangeOrientationListener;
import org.various.player.listener.UserProgressListener;
import org.various.player.ui.base.impl.IVideoControl;
import org.various.player.utils.LogUtils;
import org.various.player.utils.OrientationUtils;


/**
 * Created by Frankie on 2020/8/19
 * Email：847145851@qq.com
 * func:
 */
public abstract class BaseRecyclerControlView<T extends BaseRecyclerTopView, B extends BaseRecyclerBottomView, C extends BaseRecyclerCenterView> extends FrameLayout implements IVideoControl, View.OnClickListener, UserProgressListener, UiOrientationListener {
    private final String TAG = "BaseRecyclerControlView";
    public T topView;
    public B bottomView;
    public C centerView;
    private String url;


    public C getCentView() {
        return centerView;
    }

    UserActionListener userActionListener;
    TouchListener touchListener;
    public final int SHOW_TOP_AND_BOTTOM = 0;
    public final int HIDE_ALL = 1;
    public Handler mUiHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case SHOW_TOP_AND_BOTTOM:
                    showTopAndBottom();
                    break;
                case HIDE_ALL:
                    hideTopAndBottom();
                    //底部的进度条为0，说明是初始化状态！中间图标不应该隐藏
                    if (bottomView.video_seek.getProgress() == 0) {
                        return;
                    }
                    centerView.hideAll();
                    break;
            }
        }
    };

    @Override
    public void setOrientationListener(UserChangeOrientationListener orientationListener) {
        this.orientationListener = orientationListener;
    }

    UserChangeOrientationListener orientationListener;

    public BaseRecyclerControlView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public BaseRecyclerControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BaseRecyclerControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    protected abstract int setLayoutId();

    protected abstract int setTopViewId();

    protected abstract int setBottomViewId();

    protected abstract int setCenterViewId();


    public void initView(Context context) {
        View.inflate(context, setLayoutId(), this);
        initTopView(setTopViewId());
        initBottomView(setBottomViewId());
        initCenterView(setCenterViewId());
        touchListener = new TouchListener(getContext());
        setOnTouchListener(touchListener);

    }

    protected void initTopView(int id) {
        if (id == 0) {
            return;
        }
        topView = findViewById(id);
        topView.setOnTopClickListener(this);

    }

    protected void initBottomView(int id) {
        if (id == 0) {
            return;
        }
        bottomView = findViewById(id);
        bottomView.setOnBottomClickListener(this);
        bottomView.setDragSeekListener(this);
    }

    protected void initCenterView(int id) {
        if (id == 0) {
            return;
        }
        centerView = findViewById(id);
        centerView.setUserProgressListener(this);
        centerView.setOnCenterClickListener(this);
    }

    @Override
    public void setTitle(String title) {
        topView.setTitle(title);
    }

    @Override
    public void stateBuffering() {
        centerView.showLoading();
        hideTopAndBottom();
    }

    @Override
    public void stateReady() {
        LogUtils.e(TAG, "stateReady");
        centerView.showStatus();
        mUiHandler.sendEmptyMessageDelayed(HIDE_ALL, 5000);
        bottomView.startRepeater();
    }

    @Override
    public void showTopAndBottom() {
        LogUtils.e(TAG, "showTopAndBottom");
        //全屏
        boolean landscape = OrientationUtils.getInstance().getOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        if (topView != null && landscape) {
            topView.setVisibleStatus(PlayerConstants.SHOW);
        }
        //全屏
        if (bottomView != null && landscape) {
            bottomView.setVisibleStatus(PlayerConstants.SHOW);
            //竖屏 且播放地址一致
        } else if (bottomView != null && !TextUtils.isEmpty(url) && url.equals(VariousPlayerManager.getVideoUrl())) {
            bottomView.setVisibleStatus(PlayerConstants.SHOW);
        }
    }

    @Override
    public void hideTopAndBottom() {
        LogUtils.e(TAG, "hideTopAndBottom");
        if (topView != null) {
            topView.setVisibleStatus(PlayerConstants.HIDE);
        }
        if (bottomView != null) {
            bottomView.setVisibleStatus(PlayerConstants.HIDE);
        }
    }

    @Override
    public void showComplete() {
        showTopAndBottom();
        centerView.showEnd();
    }

    @Override
    public void showError() {
        centerView.showError();
    }

    @Override
    public void setUserActionListener(UserActionListener listener) {
        this.userActionListener = listener;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public void onClick(View view) {
        LogUtils.d(TAG, "onClick");
        if (topView != null && view == topView.getBackView()) {
            if (OrientationUtils.getInstance().getOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                quitFullScreen();
            }
            if (userActionListener != null)
                userActionListener.onUserAction(PlayerConstants.ACTION_BACK);
        }
        if (bottomView != null && view == bottomView.getImgSwitchScreen()) {
            if (OrientationUtils.getInstance().getOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                quitFullScreen();
            } else {
                startFullScreen();
            }
        }
        if (centerView.getCenterReplayView() != null && view == centerView.getCenterReplayView()) {
            VariousPlayerManager.playRetry();
        }
    }

    @Override
    public void onUserProgress(int type, long time) {
        if (type == PlayerConstants.USER_DRAG_START) {
            mUiHandler.removeMessages(HIDE_ALL);
            return;
        }
        if (type == PlayerConstants.USER_DRAG_END) {
            stateBuffering();
            return;
        }
        if (bottomView != null) {
            bottomView.changSeekBar(type, time);
        }
    }


    protected class TouchListener extends GestureDetector.SimpleOnGestureListener implements OnTouchListener {

        protected GestureDetector gestureDetector;

        public TouchListener(Context context) {
            gestureDetector = new GestureDetector(context, this);
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            gestureDetector.onTouchEvent(event);
            if (bottomView.video_seek.getProgress() != 0 && VariousPlayerManager.getCurrentStatus() != PlayerConstants.ERROR) {
                centerView.handleTouch(view, event);
            }
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
            LogUtils.e(TAG, "onSingleTapConfirmed=" + (bottomView.getVisibility() != VISIBLE));
            //当前播放器url 和当前ui不一致，则只显示播放图标
            long playProgress = bottomView.video_seek.getProgress();
            if (playProgress == 0) {
                if (topView != null) {
                    topView.hide();
                }
                if (bottomView != null) {
                    bottomView.hide();
                }
                if (centerView != null) {
                    centerView.reset();
                }
                return true;
            }
            if (VariousPlayerManager.getCurrentStatus() == PlayerConstants.END) {
                showTopAndBottom();
                centerView.showStatus();
                return true;
            }
            if (VariousPlayerManager.getCurrentStatus() == PlayerConstants.ERROR) {
                return true;
            }
            if (bottomView.getVisibility() != VISIBLE) {
                if (VariousPlayerManager.isPlaying()) {
                    showTopAndBottom();
                }
                centerView.showStatus();
                mUiHandler.removeMessages(HIDE_ALL);
                mUiHandler.sendEmptyMessageDelayed(HIDE_ALL, 5000);

            } else {
                mUiHandler.removeMessages(HIDE_ALL);
                mUiHandler.sendEmptyMessage(HIDE_ALL);
            }
            return true;
        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            if (VariousPlayerManager.getCurrentStatus() == PlayerConstants.READY || VariousPlayerManager.getCurrentStatus() == PlayerConstants.BUFFERING) {
                centerView.handleDoubleTap(e);
            }
            return super.onDoubleTap(e);
        }
    }

    @Override
    public void resetVideoView() {
        if (topView != null) {
            topView.hide();
        }
        if (centerView != null) {
            centerView.reset();
        }
        if (bottomView != null) {
            bottomView.reset();
        }
    }

    protected ViewGroup.LayoutParams blockLayoutParams;
    protected int blockIndex;
    protected int blockWidth;
    protected int blockHeight;
    protected ViewGroup parent;
    protected ViewGroup videoView;
    public long userChangeOrientationTime;

    public void startFullScreen() {
        userChangeOrientationTime = System.currentTimeMillis();
        Activity activity = OrientationUtils.getInstance().getActivity(getContext());
        if (activity == null) {
            return;
        }
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        //从当前视图中移除播放器视图
        videoView = (ViewGroup) getParent();
        parent = (ViewGroup) videoView.getParent();
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

    public void hideSysBar(Context context) {
        Activity activity = OrientationUtils.getInstance().getActivity(context);
        if (activity == null) {
            return;
        }
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        int uiOptions = decorView.getSystemUiVisibility();
        uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        decorView.setSystemUiVisibility(uiOptions);
        OrientationUtils.getInstance().getActivity(getContext()).getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    public void onOrientationChanged() {
        LogUtils.d(TAG, "onOrientationChanged");
        if (topView != null) {
            topView.onScreenOrientationChanged();
        }
        if (centerView != null) {
            centerView.onScreenOrientationChanged();
        }
        if (bottomView != null) {
            bottomView.onScreenOrientationChanged();
        }
    }

    public void initUiOrientationListener() {
        OrientationUtils.getInstance().setUiOrientationListener(this);
    }
}
