package org.various.player.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.various.player.PlayerConstants;
import org.various.player.R;
import org.various.player.core.PlayerManager;
import org.various.player.listener.UserDragSeekBarListener;
import org.various.player.utils.Repeater;
import org.various.player.utils.TimeFormatUtil;

/**
 * Created by 江雨寒 on 2020/8/13
 * Email：847145851@qq.com
 * func:
 */
public class VideoBottomView extends LinearLayout implements SeekBar.OnSeekBarChangeListener {
    @NonNull
    protected Repeater progressPollRepeater = new Repeater();
    ImageView img_switch_screen;
    SeekBar video_seek;
    TextView tv_current, tv_total;
    UserDragSeekBarListener dragSeekBarListener;

    public VideoBottomView(Context context) {
        super(context);
        initView(context);
    }

    public VideoBottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public VideoBottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.various_simple_view_bottom, this);
        img_switch_screen = findViewById(R.id.img_switch_screen);
        video_seek = findViewById(R.id.video_seek);
        video_seek.setOnSeekBarChangeListener(this);
        tv_current = findViewById(R.id.tv_current);
        tv_total = findViewById(R.id.tv_total);
        progressPollRepeater.start();
    }

    public void setOnBottomClickListener(View.OnClickListener listener) {
        img_switch_screen.setOnClickListener(listener);
    }

    public void setVisibleStatus(@PlayerConstants.VisibleStatus int status) {
        if (status == PlayerConstants.SHOW) {
            show();
            return;
        }
        hide();
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(GONE);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        progressPollRepeater.stop();
        progressPollRepeater.setRepeatListener(null);
    }

    public void setCurrentTime(String str) {
        tv_current.setText(str);
    }

    public void setTotalTime(String str) {
        tv_total.setText(str);
    }

    public void setSeekPosition(int currentPosition, int bufferPercent) {
        video_seek.setProgress(currentPosition);
        video_seek.setSecondaryProgress(bufferPercent);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        progressPollRepeater.setRepeatListener(new Repeater.RepeatListener() {
            @Override
            public void onRepeat() {
                updateProgress();
            }
        });
    }

    private void updateProgress() {
        long currentTime = PlayerManager.getPlayer().getCurrentPosition();
        String currentStr = TimeFormatUtil.formatMs(currentTime);
        setCurrentTime(currentStr);
        float duration = PlayerManager.getPlayer().getDuration();
        int currentPosition = (int) ((PlayerManager.getPlayer().getCurrentPosition() / duration) * 100);
        int bufferPercent = PlayerManager.getPlayer().getBufferedPercent();
        setSeekPosition(currentPosition, bufferPercent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) return;
        long time = PlayerManager.getPlayer().getDuration() * progress / 100;
        String str = TimeFormatUtil.formatMs(time);
        setCurrentTime(str);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        progressPollRepeater.stop();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        long time = PlayerManager.getPlayer().getDuration() * progress / 100;
        PlayerManager.getPlayer().seekTo(time);
        progressPollRepeater.start();
        if (dragSeekBarListener != null) {
            dragSeekBarListener.onUserDrag(time);
        }

    }


    public void setDragSeekListener(UserDragSeekBarListener listener) {
        this.dragSeekBarListener = listener;
    }
}
