package org.various.player.ui.base;
import android.content.Context;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.various.player.PlayerConfig;
import org.various.player.PlayerConstants;
import org.various.player.core.PlayerManager;
import org.various.player.listener.UserProgressListener;
import org.various.player.utils.OrientationUtils;

/**
 * Created by 江雨寒 on 2020/8/19
 * Email：847145851@qq.com
 * func:
 */
public abstract class BaseCenterView extends FrameLayout {
    private static final String TAG = "BaseCenterView";
    private float downX, downY;
    private int videoViewWidth, videoViewHeight;
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
    private boolean fristDownIsLeft = false;
    /**
     * 上一次音量或者屏幕亮度值
     */
    private int lastChangPercent = -1;
    public AudioManager audioManager;
    public int maxVolume = -1;
    public int currentVolume = -1;
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

    private   UserProgressListener userProgressListener;
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
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        Log.e(TAG, "maxVolume=" + maxVolume);
    }

    public abstract void showLoading();

    public abstract void hideLoading();

    public abstract void showEnd();

    public abstract void showError();

    public abstract void hideAll();

    public abstract void showStatus();

    public void handleTouch(View view, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastDownX = downX = event.getX();
                downY = event.getY();
                videoViewWidth = view.getWidth();
                videoViewHeight = view.getHeight();
                moveType = -1;
                fristDownIsLeft = downX < (videoViewWidth / 2);
                if (fristDownIsLeft) {
                    currentBrightness = OrientationUtils.getActivity(getContext()).getWindow().getAttributes().screenBrightness;
                    if (currentBrightness <= 0.00f) {
                        currentBrightness = 0.50f;
                    } else if (currentBrightness < 0.01f) {
                        currentBrightness = 0.01f;
                    }
                }
                if (maxVolume == -1) {
                    maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

                }
                currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                actionDownVideoTime = PlayerManager.getPlayer().getCurrentPosition();
                if (videoDurationTime == -1) {
                    videoDurationTime = PlayerManager.getPlayer().getDuration();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (downX < defaultMoveLength && downY < defaultMoveLength) {
                    return;
                }
                if (moveType == -1) {
                    float absDeltaX = Math.abs(event.getX() - downX);
                    float absDeltaY = Math.abs(event.getY() - downY);
                    moveType = (absDeltaX < absDeltaY) ? 0 : 1;
                }
                //垂直滑动的时候
                if (moveType == 0) {
                    int changePercent = -(int) (((event.getY() - downY) / videoViewHeight) * 100);
                    if (lastChangPercent == changePercent) {
                        return;
                    }
                    if (fristDownIsLeft) {
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
                if (userProgressListener!=null){
                    userProgressListener.onUserProgress(PlayerConstants.USER_PROGRESS_START,lastVideoPlayTime);
                }
                break;
            case MotionEvent.ACTION_UP:
                lastChangPercent = -1;
                currentBrightness = -1;
                currentVolume = -1;
                PlayerConfig.applicationHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismissAllView();
                    }
                },5000);
                if (userProgressListener!=null&&moveType==1){
                    userProgressListener.onUserProgress(PlayerConstants.USER_PROGRESS_END,lastVideoPlayTime);
                }
                break;
        }
    }


    public abstract void showBrightnessChange(int changePercent);

    public abstract void showVolumeChange(int changePercent);

    /**
     * arrowDirection :0 左边
     * arrowDirection ：1 右边
     */
    public abstract void showProgressChange(long lastVideoPlayTime, int arrowDirection, long videoDurationTime);

    public AudioManager getAudioManager() {
        if (audioManager == null) {
            audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        }
        return audioManager;
    }

    protected abstract void dismissAllView();
}
