package org.various.demo.ui.act;


import org.various.demo.R;
import org.various.demo.base.BaseActivity;
import org.various.demo.data.SimpleData;
import org.various.player.PlayerConstants;
import org.various.player.listener.UserActionListener;
import org.various.player.ui.normal.NormalVideoView;

public class NormalDemoActivity extends BaseActivity {
    NormalVideoView normal_view;
    String title = "NormalVideoView";

    @Override
    protected int setLayout() {
        return R.layout.activity_normal;
    }


    @Override
    protected void initView() {
        normal_view = findViewById(R.id.normal_view);
        normal_view.setPlayData(SimpleData.url2, title);
        normal_view.startSyncPlay();
        normal_view.setUserActionListener(new UserActionListener() {
            @Override
            public void onUserAction(int action) {
                if (action == PlayerConstants.ACTION_BACK)
                    finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (normal_view != null)
            normal_view.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (normal_view != null)
            normal_view.resume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (normal_view != null)
            normal_view.release();
    }
}
