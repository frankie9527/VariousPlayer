package org.various.player.ui.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.various.player.IVideoControl;
import org.various.player.PlayerConstants;
import org.various.player.R;
import org.various.player.listener.UserActionListener;
import org.various.player.listener.UserDragSeekBarListener;


/**
 * Created by 江雨寒 on 2020/8/19
 * Email：847145851@qq.com
 * func:
 */
public abstract class BaseControlView<T extends BaseTopView, B extends BaseBottomView, L extends BaseLoadingView> extends FrameLayout implements IVideoControl, View.OnClickListener, UserDragSeekBarListener {
    T topView;
    B bottomView;
    L loadingView;
    UserActionListener listener;

    public BaseControlView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public BaseControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BaseControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    protected abstract int setLayoutId();
    protected abstract int setTopViewId();
    protected abstract int setBottomViewId();
    protected abstract int setLoaindViewId();


    public void initView(Context context) {
        View.inflate(context, setLayoutId(), this);
        initTopView(setTopViewId());
        initBottomView(setBottomViewId());
        initLoadingView(setLoaindViewId());
    }

    protected void initTopView(int id) {
        topView=findViewById(id);
        topView.setOnTopClickListener(this);

    }

    protected void initBottomView(int id) {
        bottomView = findViewById(id);
        bottomView.setOnBottomClickListener(this);
    }
    protected void initLoadingView(int id) {
        bottomView.setDragSeekListener(this);
        loadingView = findViewById(id);
    }

    @Override
    public void setTitle(String title) {
        topView.setTitle(title);
    }

    @Override
    public void showLoading() {
        loadingView.setVisibleStatus(PlayerConstants.SHOW);

    }

    @Override
    public void hideLoading() {
        loadingView.setVisibleStatus(PlayerConstants.HIDE);
        bottomView.startRepeater();
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
    public void showComplete() {
        Toast.makeText(getContext(),"播放完了",Toast.LENGTH_LONG).show();

    }

    @Override
    public void showError() {

    }

    @Override
    public void setUserActionListener(UserActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.img_back && listener != null) {
            listener.onUserAction(PlayerConstants.ACTION_BACK);
        }
        if (viewId == R.id.img_switch_screen && listener != null) {
            listener.onUserAction(PlayerConstants.SWITCH_SCREEN);
        }
    }

    @Override
    public void onUserDrag(long time) {
        showLoading();
    }
}
