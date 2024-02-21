package org.various.player.ui.recycler;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.various.player.PlayerConstants;
import org.various.player.R;
import org.various.player.core.PlayerManager;
import org.various.player.ui.base.BaseRecyclerVideoView;


/**
 * author: Frankie
 * Date: 2024/2/20
 * Description:
 */
public class RecyclerItemVideoView extends BaseRecyclerVideoView<RecyclerItemControlView> {
    private String url;
    protected int itemPosition;
    public RecyclerItemVideoView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public RecyclerItemVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RecyclerItemVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    protected void initView(Context context) {
        View.inflate(context, R.layout.various_recycler_item_view, this);
        control = findViewById(R.id.video_control);
        control.setOrientationListener(this);
    }
    public void setPlayData(String url, String title) {
        this.url = url;
    }
    public void startSyncPlayOrPause(int position){
        this.itemPosition=position;
        setKeepScreenOn(true);
        //正在播放则 切换成 暂停或者播放
        if (PlayerManager.getInstance().getPlayItemPosition()==itemPosition){
            if (PlayerManager.getInstance().getPlayer().isPlaying()) {
                PlayerManager.getInstance().getPlayer().pause();
                return;
            }
            int currentStatus = PlayerManager.getInstance().getCurrentStatus();
            long currentPosition = PlayerManager.getInstance().getPlayer().getCurrentPosition();
            String url = PlayerManager.getInstance().getPlayer().getVideoUrl();
            if (currentStatus == PlayerConstants.IDLE && currentPosition == 0 && !TextUtils.isEmpty(url)) {
                PlayerManager.getInstance().getPlayer().startSyncPlay();
                return;
            }
            PlayerManager.getInstance().getPlayer().resume();
        }else {
            //先停止其他item 的播放ui，然后播放当前
            PlayerManager.getInstance().getPlayer().release();
            PlayerManager.getInstance().setPlayItemPosition(itemPosition);
            player = PlayerManager.getInstance().init();
            player.setVideoEventListener(this);
            player.setVideoUri(url);
            player.startSyncPlay();
            control.stateBuffering();
        }
    }
}
