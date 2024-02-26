package org.various.player.ui.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.various.player.PlayerConstants;
import org.various.player.R;

import org.various.player.core.VariousPlayerManager;
import org.various.player.ui.base.recycler.BaseRecyclerCenterView;
import org.various.player.utils.LogUtils;
import org.various.player.utils.UiUtils;
import org.various.player.widget.BrightnessPopupWindow;
import org.various.player.widget.ProgressPopupWindow;
import org.various.player.widget.VolumePopupWindow;

/**
 * Created by Frankie on 2020/8/14
 * Email：847145851@qq.com
 * func:
 */
public class RecyclerCenterView extends BaseRecyclerCenterView {
    private static final String TAG = "VideoCenterView";
    ProgressBar video_progress;
    BrightnessPopupWindow mBrightnessPop;
    VolumePopupWindow mVolumePop;
    ProgressPopupWindow mProgressPopup;

    public RecyclerCenterView(Context context, @Nullable AttributeSet attrs) {
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
        rl_play_err = findViewById(R.id.rl_play_err);
        tv_replay = findViewById(R.id.tv_replay);
        mVolumePop = new VolumePopupWindow(context);
        mBrightnessPop = new BrightnessPopupWindow(context);
        mProgressPopup = new ProgressPopupWindow(context);
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
        int type = VariousPlayerManager.getCurrentStatus();
        LogUtils.e(TAG, "type=" + type);
        UiUtils.viewSetGone(video_progress);
        UiUtils.viewSetGone(img_status);
        UiUtils.viewSetGone(rl_play_err);
        switch (type) {
            case PlayerConstants.IDLE:
            case PlayerConstants.READY:
                UiUtils.viewSetVisible(img_status);
                img_status.setImageDrawable(ContextCompat.getDrawable(getContext(), VariousPlayerManager.isPlaying() ? R.drawable.video_pause : R.drawable.video_play));
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
            default:
                UiUtils.viewSetVisible(img_status);
                img_status.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.video_play));
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
    public void setOnCenterClickListener(OnClickListener listener) {
        if (img_status != null) {
            img_status.setOnClickListener(listener);
        }
        if (tv_replay != null) {

            tv_replay.setOnClickListener(listener);
        }
    }

    @Override
    public void reset() {
        img_status.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.video_play));
    }
}
