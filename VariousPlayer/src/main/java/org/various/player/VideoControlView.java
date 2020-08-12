package org.various.player;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.various.player.ui.VideoBottomView;
import org.various.player.ui.VideoTopView;

public class VideoControlView extends RelativeLayout implements IVideoControl, View.OnClickListener {
    VideoTopView topView;
    VideoBottomView bottomView;


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

    }

    @Override
    public void hideloading() {

    }

    @Override
    public void showTopAndBottom() {
        topView.setVisibleStatus();
        bottomView.setVisibleStatus();
    }

    @Override
    public void hideTopAndBootom() {
        topView.setVisibleStatus();
        bottomView.setVisibleStatus();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.img_back) {
            Toast.makeText(getContext(), "返回", Toast.LENGTH_LONG).show();
        }
        if (viewId == R.id.img_switch_screen) {
            Toast.makeText(getContext(), "切换屏幕", Toast.LENGTH_LONG).show();
        }
    }
}
