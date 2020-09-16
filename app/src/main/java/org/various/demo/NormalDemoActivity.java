package org.various.demo;


import org.various.demo.base.BaseActivity;
import org.various.player.listener.UserActionListener;
import org.various.player.ui.normal.NormalVideoView;

public class NormalDemoActivity extends BaseActivity {
    String hsl = "http://cctvalih5ca.v.myalicdn.com/live/cctv1_2/index.m3u8";
    NormalVideoView normal_view;
    String title = "NormalVideoView";
    String url = "https://mov.bn.netease.com/open-movie/nos/mp4/2017/05/31/SCKR8V6E9_hd.mp4";


    @Override
    protected int setLayout() {
        return R.layout.activity_normal;
    }



    @Override
    protected void initView() {
        normal_view =findViewById(R.id.normal_view);
        normal_view.setPlayData(url, title);
        normal_view.startSyncPlay();
        normal_view.setUserActionListener(new UserActionListener() {
            @Override
            public void onUserAction(int action) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (normal_view!=null)
            normal_view.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (normal_view!=null)
            normal_view.resume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (normal_view!=null)
            normal_view.release();
    }
}
