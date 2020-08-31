package org.various.player.listener;



/**
 * Created by 江雨寒 on 2020/8/17
 * Email：847145851@qq.com
 * func: 用户拖动进度条
 */
public interface UserDragSeekBarListener {
    int DRAG_START = 0;
    int DRAG_END = 1;
    void onUserDrag(int type,long time);
}
