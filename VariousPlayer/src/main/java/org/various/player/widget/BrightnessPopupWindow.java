package org.various.player.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import org.various.player.R;
import org.various.player.utils.OrientationUtils;
import org.various.player.utils.UiUtils;


/**
 * Created by 江雨寒 on 2020/9/14
 * Email：847145851@qq.com
 * func:
 */
public class BrightnessPopupWindow {
    Context context;
    protected PopupWindow mBrightnessPopup;
    protected ProgressBar mBrightnessPopupProgressBar;
    View localView;
    int offsetX,  offsetY;
    public BrightnessPopupWindow(Context context) {
        this.context = context;
    }
    public void showBrightnessChange(int changePercent,View view,float currentBrightness){
        Log.e("BrightnessPopupWindow", "showBrightnessChange=" + changePercent);
        float lastBrightness = currentBrightness + changePercent * 0.01f;
        WindowManager.LayoutParams layoutParams = OrientationUtils.getActivity(context).getWindow().getAttributes();
        layoutParams.screenBrightness = lastBrightness;
        if (layoutParams.screenBrightness > 1.0f) {
            layoutParams.screenBrightness = 1.0f;
        } else if (layoutParams.screenBrightness < 0.01f) {
            layoutParams.screenBrightness = 0f;
        }
        Log.e("BrightnessPopupWindow", "layoutParams.screenBrightness=" + layoutParams.screenBrightness);
        OrientationUtils.getActivity(context).getWindow().setAttributes(layoutParams);
        if (mBrightnessPopup == null) {
            localView = LayoutInflater.from(context).inflate(R.layout.various_popup_brightness, null);
            mBrightnessPopupProgressBar =   localView.findViewById(R.id.brightness_progressbar) ;
            mBrightnessPopupProgressBar.setMax(100);
            mBrightnessPopup = getPopupWindow(localView);
            localView.measure(0,0);
            offsetX=(view.getWidth()-localView.getMeasuredWidth())/2;
            offsetY=-(view.getHeight()-UiUtils.dip2px(24));
        }
        if (!mBrightnessPopup.isShowing())
            mBrightnessPopup.showAsDropDown(view, offsetX, offsetY);
        mBrightnessPopupProgressBar.setProgress((int) (lastBrightness * 100));
    }
    private PopupWindow getPopupWindow(View popupView) {
        PopupWindow mPopupWindow = new PopupWindow(popupView, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        mPopupWindow.setAnimationStyle(R.style.popup_brightness_volume_anim);
        return mPopupWindow;
    }
    public void dismissPop(){
        if (mBrightnessPopup!=null&&mBrightnessPopup.isShowing()){
            mBrightnessPopup.dismiss();
        }
    }
}
