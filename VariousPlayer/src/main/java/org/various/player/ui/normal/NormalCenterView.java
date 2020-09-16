package org.various.player.ui.normal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.various.player.NotificationCenter;
import org.various.player.PlayerConstants;
import org.various.player.R;
import org.various.player.core.PlayerManager;
import org.various.player.ui.base.BaseCenterView;
import org.various.player.utils.ToastUtils;
import org.various.player.utils.UiUtils;
import org.various.player.widget.BrightnessPopupWindow;
import org.various.player.widget.PicPopupWindow;
import org.various.player.widget.ProgressPopupWindow;
import org.various.player.widget.VolumePopupWindow;

/**
 * Created by 江雨寒 on 2020/8/14
 * Email：847145851@qq.com
 * func:
 */
public class NormalCenterView extends BaseCenterView implements View.OnClickListener, NotificationCenter.NotificationCenterDelegate {
    private static final String TAG = "NormalCenterView";
    ProgressBar video_progress;
    ImageView img_status;
    ImageView img_lock_screen;
    ImageView img_take_pic;
    RelativeLayout rl_play_err;

    BrightnessPopupWindow mBrightnessPop;
    VolumePopupWindow mVolumePop;
    ProgressPopupWindow mProgressPopup;
    PicPopupWindow mPicPop;
    int currentOrientation;

