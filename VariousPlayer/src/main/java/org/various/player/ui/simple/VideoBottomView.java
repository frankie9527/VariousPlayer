package org.various.player.ui.simple;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
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
import org.various.player.ui.base.BaseBottomView;
import org.various.player.utils.Repeater;
import org.various.player.utils.TimeFormatUtil;

/**
 * Created by 江雨寒 on 2020/8/13
 * Email：847145851@qq.com
 * func:
 */
public class VideoBottomView extends BaseBottomView {
    String TAG="VideoBottomView";

    public VideoBottomView(@NonNull Context context) {
        super(context);
    }

    public VideoBottomView(@NonNull Context context, @Nullable AttributeSet attrs) {
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

    }
}
