package org.various.player.ui.tiktok;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.various.player.R;

/**
 * author: Frankie
 * Date: 2024/3/12
 * Description:
 */
public class DemoTiktokVideoView extends BaseTiktokVideoView{

    public DemoTiktokVideoView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public DemoTiktokVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DemoTiktokVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    protected void initView(Context context) {
        View.inflate(context, R.layout.various_tiktok_view, this);
        video_container = findViewById(R.id.video_container);
        img_status=findViewById(R.id.img_status);
        img_back_ground=findViewById(R.id.img_back_ground);
        video_seek=findViewById(R.id.video_seek);
    }




}
