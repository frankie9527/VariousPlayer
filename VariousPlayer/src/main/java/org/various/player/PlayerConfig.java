package org.various.player;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
/**
 * Created by 江雨寒 on 2020/8/18
 * Email：847145851@qq.com
 * func: 播放器配置相关
 */
public class PlayerConfig {
    private static @PlayerConstants.PlayerCore
    int currentCore = PlayerConstants.EXO_CORE;
    @SuppressLint({"StaticFieldLeak"})
    private static Application mContext;

    public static void init(@NonNull Application app) {
        mContext = app;
    }

    public static Application getContext() {
        return mContext;
    }

    public static void setPlayerCore(@PlayerConstants.PlayerCore int core) {
        currentCore = core;
    }

    public static @PlayerConstants.PlayerCore int getPlayerCore() {
        return currentCore;
    }
}
