package org.various.player.ui.simple;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.various.player.PlayerConstants;
import org.various.player.R;
import org.various.player.ui.base.BaseTopView;

/**
 * Created by 江雨寒 on 2020/8/13
 * Email：847145851@qq.com
 * func:
 */
public class VideoTopView extends BaseTopView {
    public VideoTopView(Context context) {
        super(context);

    }

    public VideoTopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoTopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.various_simple_view_top;
    }

    @Override
    public void initView(Context context) {
        super.initView(context);
        backView=findViewById(R.id.img_back);
        titleView=findViewById(R.id.tv_title);
    }

    @Override
    public void show() {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        setVisibility(View.GONE);
    }


}
