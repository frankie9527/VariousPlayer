package org.various.player.ui.recycler;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.various.player.R;
import org.various.player.ui.base.BaseControlView;
import org.various.player.ui.simple.VideoBottomView;
import org.various.player.ui.simple.VideoCenterView;
import org.various.player.ui.simple.VideoTopView;


/**
 * author: Frankie
 * Date: 2024/2/20
 * Description:
 */
public class RecyclerItemControlView extends BaseControlView<VideoTopView, VideoBottomView, VideoCenterView> {
    public RecyclerItemControlView(@NonNull Context context) {
        super(context);
    }

    public RecyclerItemControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerItemControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.various_recycler_item_control;
    }

    @Override
    protected int setTopViewId() {
        return R.id.video_top_view;
    }

    @Override
    protected int setBottomViewId() {
        return R.id.video_bottom_view;
    }

    @Override
    protected int setCenterViewId() {
        return R.id.video_center_view;
    }

    @Override
    public void onClick(View view) {

    }



}
