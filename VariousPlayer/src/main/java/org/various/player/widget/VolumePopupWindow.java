package org.various.player.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import org.various.player.R;
import org.various.player.core.PlayerManager;
import org.various.player.utils.UiUtils;


/**
 * Created by 江雨寒 on 2020/9/14
 * Email：847145851@qq.com
 * func:
 */
public class VolumePopupWindow {
    Context context;
    protected PopupWindow mVolumePopup;
    protected ProgressBar mVolumeProgressBar;
    View localView;
    int offsetX,  offsetY;
    public VolumePopupWindow(Context context) {
        this.context = context;
    }
    private PopupWindow getPopupWindow(View popupView) {
        PopupWindow mPopupWindow = new PopupWindow(popupView, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        mPopupWindow.setAnimationStyle(R.style.popup_brightness_volume_anim);
        return mPopupWindow;
    }
    public void showVolumeChange(int changePercent,View view,float currentVolume){
        float nowVolume=changePercent* 0.01f+currentVolume;
        if (nowVolume < 0)
            nowVolume = 0;
        if (nowVolume > 1.0f)
            nowVolume = 1.0f;
        Log.e("VolumePopupWindow", "nowVolume=" + nowVolume);
        PlayerManager.getPlayer().setVolume(nowVolume);
        if (mVolumePopup == null) {
             localView = LayoutInflater.from(context).inflate(R.layout.various_popup_volume, null);
            mVolumeProgressBar = localView.findViewById(R.id.brightness_progressbar);
            mVolumeProgressBar.setMax(100);
            mVolumePopup = getPopupWindow(localView);
            localView.measure(0,0);
            offsetX=(view.getWidth()-localView.getMeasuredWidth())/2;
            offsetY=-(view.getHeight()-UiUtils.dip2px(24));


        }
        if (!mVolumePopup.isShowing())
            mVolumePopup.showAsDropDown(view, offsetX, offsetY);
        mVolumeProgressBar.setProgress((int) (nowVolume*100));
    }

    public void dismissPop(){
        if (mVolumePopup!=null&&mVolumePopup.isShowing()){
            mVolumePopup.dismiss();
        }
    }
}
