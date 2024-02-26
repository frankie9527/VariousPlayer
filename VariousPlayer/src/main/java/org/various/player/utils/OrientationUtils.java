package org.various.player.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.WindowManager;

import org.various.player.NotificationCenter;
import org.various.player.listener.UiOrientationListener;

/**
 * Created by Frankie on 2020/8/21
 * Email：847145851@qq.com
 * func: ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
 */
public class OrientationUtils {
    public int currentOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    private Activity activity;
    private OrientationEventListener mOrientationEventListener;

    public void setUiOrientationListener(UiOrientationListener uiOrientationListener) {
        this.uiOrientationListener = uiOrientationListener;
    }

    private UiOrientationListener uiOrientationListener;
    private static volatile OrientationUtils globalInstance;

    public static OrientationUtils getInstance() {
        OrientationUtils localInstance = globalInstance;
        if (localInstance == null) {
            synchronized (NotificationCenter.class) {
                localInstance = globalInstance;
                if (localInstance == null) {
                    globalInstance = localInstance = new OrientationUtils();
                }
            }
        }
        return localInstance;
    }

    public void init(Context context) {
        this.activity = getActivity(context);
        mOrientationEventListener = new OrientationEventListener(activity) {
            @Override
            public void onOrientationChanged(int rotation) {
            }
        };
    }

    public Activity getActivity(Context context) {
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

    public void setRequestedOrientation(int orientation) {
        if (activity != null) {
            currentOrientation = orientation;
            activity.setRequestedOrientation(
                    orientation);
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                activity.getWindow().setAttributes(attrs);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            } else {
                attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                activity.getWindow().setAttributes(attrs);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }
            if (uiOrientationListener != null) {
                uiOrientationListener.onOrientationChanged();
            }
        }
    }

    public int getOrientation() {
        if (activity != null) {
            return activity.getRequestedOrientation();
        }

        return currentOrientation;
    }

    public void release() {
        activity = null;
        if (mOrientationEventListener != null) {
            mOrientationEventListener.disable();
        }
    }

}