    ForwardHandler forwardHandler=new ForwardHandler();
    public static final int USER_ONCLICK_BACKWARD = 0;
    public static final int USER_ONCLICK_FORWARD = 1;
    public static final int DISMISS_FORWARD_BACKWARD = 2;
    private int fingerCount = 0;
    RelativeLayout rl_backward, rl_forward;
    TextView tv_backward,tv_forward;
    AnimationDrawable animate_backward, animate_forward;
    ImageView lottie_left, lottie_right;
    public NormalCenterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.various_normal_view_center;
    }

    @Override
    public void initView(Context context) {
        super.initView(context);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.user_onclick_take_pic_data);
        video_progress = findViewById(R.id.video_progress);
        img_status = findViewById(R.id.img_status);
        img_status.setOnClickListener(this);
        rl_play_err = findViewById(R.id.rl_play_err);
        findViewById(R.id.tv_replay).setOnClickListener(this);
        img_take_pic = findViewById(R.id.img_take_pic);
        img_take_pic.setOnClickListener(this);
        img_lock_screen = findViewById(R.id.img_lock_screen);
        img_lock_screen.setOnClickListener(this);



        mVolumePop = new VolumePopupWindow(context);
        mBrightnessPop = new BrightnessPopupWindow(context);
        mProgressPopup = new ProgressPopupWindow(context);
        mPicPop = new PicPopupWindow(context);


        rl_backward = findViewById(R.id.rl_backward);
        rl_backward.setOnClickListener(this);
        rl_forward = findViewById(R.id.rl_forward);
        rl_forward.setOnClickListener(this);
        tv_backward=findViewById(R.id.tv_backward);
        tv_forward=findViewById(R.id.tv_forward);
        lottie_left=findViewById(R.id.lottie_left);
        lottie_left.setImageResource(R.drawable.anim_arrow_left);
        lottie_right=findViewById(R.id.lottie_right);
        lottie_right.setImageResource(R.drawable.anim_arrow_right);
    }

    @Override
    public void showLoading() {
        UiUtils.viewSetVisible(this);
        UiUtils.viewSetGone(img_status);
        UiUtils.viewSetGone(rl_play_err);
        UiUtils.viewSetVisible(video_progress);
    }

    @Override
    public void hideLoadingAndPlayIcon() {
        UiUtils.viewSetGone(video_progress);
        UiUtils.viewSetGone(img_status);
    }

    @Override
    public void showEnd() {
        UiUtils.viewSetVisible(this);
        UiUtils.viewSetGone(video_progress);
        img_status.setVisibility(VISIBLE);
        img_status.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.video_replay));
    }

    @Override
    public void showError() {
        UiUtils.viewSetVisible(this);
        UiUtils.viewSetGone(video_progress);
        UiUtils.viewSetVisible(rl_play_err);
    }

    @Override
    public void hideAll() {
        UiUtils.viewSetGone(video_progress);
        UiUtils.viewSetGone(img_status);
        UiUtils.viewSetGone(rl_play_err);
        UiUtils.viewSetGone(this);
    }

    @Override
    public void showStatus() {
        setVisibility(View.VISIBLE);
        int type = PlayerManager.getCurrentStatus();
        Log.e(TAG, "type=" + type);
        UiUtils.viewSetGone(video_progress);
        UiUtils.viewSetGone(img_status);
        UiUtils.viewSetGone(rl_play_err);
        switch (type) {
            case PlayerConstants.READY:
                UiUtils.viewSetVisible(img_status);
                img_status.setImageDrawable(ContextCompat.getDrawable(getContext(), PlayerManager.getPlayer().isPlaying() ? R.drawable.video_pause : R.drawable.video_play));
                onScreenOrientationChanged(currentOrientation);
                break;
            case PlayerConstants.BUFFERING:
                showLoading();
                break;
            case PlayerConstants.END:
                showEnd();
                break;
            case PlayerConstants.ERROR:
                showError();
                break;
        }
    }


    @Override
    public void showBrightnessChange(int changePercent) {
        mBrightnessPop.showBrightnessChange(changePercent, (View) getParent(), currentBrightness);
    }

    @Override
    public void showVolumeChange(int changePercent) {
        mVolumePop.showVolumeChange(changePercent, (View) getParent(), currentVolume);
    }

    @Override
    public void showProgressChange(long lastVideoPlayTime, int arrowDirection, long videoDurationTime) {
        mProgressPopup.showProgressChange((View) getParent(), lastVideoPlayTime, arrowDirection, videoDurationTime);
    }

    @Override
    protected void dismissAllView() {
        if (mVolumePop != null) {
            mVolumePop.dismissPop();
        }
        if (mBrightnessPop != null) {
            mBrightnessPop.dismissPop();
        }
        if (mProgressPopup != null) {
            mProgressPopup.dismissPop();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_status) {
            if (PlayerManager.getPlayer().isPlaying()) {
                PlayerManager.getPlayer().pause();
                return;
            }
            PlayerManager.getPlayer().resume();
            return;
        }
        if (v.getId() == R.id.tv_replay) {
            NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.user_onclick_video_err_retry);
            return;
        }
        if (v.getId() == R.id.img_take_pic) {
            NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.user_onclick_take_pic);
            return;
        }
        if (v.getId() == R.id.img_lock_screen) {

            return;
        }
        if (v.getId() == R.id.rl_backward) {
            forwardHandler.sendEmptyMessage(USER_ONCLICK_BACKWARD);
            forwardHandler.removeMessages(DISMISS_FORWARD_BACKWARD);
            return;
        }
        if (v.getId() == R.id.rl_forward) {
            forwardHandler.sendEmptyMessage(USER_ONCLICK_FORWARD);
            forwardHandler.removeMessages(DISMISS_FORWARD_BACKWARD);
        }
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
        if (id == NotificationCenter.user_onclick_take_pic_data) {
            Bitmap bitmap = (Bitmap) args[0];
            mPicPop.showPic((View) getParent(), bitmap);
        }
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (forwardHandler != null) {
            forwardHandler.removeMessages(USER_ONCLICK_BACKWARD);
            forwardHandler.removeMessages(USER_ONCLICK_FORWARD);
            forwardHandler.removeMessages(DISMISS_FORWARD_BACKWARD);
            forwardHandler = null;
        }

    }
    @Override
    public void onScreenOrientationChanged(int currentOrientation) {
        super.onScreenOrientationChanged(currentOrientation);
        this.currentOrientation = currentOrientation;
        if (currentOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            UiUtils.viewSetVisible(img_lock_screen);
            UiUtils.viewSetVisible(img_take_pic);
            return;
        }
        img_lock_screen.setVisibility(INVISIBLE);
        img_take_pic.setVisibility(INVISIBLE);

    }

    @SuppressLint("HandlerLeak")
    private class ForwardHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case USER_ONCLICK_BACKWARD:
                    fingerCount--;
                    tv_backward.setText(fingerCount * 10 + "秒");
                    forwardHandler.sendEmptyMessageDelayed(DISMISS_FORWARD_BACKWARD, 2000);
                    break;
                case USER_ONCLICK_FORWARD:
                    fingerCount++;
                    tv_forward.setText(fingerCount * 10 + "秒");
                    forwardHandler.sendEmptyMessageDelayed(DISMISS_FORWARD_BACKWARD, 2000);
                    break;
                case DISMISS_FORWARD_BACKWARD:
                    UiUtils.viewSetGone(rl_backward);
                    UiUtils.viewSetGone(rl_forward);
                    long goTime = fingerCount * 10 * 1000 + PlayerManager.getPlayer().getCurrentPosition();

                    if (goTime > 0 && goTime < PlayerManager.getPlayer().getDuration()) {
                        PlayerManager.getPlayer().seekTo(goTime);
                    } else {
                        ToastUtils.show( "预期播放不符合影片时长");
                    }
                    break;
            }
        }
    }
    @Override
    public void onLeftDoubleTop() {
        UiUtils.viewSetVisible(rl_backward);
        rl_forward.setVisibility(INVISIBLE);
        if (animate_backward == null) {
            animate_backward = (AnimationDrawable) lottie_left.getDrawable();
        }
        if (!animate_backward.isRunning()) {
            //开启帧动画
            animate_backward.start();
        }
        fingerCount = -1;
        String str = fingerCount * 10 + "秒";
        tv_backward.setText(str);
        forwardHandler.sendEmptyMessageDelayed(DISMISS_FORWARD_BACKWARD, 2000);
    }
    @Override
    public void onRightDoubleTap() {
        UiUtils.viewSetVisible(rl_forward);
        rl_backward.setVisibility(INVISIBLE);
        if (animate_forward == null) {
            animate_forward = (AnimationDrawable) lottie_right.getDrawable();
        }
        if (!animate_forward.isRunning()) {
            //开启帧动画
            animate_forward.start();
        }

        fingerCount = 1;
        String str = fingerCount * 10 + "秒";
        tv_forward.setText(str);
        forwardHandler.sendEmptyMessageDelayed(DISMISS_FORWARD_BACKWARD, 2000);
    }


}
