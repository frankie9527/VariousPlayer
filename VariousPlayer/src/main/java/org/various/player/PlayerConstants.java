package org.various.player;

import androidx.annotation.IntDef;


/**
 * Created by Frankie on 2020/8/14
 * Email：847145851@qq.com
 * func:
 */
public interface PlayerConstants {
    /** 播放器Ui层显示状态*/
    @IntDef({SHOW, HIDE,SHOW_LOADING,HIDE_LOADING,PLAY_END,PLAY_ERROR})
    @interface VisibleStatus {
    }
    int SHOW = 0;
    int HIDE = 1;
    int SHOW_LOADING = 2;
    int HIDE_LOADING = 3;
    int PLAY_END=4;
    int PLAY_ERROR=5;


    /** 播放器内核状态*/
    @IntDef({READY, BUFFERING,END,ERROR})
    @interface PlayerStatus {

    }

    int IDLE=-1;
    int READY = 0;
    int BUFFERING = 1;
    int END=2;
    int ERROR=3;


    @IntDef({ACTION_BACK, SWITCH_SCREEN})
    @interface UserAction {

    }

    int ACTION_BACK = 0;
    int SWITCH_SCREEN = 1;
    /**选择播放器内核*/
    @IntDef({EXO_CORE, IJY_CORE})
    @interface PlayerCore {

    }

    int EXO_CORE = 0;
    int IJY_CORE = 1;


    /**用户滑动屏幕或者拖动底部的seekBar,*/
    @IntDef({USER_DRAG_START,USER_DRAG_END,USER_PROGRESS_START, USER_PROGRESS_END})
    @interface UserProgress {

    }
    int USER_DRAG_START = 0;
    int USER_DRAG_END = 1;
    int USER_PROGRESS_START = 2;
    int USER_PROGRESS_END = 3;
}
