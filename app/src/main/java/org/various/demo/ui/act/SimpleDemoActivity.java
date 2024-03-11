package org.various.demo.ui.act;


import org.various.demo.R;
import org.various.demo.base.BaseActivity;
import org.various.demo.data.SimpleData;
import org.various.player.PlayerConstants;
import org.various.player.listener.UserActionListener;
import org.various.player.ui.simple.SimpleVideoView;
import org.various.player.PlayerConfig;

public class SimpleDemoActivity extends BaseActivity {
    SimpleVideoView simple_view;
    String title = "SimpleVideoView";
    @Override
    protected int setLayout() {
        return R.layout.activity_simple;
    }
    @Override
    protected void initView() {
        PlayerConfig.setPlayerCore(PlayerConstants.EXO_CORE);
        simple_view = findViewById(R.id.simple_view);
        simple_view.setPlayData(SimpleData.url, title);
        simple_view.startSyncPlay();
        simple_view.setUserActionListener(new UserActionListener() {
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
        if (simple_view != null)
            simple_view.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (simple_view != null)
            simple_view.resume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (simple_view != null)
            simple_view.release();
    }
}
