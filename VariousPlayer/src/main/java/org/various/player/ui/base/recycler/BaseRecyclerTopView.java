package org.various.player.ui.base.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.various.player.PlayerConstants;
import org.various.player.utils.LogUtils;


/**
 * Created by Frankie on 2020/8/19
 * Email：847145851@qq.com
 * func:
 */
public abstract class BaseRecyclerTopView extends FrameLayout {
    protected View backView;
    protected TextView titleView;


    public BaseRecyclerTopView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public BaseRecyclerTopView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BaseRecyclerTopView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    protected abstract int setLayoutId();

    public void initView(Context context) {
        View.inflate(context, setLayoutId(), this);

    }



    public void setVisibleStatus(@PlayerConstants.VisibleStatus int status) {
        if (status == PlayerConstants.SHOW) {
            show();
            return;
        }
        if (status == PlayerConstants.HIDE) {
            hide();
        }
    }

    public void setTitle(String title) {
        if (titleView != null)
            titleView.setText(title);
    }

    public abstract void show();

    public abstract void hide();

    public void setOnTopClickListener(OnClickListener listener) {
        if (backView != null)
            backView.setOnClickListener(listener);
    }

    public View getBackView(){
        return backView;
    }
    public void onScreenOrientationChanged() {
        LogUtils.e("BaseRecyclerTopView", "user ScreenOrientationChanged");
    }
}
