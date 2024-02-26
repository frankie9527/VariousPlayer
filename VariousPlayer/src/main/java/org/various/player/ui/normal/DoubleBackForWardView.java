package org.various.player.ui.normal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.various.player.R;

import org.various.player.core.VariousPlayerManager;
import org.various.player.utils.ToastUtils;
import org.various.player.utils.UiUtils;

/**
 * Created by Frankie on 2020/9/17
 * Email：847145851@qq.com
 * func:
 */
public class DoubleBackForWardView extends LinearLayout implements View.OnClickListener{
    ForwardHandler forwardHandler=new ForwardHandler();
    public static final int USER_ONCLICK_BACKWARD = 0;
    public static final int USER_ONCLICK_FORWARD = 1;
    public static final int DISMISS_FORWARD_BACKWARD = 2;
    private int fingerCount = 0;
    RelativeLayout rl_backward, rl_forward;
    TextView tv_backward,tv_forward;
    AnimationDrawable animate_backward, animate_forward;
    ImageView lottie_left, lottie_right;
    public DoubleBackForWardView(Context context) {
        super(context);
        initView(context);
    }

    public DoubleBackForWardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DoubleBackForWardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    public void initView(Context context) {
        View.inflate(context, R.layout.various_video_forward, this);
        rl_backward = findViewById(R.id.rl_backward);
        rl_backward.setOnClickListener(this);
        rl_forward = findViewById(R.id.rl_forward);
        rl_forward.setOnClickListener(this);
        tv_backward=findViewById(R.id.tv_backward);
        tv_forward=findViewById(R.id.tv_forward);
        lottie_left=findViewById(R.id.lottie_left);
        lottie_left.setImageResource(R.drawable.anim_arrow_left);
        lottie_right=findViewById(R.id.lottie_right);
        lottie_right.setImageResource(R.drawable.anim_arrow_right);
    }
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.rl_backward) {
            forwardHandler.sendEmptyMessage(USER_ONCLICK_BACKWARD);
            forwardHandler.removeMessages(DISMISS_FORWARD_BACKWARD);
            return;
        }
        if (v.getId() == R.id.rl_forward) {
            forwardHandler.sendEmptyMessage(USER_ONCLICK_FORWARD);
            forwardHandler.removeMessages(DISMISS_FORWARD_BACKWARD);
        }
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (forwardHandler != null) {
            forwardHandler.removeMessages(USER_ONCLICK_BACKWARD);
            forwardHandler.removeMessages(USER_ONCLICK_FORWARD);
            forwardHandler.removeMessages(DISMISS_FORWARD_BACKWARD);
            forwardHandler = null;
        }
    }
    @SuppressLint("HandlerLeak")
    private class ForwardHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case USER_ONCLICK_BACKWARD:
                    fingerCount--;
                    tv_backward.setText(fingerCount * 10 + "秒");
                    forwardHandler.sendEmptyMessageDelayed(DISMISS_FORWARD_BACKWARD, 2000);
                    break;
                case USER_ONCLICK_FORWARD:
                    fingerCount++;
                    tv_forward.setText(fingerCount * 10 + "秒");
                    forwardHandler.sendEmptyMessageDelayed(DISMISS_FORWARD_BACKWARD, 2000);
                    break;
                case DISMISS_FORWARD_BACKWARD:
                    UiUtils.viewSetGone(rl_backward);
                    UiUtils.viewSetGone(rl_forward);
                    long goTime = fingerCount * 10 * 1000 + VariousPlayerManager.getCurrentPosition();

                    if (goTime > 0 && goTime < VariousPlayerManager.getDuration()) {
                        VariousPlayerManager.seekTo(goTime);
                    } else {
                        ToastUtils.show( "预期播放不符合影片时长");
                    }
                    break;
            }
        }
    }
    public void onLeftDoubleTop() {
        UiUtils.viewSetVisible(rl_backward);
        rl_forward.setVisibility(INVISIBLE);
        if (animate_backward == null) {
            animate_backward = (AnimationDrawable) lottie_left.getDrawable();
        }
        if (!animate_backward.isRunning()) {
            //开启帧动画
            animate_backward.start();
        }
        fingerCount = -1;
        String str = fingerCount * 10 + "秒";
        tv_backward.setText(str);
        forwardHandler.sendEmptyMessageDelayed(DISMISS_FORWARD_BACKWARD, 2000);
    }

    public void onRightDoubleTap() {
        UiUtils.viewSetVisible(rl_forward);
        rl_backward.setVisibility(INVISIBLE);
        if (animate_forward == null) {
            animate_forward = (AnimationDrawable) lottie_right.getDrawable();
        }
        if (!animate_forward.isRunning()) {
            //开启帧动画
            animate_forward.start();
        }

        fingerCount = 1;
        String str = fingerCount * 10 + "秒";
        tv_forward.setText(str);
        forwardHandler.sendEmptyMessageDelayed(DISMISS_FORWARD_BACKWARD, 2000);
    }
}
