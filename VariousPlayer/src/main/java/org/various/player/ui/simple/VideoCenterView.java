package org.various.player.ui.simple;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.various.player.NotificationCenter;
import org.various.player.PlayerConstants;
import org.various.player.R;
import org.various.player.core.PlayerManager;
import org.various.player.ui.base.BaseCenterView;
import org.various.player.utils.OrientationUtils;
import org.various.player.utils.TimeFormatUtil;
import org.various.player.utils.UiUtils;

/**
 * Created by 江雨寒 on 2020/8/14
 * Email：847145851@qq.com
 * func:
 */
public class VideoCenterView extends BaseCenterView {
    private static final String TAG = "VideoCenterView";
    ProgressBar video_progress;
    ImageView img_status;
    RelativeLayout rl_play_err;
    TextView tv_replay;

    public VideoCenterView(Context context) {
        super(context);

    }

    public VideoCenterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public VideoCenterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.various_simple_view_center;
    }

    @Override
    public void initView(Context context) {
        super.initView(context);
        video_progress = findViewById(R.id.video_progress);
        img_status = findViewById(R.id.img_status);
        img_status.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PlayerManager.getPlayer().isPlaying()) {
                    PlayerManager.getPlayer().pause();
                    return;
                }
                PlayerManager.getPlayer().resume();
            }
        });
        rl_play_err = findViewById(R.id.rl_play_err);
        tv_replay = findViewById(R.id.tv_replay);
        tv_replay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.user_onclick_video_err_retry);
            }
        });

    }

    @Override
    public void showLoading() {
        viewSetVisible(this);
        viewSetGone(img_status);
        viewSetGone(rl_play_err);
        viewSetVisible(video_progress);
    }

    @Override
    public void hideLoading() {
        viewSetGone(video_progress);
        viewSetGone(this);
    }

    @Override
    public void showEnd() {
        viewSetVisible(this);
        viewSetGone(video_progress);
        img_status.setVisibility(VISIBLE);
        img_status.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.video_replay));
    }

    @Override
    public void showError() {
        viewSetVisible(this);
        viewSetGone(video_progress);
        viewSetVisible(rl_play_err);
    }

    @Override
    public void hideAll() {
        viewSetGone(video_progress);
        viewSetGone(img_status);
        viewSetGone(rl_play_err);
        viewSetGone(this);
    }

    @Override
    public void showStatus() {
        setVisibility(View.VISIBLE);
        int type = PlayerManager.getCurrentStatus();
        Log.e(TAG, "type=" + type);
        viewSetGone(video_progress);
        viewSetGone(img_status);
        viewSetGone(rl_play_err);
        switch (type) {
            case PlayerConstants.READY:
                viewSetVisible(img_status);
                img_status.setImageDrawable(ContextCompat.getDrawable(getContext(), PlayerManager.getPlayer().isPlaying() ? R.drawable.video_pause : R.drawable.video_play));
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

    protected PopupWindow mBrightnessPopup;
    protected ProgressBar mBrightnessPopupProgressBar;

    @Override
    public void showBrightnessChange(int changePercent) {
        Log.e(TAG, "showBrightnessChange=" + changePercent);
        float lastBrightness = currentBrightness + changePercent * 0.01f;
        WindowManager.LayoutParams layoutParams = OrientationUtils.getActivity(getContext()).getWindow().getAttributes();
        layoutParams.screenBrightness = lastBrightness;
        if (layoutParams.screenBrightness > 1.0f) {
            layoutParams.screenBrightness = 1.0f;
        } else if (layoutParams.screenBrightness < 0.01f) {
            layoutParams.screenBrightness = 0f;
        }
        Log.e(TAG, "layoutParams.screenBrightness=" + layoutParams.screenBrightness);
        OrientationUtils.getActivity(getContext()).getWindow().setAttributes(layoutParams);

        if (mBrightnessPopup == null) {
            View localView = LayoutInflater.from(getContext()).inflate(R.layout.various_popup_brightness, null);

            mBrightnessPopupProgressBar = ((ProgressBar) localView.findViewById(R.id.brightness_progressbar));
            mBrightnessPopupProgressBar.setMax(100);

            mBrightnessPopup = getPopupWindow(localView);
        }
        if (!mBrightnessPopup.isShowing())
            mBrightnessPopup.showAtLocation(this, Gravity.TOP, 0, UiUtils.dip2px(20));
        mBrightnessPopupProgressBar.setProgress((int) (lastBrightness * 100));
    }

    protected PopupWindow mVolumePopup;
    protected ProgressBar mVolumeProgressBar;

    @Override
    public void showVolumeChange(int changePercent) {
        Log.e(TAG, "showVolumeChange=" + changePercent);
        int offsetVolume = (int) (maxVolume * changePercent * 0.01f);
        int nowVolume = currentVolume + offsetVolume;
        if (nowVolume < 0)
            nowVolume = 0;
        if (nowVolume > maxVolume)
            nowVolume = maxVolume;
        Log.e(TAG, "nowVolume=" + nowVolume);
        getAudioManager().setStreamVolume(AudioManager.STREAM_MUSIC, nowVolume, 0);

        if (mVolumePopup == null) {
            View localView = LayoutInflater.from(getContext()).inflate(R.layout.various_popup_volume, null);
            mVolumeProgressBar = localView.findViewById(R.id.brightness_progressbar);
            mVolumeProgressBar.setMax(maxVolume);
            mVolumePopup = getPopupWindow(localView);
        }
        if (!mVolumePopup.isShowing())
            mVolumePopup.showAtLocation(this, Gravity.TOP, 0, UiUtils.dip2px(20));
        mVolumeProgressBar.setProgress(nowVolume);
    }

    @Override
    public void showProgressChange(long lastVideoPlayTime, int arrowDirection, long videoDurationTime) {
        String str = arrowDirection <= 0 ? "左" : "右";
        String playStr = TimeFormatUtil.formatMs(lastVideoPlayTime);
        String allStr = TimeFormatUtil.formatMs(videoDurationTime);

        Log.e(TAG, "arrowDirection=" + arrowDirection+" str="+str);


    }

    @Override
    protected void dismissAllView() {
        if (mVolumePopup != null && mVolumePopup.isShowing()) {
            mVolumePopup.dismiss();
        }
        if (mBrightnessPopup != null && mBrightnessPopup.isShowing()) {
            mBrightnessPopup.dismiss();
        }
    }

    public void viewSetGone(View view) {
        if (view.getVisibility() == VISIBLE) {
            view.setVisibility(GONE);
        }
    }

    public void viewSetVisible(View view) {
        if (view.getVisibility() != VISIBLE) {
            view.setVisibility(VISIBLE);
        }
    }

    private PopupWindow getPopupWindow(View popupView) {
        PopupWindow mPopupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        mPopupWindow.setAnimationStyle(R.style.popup_brightness_volume_anim);
        return mPopupWindow;
    }
}
