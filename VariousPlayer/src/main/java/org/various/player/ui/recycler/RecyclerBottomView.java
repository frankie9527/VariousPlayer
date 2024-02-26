package org.various.player.ui.recycler;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.various.player.R;
import org.various.player.ui.base.recycler.BaseRecyclerBottomView;
import org.various.player.utils.OrientationUtils;

/**
 * Created by Frankie on 2020/8/13
 * Email：847145851@qq.com
 * func:
 */
public class RecyclerBottomView extends BaseRecyclerBottomView {
    String TAG="VideoRecyclerBottomView";

    public RecyclerBottomView(@NonNull Context context) {
        super(context);
    }

    public RecyclerBottomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.various_simple_view_bottom;
    }

    @Override
    public void initView(Context context) {
        super.initView(context);
        video_seek = findViewById(R.id.video_seek);
        video_seek.setOnSeekBarChangeListener(this);
        tv_current = findViewById(R.id.tv_current);
        tv_total = findViewById(R.id.tv_total);
        img_switch_screen=findViewById(R.id.img_switch_screen);
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
    public void setOnBottomClickListener(OnClickListener listener) {
        if (img_switch_screen!=null)
            img_switch_screen.setOnClickListener(listener);
    }

    @Override
    public void onScreenOrientationChanged() {

        if (OrientationUtils.getInstance().getOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            img_switch_screen.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.video_switch_full));
            return;
        }
        img_switch_screen.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.video_switch_normal));
    }
}
