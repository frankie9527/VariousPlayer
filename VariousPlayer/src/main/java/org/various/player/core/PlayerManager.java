package org.various.player.core;


import android.util.Log;

import org.various.player.PlayerConstants;
import org.various.player.PlayerConfig;
import org.various.player.utils.StackTraceUtils;

/**
 * Created by 江雨寒 on 2020/8/17
 * Email：847145851@qq.com
 * func:
 */
public class PlayerManager {
    private static final String TAG = "PlayerManager";
    private static int currentStatus = -1;
    private static AbstractBasePlayer iPlayer;

    public static AbstractBasePlayer getPlayer() {
        if (iPlayer == null) {
            iPlayer = new VariousExoPlayer();
        }
        return iPlayer;
    }

    public static AbstractBasePlayer init() {
        Log.e(TAG, "init");
        if (PlayerConfig.getPlayerCore() == PlayerConstants.EXO_CORE) {
            Log.e(TAG, "getPlayer new VariousExoPlayer");
            iPlayer = new VariousExoPlayer();
        } else {
            Log.e(TAG, "getPlayer new VariousIjkPlayer");
            iPlayer = new VariousIjkPlayer();
        }
        return iPlayer;
    }

    public static void setPlayerStatus(int status) {
        Log.e(TAG, "status=" + status);
        currentStatus = status;
    }

    public static int getCurrentStatus() {
        return currentStatus;
    }
    public static void releasePlayer(){
        iPlayer=null;
    }
}
