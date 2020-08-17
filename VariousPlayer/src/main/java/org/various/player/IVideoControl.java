package org.various.player;

import org.various.player.listener.UserActionListener;

/**
 * Created by 江雨寒 on 2020/8/13
 * Email：847145851@qq.com
 * func:
 */
public interface IVideoControl {
    void setTitle(String title);

    void showLoading();

    void hideLoading();

    void showTopAndBottom();

    void hideTopAndBootom();

    void setUserActionListener(UserActionListener listener);
}
