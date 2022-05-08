package org.various.demo;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import org.various.demo.ui.act.NormalDemoActivity;
import org.various.demo.ui.act.SimpleRecyclerActivity;
import org.various.demo.ui.act.SmoothStreamingActivity;
import org.various.demo.ui.act.SimpleDemoActivity;
import org.various.demo.ui.act.VideoApiActivity;


public class MainActivity extends AppCompatActivity {
    Intent intent;
    public int REQUEST_STORAGE_PERMISSION = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkReadPermission();
    }

    /**
     * 请求读写权限
     */
    private void checkReadPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //TODO 此处写第一次检查权限且已经拥有权限后的业务
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        }
    }


    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (permissions.length != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "打开读写权限失败，请允许权限后再试", Toast.LENGTH_SHORT).show();
            } else {
                //TODO 请求权限弹窗 允许后回调返回的成功回调 在此写业务逻辑
            }
        }

    }
    public void goApi(View view) {
        intent=new Intent(this, VideoApiActivity.class);
        startActivity(intent);
    }
    public void goSimple(View view) {
        intent=new Intent(this, SimpleDemoActivity.class);
        startActivity(intent);

    }

    public void goNormal(View view) {
        intent=new Intent(this, NormalDemoActivity.class);
        startActivity(intent);
    }
    public void SmoothStreaming(View view){
        intent=new Intent(this, SmoothStreamingActivity.class);
        startActivity(intent);
    }
    public void SimpleRecycler(View view){
        intent=new Intent(this, SimpleRecyclerActivity.class);
        startActivity(intent);
    }
}