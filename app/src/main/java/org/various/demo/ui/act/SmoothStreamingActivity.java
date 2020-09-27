package org.various.demo.ui.act;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.easy.ui.recycler.listener.ItemClickListener;
import org.various.demo.R;
import org.various.demo.base.BaseActivity;
import org.various.demo.data.SimpleData;
import org.various.demo.ui.adapter.SmoothStreamingAdapter;
import org.various.player.core.PlayerManager;
import org.various.player.core.VariousExoPlayer;
import org.various.player.listener.UserActionListener;
import org.various.player.ui.normal.NormalVideoView;


/**
 * Created by 江雨寒 on 2020/9/17
 * Email：847145851@qq.com
 * func: exo 清晰度切换demo，
 */
public class SmoothStreamingActivity extends BaseActivity implements ItemClickListener {
    NormalVideoView normal_view;
    RecyclerView recycler;
    SmoothStreamingAdapter adapter;
    @Override
    protected int setLayout() {
        return R.layout.activity_smooth_streaming;
    }
    @Override
    protected void initView() {
        normal_view = findViewById(R.id.normal_view);
        normal_view.setPlayData(SimpleData.smoothStreamUrl, "SeamlessConnectionActivity");
        normal_view.startSyncPlay();
        normal_view.setUserActionListener(new UserActionListener() {
            @Override
            public void onUserAction(int action) {

            }
        });
        recycler=findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapter=new SmoothStreamingAdapter(SmoothStreamingActivity.this);
        adapter.setItemListener(this);
        recycler.setAdapter(adapter);

    }

    public void test(View view){
        VariousExoPlayer player= (VariousExoPlayer) PlayerManager.getPlayer();
        adapter.setVideoData(player);
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

    @Override
    public void onItemClick(View view, int postion) {
        adapter.onClickOnPosition(postion);
    }
}
