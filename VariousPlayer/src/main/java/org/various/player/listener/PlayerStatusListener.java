package org.various.player.listener;

import org.various.player.PlayerConstants;

/**
 * Created by Frankie on 2020/8/19
 * Email：847145851@qq.com
 * func:
 */
public interface PlayerStatusListener {

    void statusChange(@PlayerConstants.PlayerStatus int status);

    void onRenderedFirstFrame(int width,int height);

}
