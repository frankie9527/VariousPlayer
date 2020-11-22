package org.various.player.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.various.player.R;
import org.various.player.utils.TimeFormatUtil;


/**
 * Created by 江雨寒 on 2020/9/14
 * Email：847145851@qq.com
 * func:
 */
public class ProgressPopupWindow {
    Context context;
    protected PopupWindow mProgressPopup;
    ImageView img_arrow;
    TextView tv_current, tv_total;
    ProgressBar video_progressbar;
    View localView;
    int offsetX, offsetY;

    public ProgressPopupWindow(Context context) {
        this.context = context;
    }

    public void showProgressChange(View view, long lastVideoPlayTime, int arrowDirection, long videoDurationTime) {

        String str = arrowDirection <= 0 ? "左" : "右";
        String playStr = TimeFormatUtil.formatMs(lastVideoPlayTime);
        String allStr = TimeFormatUtil.formatMs(videoDurationTime);
        Log.e("ProgressPopupWindow", "str="+str);
        Log.e("ProgressPopupWindow", "playStr="+playStr);
        Log.e("ProgressPopupWindow", "allStr="+allStr);
        if (mProgressPopup == null) {
            localView = LayoutInflater.from(context).inflate(R.layout.various_popup_progress, null);
            img_arrow = localView.findViewById(R.id.img_arrow);
            tv_current = localView.findViewById(R.id.tv_current);
            tv_total = localView.findViewById(R.id.tv_total);
            video_progressbar = localView.findViewById(R.id.video_progressbar);
            video_progressbar.setMax(100);
            mProgressPopup = getPopupWindow(localView);
        }
        localView.measure(0, 0);
        offsetX = (view.getWidth() - localView.getMeasuredWidth()) / 2;
        offsetY = -(view.getHeight() + localView.getMeasuredHeight()) / 2;
        if (!mProgressPopup.isShowing()) {
            mProgressPopup.showAsDropDown(view, offsetX, offsetY);
        }

        img_arrow.setImageDrawable(ContextCompat.getDrawable(context, arrowDirection <= 0 ? R.drawable.video_playback : R.drawable.video_forward));
        tv_current.setText(playStr);
        tv_total.setText("/ " + allStr);
        int num = (int) ((lastVideoPlayTime * 1.0f) / (videoDurationTime * 1.0f) * 100);
        video_progressbar.setProgress(num);
        Log.e("ProgressPopupWindow", "num=" + num);
    }

    public void dismissPop() {
        if (mProgressPopup != null && mProgressPopup.isShowing()) {
            mProgressPopup.dismiss();
        }
    }

    private PopupWindow getPopupWindow(View popupView) {
        PopupWindow mPopupWindow = new PopupWindow(popupView, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        mPopupWindow.setAnimationStyle(R.style.popup_brightness_volume_anim);
        return mPopupWindow;
    }
}
