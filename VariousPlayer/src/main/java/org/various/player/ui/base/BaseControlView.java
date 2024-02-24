package org.various.player.ui.base;

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
import org.various.player.core.PlayerManager;
import org.various.player.listener.UserProgressListener;
import org.various.player.ui.base.impl.IVideoControl;
import org.various.player.PlayerConstants;
import org.various.player.listener.UserActionListener;
import org.various.player.listener.UserChangeOrientationListener;
import org.various.player.utils.OrientationUtils;


/**
 * Created by 江雨寒 on 2020/8/19
 * Email：847145851@qq.com
 * func:
 */
public abstract class BaseControlView<T extends BaseTopView, B extends BaseBottomView, C extends BaseCenterView> extends FrameLayout implements IVideoControl, View.OnClickListener, UserProgressListener {
    private final String TAG = "BaseControlView";
    public T topView;
    public B bottomView;
    public C centerView;
    private String url;

    private boolean inRecycler = false;


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
                    //在Recycler 里面且 ui和播放的url一致，并且在播放状态。则正常隐藏centerView
                    if (isInRecycler() && getUrl().equals(PlayerManager.getInstance().getPlayer().getVideoUrl()) && PlayerManager.getInstance().getPlayer().isPlaying()) {
                        centerView.hideAll();
                    } else {
                        centerView.hideAllExceptPlayIcon();
                    }
                    break;
            }
        }
    };

    @Override
    public void setOrientationListener(UserChangeOrientationListener orientationListener) {
        this.orientationListener = orientationListener;
    }

    UserChangeOrientationListener orientationListener;

    public BaseControlView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public BaseControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BaseControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        Log.e(TAG, "stateReady");
        centerView.showStatus();
        mUiHandler.sendEmptyMessageDelayed(HIDE_ALL, 5000);
        bottomView.startRepeater();
    }

    @Override
    public void showTopAndBottom() {
        Log.e(TAG, "showTopAndBottom");
        if (topView != null) {
            topView.setVisibleStatus(PlayerConstants.SHOW);
        }
        if (bottomView != null) {
            bottomView.setVisibleStatus(PlayerConstants.SHOW);
        }
    }

    @Override
    public void hideTopAndBottom() {
        Log.e(TAG, "hideTopAndBottom");
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

    public boolean isInRecycler() {
        return inRecycler;
    }

    public void setInRecycler(boolean inRecycler) {
        this.inRecycler = inRecycler;
    }

    @Override
    public void onClick(View view) {
        if (topView != null && view == topView./**/getBackView() && orientationListener != null) {
            if (OrientationUtils.Orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                orientationListener.changeOrientation();
                topView.onScreenOrientationChanged(OrientationUtils.Orientation);
                centerView.onScreenOrientationChanged(OrientationUtils.Orientation);
                bottomView.onScreenOrientationChanged(OrientationUtils.Orientation);
                return;
            }
            if (userActionListener != null)
                userActionListener.onUserAction(PlayerConstants.ACTION_BACK);
        }
        if (bottomView != null && view == bottomView.getImgSwitchScreen()) {
            if (orientationListener != null) {
                orientationListener.changeOrientation();
                topView.onScreenOrientationChanged(OrientationUtils.Orientation);
                centerView.onScreenOrientationChanged(OrientationUtils.Orientation);
                bottomView.onScreenOrientationChanged(OrientationUtils.Orientation);
                mUiHandler.removeMessages(HIDE_ALL);
                mUiHandler.sendEmptyMessageDelayed(HIDE_ALL, 5000);
            }
        }
        if (bottomView != null && view == centerView.getCenterPlayView()) {
            if (PlayerManager.getInstance().getPlayer().isPlaying()) {
                PlayerManager.getInstance().getPlayer().pause();
                return;
            }
            int currentStatus = PlayerManager.getInstance().getCurrentStatus();
            long currentPosition = PlayerManager.getInstance().getPlayer().getCurrentPosition();
            String url = PlayerManager.getInstance().getPlayer().getVideoUrl();
            if (currentStatus == PlayerConstants.IDLE && currentPosition == 0 && !TextUtils.isEmpty(url)) {
                PlayerManager.getInstance().getPlayer().startSyncPlay();
                return;
            }
            PlayerManager.getInstance().getPlayer().resume();
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
            if (PlayerManager.getInstance().getCurrentStatus() == PlayerConstants.READY || PlayerManager.getInstance().getCurrentStatus() == PlayerConstants.BUFFERING) {
                centerView.handleTouch(view, event);
            }
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
            Log.e(TAG, "onSingleTapConfirmed=" + (bottomView.getVisibility() != VISIBLE));
             //当前播放器url 和当前ui不一致，则只显示播放图标
            if ((!TextUtils.isEmpty(getUrl()) && !getUrl().equals(PlayerManager.getInstance().getPlayer().getVideoUrl()) && isInRecycler())) {
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
            if (PlayerManager.getInstance().getCurrentStatus() == PlayerConstants.END || PlayerManager.getInstance().getCurrentStatus() == PlayerConstants.ERROR) {
                showTopAndBottom();
                centerView.showStatus();
                return true;
            }
            if (bottomView.getVisibility() != VISIBLE) {
                showTopAndBottom();
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
            if (PlayerManager.getInstance().getCurrentStatus() == PlayerConstants.READY || PlayerManager.getInstance().getCurrentStatus() == PlayerConstants.BUFFERING) {
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
}
