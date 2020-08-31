package org.various.player;

import org.various.player.listener.UserActionListener;
import org.various.player.listener.UserChangeOrientationListener;

/**
 * Created by 江雨寒 on 2020/8/13
 * Email：847145851@qq.com
 * func:
 */
public interface IVideoControl {
    void setTitle(String title);


    void stateBuffering();

    void stateReady();

    void showTopAndBottom();

    void hideTopAndBottom();

    void showComplete();

    void showError();

    void setUserActionListener(UserActionListener listener);

    void setOrientationListener(UserChangeOrientationListener orientationListener);
}
