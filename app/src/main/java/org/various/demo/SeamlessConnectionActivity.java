package org.various.demo;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.various.demo.base.BaseActivity;
import org.various.player.listener.UserActionListener;
import org.various.player.ui.normal.NormalVideoView;


/**
 * Created by 江雨寒 on 2020/9/17
 * Email：847145851@qq.com
 * func: 视频无缝衔接播放的demo
 */
public class SeamlessConnectionActivity extends BaseActivity {
    String url="https://playready.directtaps.net/smoothstreaming/SSWSS720H264/SuperSpeedway_720.ism/Manifest";
    NormalVideoView normal_view;
    RecyclerView recycler;
    SeamlessConnectionAdapter adapter;
    @Override
    protected int setLayout() {
        return R.layout.activity_seamless_con;
    }
    @Override
    protected void initView() {
        normal_view = findViewById(R.id.normal_view);
        normal_view.setPlayData(url, "SeamlessConnectionActivity");
        normal_view.startSyncPlay();
        normal_view.setUserActionListener(new UserActionListener() {
            @Override
            public void onUserAction(int action) {

            }
        });
        recycler=findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapter=new SeamlessConnectionAdapter(SeamlessConnectionActivity.this);
        recycler.setAdapter(adapter);

    }

    public void test(View view){
        adapter.setVideoData();
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
