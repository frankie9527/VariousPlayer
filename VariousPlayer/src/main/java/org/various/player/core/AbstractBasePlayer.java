package org.various.player.core;

import com.google.android.exoplayer2.Player;

import org.various.player.PlayerConstants;
import org.various.player.listener.PlayerStatustListener;

/**
 * Created by 江雨寒 on 2020/8/19
 * Email：847145851@qq.com
 * func:
 */
public abstract class AbstractBasePlayer implements IPlayer {
    PlayerStatustListener mStatustListener = null;


    public void setVideoEventListener(PlayerStatustListener listener) {
        mStatustListener = listener;
    }

    public void notifyStatus(int status) {
        if (mStatustListener == null)
            return;
        if (status == Player.STATE_READY) {
            PlayerManager.setPlayerStatus(PlayerConstants.READY);
            mStatustListener.statusChange(PlayerConstants.READY);
        } else if (status == Player.STATE_BUFFERING) {
            PlayerManager.setPlayerStatus(PlayerConstants.BUFFERING);
            mStatustListener.statusChange(PlayerConstants.BUFFERING);
        } else if (status == Player.STATE_ENDED) {
            PlayerManager.setPlayerStatus(PlayerConstants.END);
            mStatustListener.statusChange(PlayerConstants.END);
        }
    }

    public void notifyPlayerError(){
        PlayerManager.setPlayerStatus(PlayerConstants.ERROR);
        if (mStatustListener !=null){
            mStatustListener.statusChange(PlayerConstants.ERROR);
        }
    }


}
