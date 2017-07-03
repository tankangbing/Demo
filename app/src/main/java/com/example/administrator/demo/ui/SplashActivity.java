package com.example.administrator.demo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.PreferencesUtils;

/**
 * Created by U on 2017/1/13.
 * 欢迎界面
 */

public class SplashActivity extends Activity {

    private ImageView mIvSplash;
    private AlphaAnimation mA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }



    private void initView() {
        setContentView(R.layout.activity_splash);
        mIvSplash = (ImageView) findViewById(R.id.iv_splash);
    }

    private void initData() {
        // 初始动画
        initAnim();
    }

    // 初始动画
    private void initAnim() {

        // 透明度
        mA = new AlphaAnimation(0,1);
        mA.setDuration(2000);
        mIvSplash.startAnimation(mA);
    }

    private void initEvent() {
        // 动画的监听
        mA.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                    // 动画做完
                Intent intent=null;
                boolean yes = PreferencesUtils.getBoolean(getApplicationContext(), "yes");
                // 跳转到主页面
                if(yes){
                    intent=new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    intent=new Intent(SplashActivity.this,UserActivity.class);
                    startActivity(intent);
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
