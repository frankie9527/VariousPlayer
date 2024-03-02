package org.various.demo.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Created by Frankie on 2020/8/27
 * Email：847145851@qq.com
 * func:
 */
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        initView();
        initImmersionBar();
        initData();
    }



    protected abstract int setLayout();

    protected void initView() {

    }

    protected void initData() {
    }
    /**
     * 沉侵式
     */
    protected void initImmersionBar() {
        //在BaseActivity裡初始化
//        ImmersionBar.with(this)
//                .statusBarDarkFont(false, 1f)
//                .keyboardEnable(false)
//                .init();
    }

}
