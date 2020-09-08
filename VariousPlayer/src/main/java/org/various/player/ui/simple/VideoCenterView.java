package org.various.player.ui.simple;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.various.player.NotificationCenter;
import org.various.player.PlayerConstants;
import org.various.player.R;
import org.various.player.core.PlayerManager;
import org.various.player.ui.base.BaseCenterView;

/**
 * Created by 江雨寒 on 2020/8/14
 * Email：847145851@qq.com
 * func:
 */
public class VideoCenterView extends BaseCenterView {
    private static final String TAG = "VideoCenterView";
    ProgressBar video_progress;
    ImageView img_status;
    RelativeLayout rl_play_err;
    TextView tv_replay;
    public VideoCenterView(Context context) {
        super(context);

    }

    public VideoCenterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public VideoCenterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.various_simple_view_center;
    }

    @Override
    public void initView(Context context) {
        super.initView(context);
        video_progress=findViewById(R.id.video_progress);
        img_status=findViewById(R.id.img_status);
        img_status.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int type=PlayerManager.getCurrentStatus();
                if (type==PlayerConstants.READY){
                    return;
                }
                if (type==PlayerConstants.READY){
                    return;
                }
            }
        });
        rl_play_err=findViewById(R.id.rl_play_err);
        tv_replay=findViewById(R.id.tv_replay);
        tv_replay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.user_onclick_video_err_retry);
            }
        });
    }

    @Override
    public void showLoading() {
        setVisibility(View.VISIBLE);
        if (img_status!=null&&img_status.getVisibility()==VISIBLE){
            img_status.setVisibility(GONE);
        }
        video_progress.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoading() {
        video_progress.setVisibility(GONE);
        setVisibility(View.GONE);
    }

    @Override
    public void showEnd() {
        setVisibility(View.VISIBLE);
        if (video_progress!=null&&video_progress.getVisibility()==VISIBLE){
            video_progress.setVisibility(GONE);
        }
        img_status.setVisibility(VISIBLE);
        img_status.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.video_replay));
    }

    @Override
    public void showError() {
        setVisibility(View.VISIBLE);
        if (video_progress!=null&&video_progress.getVisibility()==VISIBLE){
            video_progress.setVisibility(GONE);
        }
        if (rl_play_err!=null&&rl_play_err.getVisibility()!=VISIBLE){
            rl_play_err.setVisibility(VISIBLE);
        }
    }

    @Override
    public void hideAll() {
        video_progress.setVisibility(GONE);
        img_status.setVisibility(GONE);
        setVisibility(View.GONE);
    }

    @Override
    public void showStatus() {
        setVisibility(View.VISIBLE);
        int type=PlayerManager.getCurrentStatus();
        Log.e(TAG,"type="+type);
        img_status.setVisibility(GONE);
        video_progress.setVisibility(GONE);
        switch (type){
            case PlayerConstants.READY:
                img_status.setVisibility(VISIBLE);
                img_status.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.video_pause));
                break;
            case PlayerConstants.BUFFERING:
                showLoading();
                break;
            case PlayerConstants.END:
                showEnd();
                break;
            case PlayerConstants.ERROR:
                showError();
                break;
        }
    }


}
