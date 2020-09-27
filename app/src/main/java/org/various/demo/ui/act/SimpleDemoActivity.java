package org.various.demo.ui.act;


import org.various.demo.R;
import org.various.demo.base.BaseActivity;
import org.various.player.PlayerConstants;
import org.various.player.listener.UserActionListener;
import org.various.player.ui.simple.SimpleVideoView;
import org.various.player.PlayerConfig;

public class SimpleDemoActivity extends BaseActivity {
    String hsl = "http://cctvalih5ca.v.myalicdn.com/live/cctv1_2/index.m3u8";
    SimpleVideoView simple_view;
    String title = "SimpleVideoView";
    String url = "https://mov.bn.netease.com/open-movie/nos/mp4/2017/05/31/SCKR8V6E9_hd.mp4";


    @Override
    protected int setLayout() {
        return R.layout.activity_simple;
    }



    @Override
    protected void initView() {
        PlayerConfig.setPlayerCore(PlayerConstants.EXO_CORE);
        simple_view =findViewById(R.id.simple_view);
        simple_view.setPlayData(url, title);
//        simple_view.startSyncPlay();
        simple_view.setUserActionListener(new UserActionListener() {
            @Override
            public void onUserAction(int action) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (simple_view!=null)
            simple_view.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (simple_view!=null)
            simple_view.resume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (simple_view!=null)
            simple_view.release();
    }
}
