package org.various.player.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.various.player.R;

/**
 * Created by 江雨寒 on 2020/8/13
 * Email：847145851@qq.com
 * func:
 */
public class VideoBottomView extends LinearLayout {
    ImageView img_switch_screen;
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
        img_switch_screen=findViewById(R.id.img_switch_screen);
    }
    public void setOnBottomClickListener(View.OnClickListener listener) {
        img_switch_screen.setOnClickListener(listener);
    }
    public void setVisibleStatus() {

    }
}
