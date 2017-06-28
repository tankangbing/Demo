package com.example.administrator.demo.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.SystemBarTintManager;
import com.example.administrator.demo.been.CameraBeen;

import org.xutils.x;


public class CameraaActivity extends AppCompatActivity implements View.OnClickListener{


    private ImageView mIvresult;
    private Button mBtnNext;
    private Button mBtnPre;

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
        setContentView(R.layout.activity_cameraa);
        mIvresult = (ImageView) findViewById(R.id.iv_result);
        mBtnNext = (Button) findViewById(R.id.btn_next);
        mBtnPre = (Button) findViewById(R.id.btn_pre);

        mBtnPre.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
    }

    private void initData() {
        CameraBeen cameraBeen = new CameraBeen();
        x.Ext.init(getApplication());
        x.image().bind(mIvresult, "http://192.168.1.99/AugmentedRealitySystemFile/scenePhoto/1920/3f261939-5140-40ca-8d16-b9aa4d30b451_ar_haixinsha.jpg");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }




}
