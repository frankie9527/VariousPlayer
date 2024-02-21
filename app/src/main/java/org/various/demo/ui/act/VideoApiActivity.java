package org.various.demo.ui.act;


import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.various.demo.R;
import org.various.demo.base.BaseActivity;
import org.various.demo.data.SimpleData;
import org.various.player.NotificationCenter;
import org.various.player.PlayerConstants;
import org.various.player.listener.UserActionListener;
import org.various.player.ui.simple.SimpleVideoView;

public class VideoApiActivity extends BaseActivity implements NotificationCenter.NotificationCenterDelegate {
    SimpleVideoView simple_view;
    String title = "hello!frankie";
    Button speedButton;
    /**
     * 当前播放的倍速
     * */
    float currentSpeed;
    ImageView img_copy;
    @Override
    protected int setLayout() {
        return R.layout.activity_video_api;
    }



    @Override
    protected void initView() {
        NotificationCenter.getGlobalInstance().addObserver(this,NotificationCenter.user_onclick_take_pic_data);
        speedButton=findViewById(R.id.speed);
        simple_view =findViewById(R.id.simple_view);
        simple_view.setUserActionListener(new UserActionListener() {
            @Override
            public void onUserAction(int action) {
                if (action == PlayerConstants.ACTION_BACK)
                    finish();
            }
        });
        img_copy=findViewById(R.id.img_copy);

    }


    public void pause(View view){
        simple_view.pause();
    }
    public void resume(View view){
        simple_view.resume();
    }

    public void speed(View view){
        currentSpeed=simple_view.getSpeed();
        if (currentSpeed<2.0){
            currentSpeed+=0.5f;
        }else {
            currentSpeed=1.0f;
        }
        simple_view.setSpeed(currentSpeed);
        speedButton.setText(currentSpeed+"x");
    }
    public void  screenShot(View view){
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.user_onclick_take_pic);
    }

    public void play1(View view){
        simple_view.setPlayData(SimpleData.url, title);
        simple_view.startSyncPlay();
    }
    public void play2(View view){
        simple_view.setPlayData(SimpleData.url3, title);
        simple_view.startSyncPlay();
    }
    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
        if (id==NotificationCenter.user_onclick_take_pic_data){
            img_copy.setImageBitmap((Bitmap) args[0]);
        }
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
