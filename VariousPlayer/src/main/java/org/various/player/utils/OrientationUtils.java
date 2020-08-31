package org.various.player.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.view.OrientationEventListener;
import android.view.View;

/**
 * Created by 江雨寒 on 2020/8/21
 * Email：847145851@qq.com
 * func: ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
 */
public class OrientationUtils {
    public static int Orientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    private Activity activity;
    private OrientationEventListener mOrientationEventListener;
    public OrientationUtils(Context context) {
        this.activity = getActivity(context);
        init();
    }

    private void init() {
        mOrientationEventListener=new OrientationEventListener(activity) {
            @Override
            public void onOrientationChanged(int rotation) {

            }
        };
    }

    private static Activity getActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return getActivity(((ContextWrapper) context).getBaseContext());
        }

        return null;
    }

    public  void setRequestedOrientation(int orientation) {
        if (activity!=null){
            Orientation=orientation;
            activity.setRequestedOrientation(
                    orientation);
            activity.getWindow().getDecorView().setSystemUiVisibility(orientation==ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE?getFullscreenUiFlags():getStableUiFlags());
        }



    }
    public  int getOrientation(){
        if (activity!=null){
          return   activity.getRequestedOrientation();
        }
       
        return   -1;
    }
    public void release(){
        activity=null;
        if (mOrientationEventListener!=null){
            mOrientationEventListener.disable();
        }
    }

    private int getFullscreenUiFlags() {
        return  View.SYSTEM_UI_FLAG_FULLSCREEN ;
    }

    private int getStableUiFlags() {
        return View.SYSTEM_UI_FLAG_VISIBLE;
    }
}
