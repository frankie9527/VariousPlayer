package org.various.player.listener;


import org.various.player.PlayerConstants;

/**
 * Created by 江雨寒 on 2020/9/1
 * Email：847145851@qq.com
 * func: 用户横向滑动屏幕或者拖动seekBar，快进或者快退
 */
public interface UserProgressListener {
    void onUserProgress(@PlayerConstants.UserProgress int type, long time);
}
