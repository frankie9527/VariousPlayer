package org.various.player.ui.simple;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import org.various.player.R;
import org.various.player.ui.base.BaseCenterView;

/**
 * Created by 江雨寒 on 2020/8/14
 * Email：847145851@qq.com
 * func:
 */
public class VideoCenterView extends BaseCenterView {


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
    public void showLoading() {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        setVisibility(View.GONE);
    }

    @Override
    public void showEnd() {

    }

    @Override
    public void showError() {

    }


}
