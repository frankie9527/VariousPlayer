package org.various.player;

import androidx.annotation.IntDef;


/**
 * Created by 江雨寒 on 2020/8/14
 * Email：847145851@qq.com
 * func:
 */
public interface PlayerConstants {

    @IntDef({SHOW, HIDE})
    @interface VisibleStatus {
    }

    int SHOW = 0;
    int HIDE = 1;


    @interface PlayerStatus{

    }
    int startPlay=0;
    int loading=1;
}
