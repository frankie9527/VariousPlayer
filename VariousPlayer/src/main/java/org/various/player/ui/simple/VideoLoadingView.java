package org.various.player.ui.simple;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.various.player.PlayerConstants;
import org.various.player.R;
import org.various.player.ui.base.BaseLoadingView;

/**
 * Created by 江雨寒 on 2020/8/14
 * Email：847145851@qq.com
 * func:
 */
public class VideoLoadingView extends BaseLoadingView {


    public VideoLoadingView(Context context) {
        super(context);

    }

    public VideoLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public VideoLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.various_simple_view_loading;
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
