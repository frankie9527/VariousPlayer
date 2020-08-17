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


    @interface PlayerStatus {

    }

    int STARTPLAY = 0;
    int LOADING = 1;

    @IntDef({ACTION_BACK, SWITCH_SCREEN})
    @interface UserAction {

    }

    int ACTION_BACK = 0;
    int SWITCH_SCREEN = 1;

    @IntDef({EXO_CORE, IJY_CORE})
    @interface PlayerCore {

    }

    int EXO_CORE = 0;
    int IJY_CORE = 1;
}
