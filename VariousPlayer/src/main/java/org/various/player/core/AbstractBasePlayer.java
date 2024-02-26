package org.various.player.core;

import org.various.player.PlayerConfig;
import org.various.player.PlayerConstants;
import org.various.player.listener.PlayerStatusListener;
import org.various.player.utils.LogUtils;

/**
 * Created by Frankie on 2020/8/19
 * Email：847145851@qq.com
 * func:
 */
public abstract class AbstractBasePlayer implements IPlayer {
    PlayerStatusListener mStatusListener = null;

    public void setVideoEventListener(PlayerStatusListener listener) {
        mStatusListener = listener;
    }

    public void notifyStatus(@PlayerConstants.PlayerCore int core, int status) {
        if (mStatusListener == null)
            return;
        int normalStatus = PlayerConfig.changStatus2Normal(core, status);
        LogUtils.e("AbstractBasePlayer", "exoplayer status =" + status + "normalStatus =" + normalStatus);
        //播放器播放错误，播放器闲置状态！因为上个状态是3播放错误，显示播放错误的ui！如果把播放器闲置的状态1下发下去，那么ui就不对了
        if (VariousPlayerManager.getCurrentStatus() == PlayerConstants.ERROR && normalStatus == PlayerConstants.IDLE) {
            return;
        }
        VariousPlayerManager.setPlayerStatus(normalStatus);
        mStatusListener.statusChange(normalStatus);
    }

    public void notifyPlayerError() {
        VariousPlayerManager.setPlayerStatus(PlayerConstants.ERROR);
        if (mStatusListener != null) {
            mStatusListener.statusChange(PlayerConstants.ERROR);
        }
    }


}
