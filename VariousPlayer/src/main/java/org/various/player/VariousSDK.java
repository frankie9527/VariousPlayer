package org.various.player;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;

public class VariousSDK {
    @SuppressLint({"StaticFieldLeak"})
    private static Application mContext;

    public static void init(@NonNull Application app) {
        mContext = app;
    }

    public static Application getContext() {
        return mContext;
    }
}
