package org.various.player.ui.recycler;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import org.various.player.R;
import org.various.player.ui.base.recycler.BaseRecyclerTopView;
import org.various.player.utils.OrientationUtils;

/**
 * Created by Frankie on 2020/8/13
 * Email：847145851@qq.com
 * func:
 */
public class RecyclerTopView extends BaseRecyclerTopView {
    public RecyclerTopView(Context context) {
        super(context);

    }

    public RecyclerTopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerTopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.various_simple_view_top;
    }

    @Override
    public void initView(Context context) {
        super.initView(context);
        backView = findViewById(R.id.img_back);
        titleView = findViewById(R.id.tv_title);
    }

    @Override
    public void show() {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        setVisibility(View.GONE);
    }

    @Override
    public void onScreenOrientationChanged() {
        //竖屏
        boolean vertical = OrientationUtils.getInstance().getOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        if (vertical) {
            hide();
        }else {
            show();
        }
    }
}
