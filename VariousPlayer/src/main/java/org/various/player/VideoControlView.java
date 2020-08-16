package org.various.player;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;

import org.various.player.ui.VideoBottomView;
import org.various.player.ui.VideoLoadingView;
import org.various.player.ui.VideoTopView;
import org.various.player.utils.Repeater;

public class VideoControlView extends RelativeLayout implements IVideoControl, View.OnClickListener {
    VideoTopView topView;
    VideoBottomView bottomView;
    VideoLoadingView loadingView;
    @NonNull
    protected Repeater progressPollRepeater = new Repeater();

    public VideoControlView(Context context) {
        super(context);
        initView(context);
    }

    public VideoControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public VideoControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {
        View.inflate(context, R.layout.various_simple_view_control, this);
        topView = findViewById(R.id.video_top_view);
        topView.setOnTopClickListener(this);
        bottomView = findViewById(R.id.video_bottom_view);
        bottomView.setOnBottomClickListener(this);
    }


    @Override
    public void setTitle(String title) {
        topView.setTitle(title);
    }

    @Override
    public void showLoading() {
        loadingView.setVisibleStatus(PlayerConstants.SHOW);
    }

    @Override
    public void hideloading() {
        loadingView.setVisibleStatus(PlayerConstants.HIDE);
    }

    @Override
    public void showTopAndBottom() {
        topView.setVisibleStatus(PlayerConstants.SHOW);
        bottomView.setVisibleStatus(PlayerConstants.SHOW);
    }

    @Override
    public void hideTopAndBootom() {
        topView.setVisibleStatus(PlayerConstants.HIDE);
        bottomView.setVisibleStatus(PlayerConstants.HIDE);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.img_back) {

        }
        if (viewId == R.id.img_switch_screen) {

        }
    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //A poll used to periodically update the progress bar
        progressPollRepeater.setRepeatListener(new Repeater.RepeatListener() {
            @Override
            public void onRepeat() {

            }
        });
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        progressPollRepeater.stop();
        progressPollRepeater.setRepeatListener(null);
    }
}
