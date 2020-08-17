package org.various.player.ui;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import org.various.player.IVideoControl;
import org.various.player.PlayerConstants;
import org.various.player.R;
import org.various.player.listener.UserActionListener;
import org.various.player.listener.UserDragSeekBarListener;
public class VideoControlView extends RelativeLayout implements IVideoControl, View.OnClickListener , UserDragSeekBarListener {
    VideoTopView topView;
    VideoBottomView bottomView;
    VideoLoadingView loadingView;
    UserActionListener listener;

    public VideoControlView(Context context) {
        super(context);
        initView(context);
    }

    public VideoControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public VideoControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {
        View.inflate(context, R.layout.various_simple_view_control, this);
        topView = findViewById(R.id.video_top_view);
        topView.setOnTopClickListener(this);
        bottomView = findViewById(R.id.video_bottom_view);
        bottomView.setOnBottomClickListener(this);
        bottomView.setDragSeekListener(this);
        loadingView = findViewById(R.id.video_loading_view);
    }


    @Override
    public void setTitle(String title) {
        topView.setTitle(title);
    }

    @Override
    public void showLoading() {
        loadingView.setVisibleStatus(PlayerConstants.HIDE);
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibleStatus(PlayerConstants.HIDE);
    }

    @Override
    public void showTopAndBottom() {
        topView.setVisibleStatus(PlayerConstants.SHOW);
        bottomView.setVisibleStatus(PlayerConstants.SHOW);
    }

    @Override
    public void hideTopAndBootom() {
        topView.setVisibleStatus(PlayerConstants.HIDE);
        bottomView.setVisibleStatus(PlayerConstants.HIDE);
    }

    @Override
    public void setUserActionListener(UserActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.img_back) {
            listener.onUserAction(PlayerConstants.ACTION_BACK);
        }
        if (viewId == R.id.img_switch_screen) {
            listener.onUserAction(PlayerConstants.SWITCH_SCREEN);
        }
    }

    @Override
    public void onUserDrag(long time) {
        showLoading();
    }
}
