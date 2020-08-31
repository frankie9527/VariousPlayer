package org.various.demo;


import android.view.View;
import org.various.demo.base.BaseActivity;
import org.various.player.PlayerConstants;
import org.various.player.listener.UserActionListener;
import org.various.player.ui.simple.SimpleVideoView;
import org.various.player.PlayerConfig;

public class SimpleDemoActivity extends BaseActivity {
    String hsl = "http://cctvalih5ca.v.myalicdn.com/live/cctv1_2/index.m3u8";
    SimpleVideoView simple_view;
    String title = "hello!frankie";
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
        simple_view.startSyncPlay();
        simple_view.setUserActionListener(new UserActionListener() {
            @Override
            public void onUserAction(int action) {

            }
        });
    }
    public void stop(View view) {
        simple_view.pause();
    }

    public void continuePlay(View view) {
        simple_view.resume();
    }
}
