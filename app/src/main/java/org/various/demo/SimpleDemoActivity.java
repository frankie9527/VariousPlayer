package org.various.demo;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.various.player.PlayerConstants;
import org.various.player.ui.simple.SimpleVideoView;
import org.various.player.PlayerConfig;

public class SimpleDemoActivity extends AppCompatActivity {
    String hsl = "http://cctvalih5ca.v.myalicdn.com/live/cctv1_2/index.m3u8";
    SimpleVideoView simple_view;
    String title = "hello!frankie";
    String url = "https://mov.bn.netease.com/open-movie/nos/mp4/2017/05/31/SCKR8V6E9_hd.mp4";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        init();
    }
    private void init() {
        PlayerConfig.setPlayerCore(PlayerConstants.IJY_CORE);
        simple_view =findViewById(R.id.simple_view);
        simple_view.setPlayData(url, title);
        simple_view.startSyncPlay();
    }
    public void stop(View view) {
        simple_view.pause();
    }

    public void continuePlay(View view) {
        simple_view.resume();
    }
}
