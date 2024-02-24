package org.various.player.core;


import android.util.Log;

import org.various.player.NotificationCenter;
import org.various.player.PlayerConstants;
import org.various.player.PlayerConfig;

/**
 * Created by 江雨寒 on 2020/8/17
 * Email：847145851@qq.com
 * func:
 */
public class PlayerManager {
    private static final String TAG = "PlayerManager";
    private int currentStatus = -1;
    private AbstractBasePlayer iPlayer;

    public AbstractBasePlayer getPlayer() {
        if (iPlayer == null) {
            init();
        }
        return iPlayer;
    }

    private static volatile PlayerManager globalInstance;

    public static PlayerManager getInstance() {
        PlayerManager localInstance = globalInstance;
        if (localInstance == null) {
            synchronized (NotificationCenter.class) {
                localInstance = globalInstance;
                if (localInstance == null) {
                    globalInstance = localInstance = new PlayerManager();
                }
            }
        }
        return localInstance;
    }

    public AbstractBasePlayer init() {
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

    public void setPlayerStatus(int status) {
        Log.e(TAG, "status=" + status);
        currentStatus = status;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public void releasePlayer() {
        if (iPlayer != null) {
            iPlayer.release();
        }
    }

}
