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
    PlayerStatustListener statustListener = null;


    public void setVideoEventListener(PlayerStatustListener listener) {
        statustListener = listener;
    }

    public void notifyStatus(int status) {
        if (statustListener == null)
            return;
        if (status == Player.STATE_READY) {
            statustListener.statusChange(PlayerConstants.READY);
        } else if (status == Player.STATE_BUFFERING) {
            statustListener.statusChange(PlayerConstants.BUFFERING);
        } else if (status == Player.STATE_ENDED) {
            statustListener.statusChange(PlayerConstants.END);
        }
    }

    public void notifyPlayerError(){
        if (statustListener!=null){
            statustListener.statusChange(PlayerConstants.ERROR);
        }
    }


}
