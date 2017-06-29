package com.example.administrator.demo.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.SystemBarTintManager;
import com.example.administrator.demo.been.CameraBeen;
import com.example.administrator.demo.utils.PreferencesUtils;

import org.xutils.x;


public class OverlayActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mBtn_overlay_save;
    private Button mBtnOverlay_pre;
    private ImageView mIv_overlay_result,mIv_overlay_item;
    private Bitmap mBitmap;
    private Canvas canvas;
    private int lastX, lastY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initToobar();
        initData();
    }

    private void initToobar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_overlay);
        mBtn_overlay_save = (Button) findViewById(R.id.btn_overlay_save);
        mBtnOverlay_pre = (Button) findViewById(R.id.btn_overlay_pre);
        mIv_overlay_result = (ImageView) findViewById(R.id.iv_overlay_result);
        mIv_overlay_item = (ImageView) findViewById(R.id.iv_overlay_item);

        mBtn_overlay_save.setOnClickListener(this);
        mBtnOverlay_pre.setOnClickListener(this);
    }

    private void initData() {
        mIv_overlay_result.setDrawingCacheEnabled(true);// 启用缓存
        mIv_overlay_item.setDrawingCacheEnabled(true);
//        mIv_overlay_item.setOnTouchListener((View.OnTouchListener) this);//点击
        Intent intent=getIntent();
        if(intent!=null)
        {
            mBitmap=intent.getParcelableExtra("bitmap");
            mIv_overlay_item.setImageBitmap(mBitmap);
        }
        String url = PreferencesUtils.getString(getApplicationContext(), "url");
        x.Ext.init(getApplication());
//        x.image().bind(mIv_overlay_result, "http://192.168.1.99/AugmentedRealitySystemFile/scenePhoto/1920/3f261939-5140-40ca-8d16-b9aa4d30b451_ar_haixinsha.jpg");
        CameraBeen cameraBeen = new CameraBeen();
        x.image().bind(mIv_overlay_result,url);
//        mIv_overlay_item.setImageResource(R.drawable.logo);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_overlay_pre:
                break;
            case R.id.btn_overlay_save:
                break;
            default:
                break;
        }
    }

//    @Override
//    public boolean onTouch(View v ;MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                lastX = (int) event.getRawX();
//                lastY = (int) event.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int dx = (int) event.getRawX() - lastX;
//                int dy = (int) event.getRawY() - lastY;
//                int left = v.getLeft() + dx;
//                int top = v.getTop() + dy;
//                int right = v.getRight() + dx;
//                int bottom = v.getBottom() + dy;
//                v.layout(left, top, right, bottom);
//                lastX = (int) event.getRawX();
//                lastY = (int) event.getRawY();
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//        return true;
//    }
}
