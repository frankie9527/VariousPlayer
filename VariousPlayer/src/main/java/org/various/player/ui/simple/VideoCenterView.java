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
import org.various.player.widget.BrightnessPopupWindow;
import org.various.player.widget.ProgressPopupWindow;
import org.various.player.widget.VolumePopupWindow;

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
    BrightnessPopupWindow mBrightnessPop;
    VolumePopupWindow   mVolumePop;
    ProgressPopupWindow mProgressPopup;
    public VideoCenterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

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
        mVolumePop=new VolumePopupWindow(context);
        mBrightnessPop=new BrightnessPopupWindow(context);
        mProgressPopup=new ProgressPopupWindow(context);
    }

    @Override
    public void showLoading() {
        UiUtils.viewSetVisible(this);
        UiUtils.viewSetGone(img_status);
        UiUtils.viewSetGone(rl_play_err);
        UiUtils.viewSetVisible(video_progress);
    }

    @Override
    public void hideLoading() {
        UiUtils.viewSetGone(video_progress);
        UiUtils.viewSetGone(this);
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
        mBrightnessPop.showBrightnessChange(changePercent, (View) getParent(),currentBrightness);
    }

    @Override
    public void showVolumeChange(int changePercent) {
        mVolumePop.showVolumeChange(changePercent,(View) getParent(),currentVolume);
    }

    @Override
    public void showProgressChange(long lastVideoPlayTime, int arrowDirection, long videoDurationTime) {
        mProgressPopup.showProgressChange((View) getParent(),lastVideoPlayTime,arrowDirection,videoDurationTime);
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

}
