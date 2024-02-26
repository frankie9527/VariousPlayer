package org.various.player.listener;

import org.various.player.PlayerConstants;

/**
 * Created by Frankie on 2020/8/17
 * Email：847145851@qq.com
 * func: 用户点击返回或者横竖屏切换
 */
public interface UserActionListener {
    void onUserAction(@PlayerConstants.UserAction int action);
}
