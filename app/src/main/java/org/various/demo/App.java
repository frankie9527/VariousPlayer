package org.various.demo;

import android.annotation.SuppressLint;
import android.app.Application;

import org.various.demo.utils.CrashHandler;
import org.various.player.VariousSDK;

public class App extends Application {
    @SuppressLint({"StaticFieldLeak"})
    private static Application mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        VariousSDK.init(this);
        CrashHandler.getInstance().init();
    }
    public static Application getContext() {
        return mContext;
    }
}
