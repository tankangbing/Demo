package com.example.administrator.demo.ui;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.view.MyImageView;
import com.example.administrator.demo.view.TouchImageView;

import org.xutils.x;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

public class TouchImageViewActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mIv_overlay_result;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        mIv_overlay_result = (ImageView) findViewById(R.id.iv_overlay_result);//扫描的图片
        TouchImageView myImageView = (TouchImageView)findViewById(R.id.iv_overlay_itemt);
        myImageView.setBitmab(BitmapFactory.decodeResource(getResources(), R.drawable.bg));
        x.Ext.init(getApplication());
        x.image().bind(mIv_overlay_result, "http://192.168.1.99/AugmentedRealitySystemFile/scenePhoto/1920/3f261939-5140-40ca-8d16-b9aa4d30b451_ar_haixinsha.jpg");
        myImageView.setImageDrawable(getResources().getDrawable(R.drawable.logo));
        myImageView.setDrawingCacheEnabled(true);// 启用缓存
    }

    @Override
    public void onClick(View v) {

    }

}
