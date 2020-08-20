package org.various.player.listener;

import org.various.player.PlayerConstants;

/**
 * Created by 江雨寒 on 2020/8/19
 * Email：847145851@qq.com
 * func:
 */
public interface PlayerStatustListener {

    void statusChange(@PlayerConstants.PlayerStatus int status);



}
