package org.various.player.core;

import com.google.android.exoplayer2.Player;

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
        mStatusListener = listener;
    }

    public void notifyStatus(int status) {
        if (mStatusListener == null)
            return;
        if (status == Player.STATE_READY) {
            PlayerManager.setPlayerStatus(PlayerConstants.READY);
            mStatusListener.statusChange(PlayerConstants.READY);
        } else if (status == Player.STATE_BUFFERING) {
            PlayerManager.setPlayerStatus(PlayerConstants.BUFFERING);
            mStatusListener.statusChange(PlayerConstants.BUFFERING);
        } else if (status == Player.STATE_ENDED) {
            PlayerManager.setPlayerStatus(PlayerConstants.END);
            mStatusListener.statusChange(PlayerConstants.END);
        }
    }

    public void notifyPlayerError(){
        PlayerManager.setPlayerStatus(PlayerConstants.ERROR);
        if (mStatusListener !=null){
            mStatusListener.statusChange(PlayerConstants.ERROR);
        }
    }


}
