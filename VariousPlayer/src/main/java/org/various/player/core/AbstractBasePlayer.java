package org.various.player.core;

import android.util.Log;

import com.google.android.exoplayer2.Player;

import org.various.player.PlayerConfig;
import org.various.player.PlayerConstants;
import org.various.player.listener.PlayerStatusListener;


/**
 * Created by 江雨寒 on 2020/8/19
 * Email：847145851@qq.com
 * func:
 */
public abstract class AbstractBasePlayer implements IPlayer {
    PlayerStatusListener mStatusListener = null;


    public void setVideoEventListener(PlayerStatusListener listener) {
        if (mStatusListener != null) {
            mStatusListener = null;
        }
        mStatusListener = listener;
    }

    public void notifyStatus(@PlayerConstants.PlayerCore int core, int status) {
        if (mStatusListener == null)
            return;
        Log.e("AbstractBasePlayer", "status=" + status);
        int normalStatus = PlayerConfig.changStatus2Normal(core, status);
        Log.e("AbstractBasePlayer", "normalStatus=" + normalStatus);
        PlayerManager.setPlayerStatus(normalStatus);
        mStatusListener.statusChange(normalStatus);
    }

    public void notifyPlayerError() {
        PlayerManager.setPlayerStatus(PlayerConstants.ERROR);
        if (mStatusListener != null) {
            mStatusListener.statusChange(PlayerConstants.ERROR);
        }
    }


}
