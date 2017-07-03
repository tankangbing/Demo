package com.example.administrator.demo.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.demo.R;
import com.example.administrator.demo.SystemBarTintManager;
import com.example.administrator.demo.utils.PreferencesUtils;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

public class UserActivity extends AppCompatActivity {

    private EditText mEdt_use_address;
    private EditText mEdt_use_phone;
    private EditText mEdt_use_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        setContentView(R.layout.activity_user);
        mEdt_use_address = (EditText) findViewById(R.id.edt_use_address);
        mEdt_use_phone = (EditText) findViewById(R.id.edt_use_phone);
        mEdt_use_name = (EditText) findViewById(R.id.edt_use_name);
    }

    private void initData() {

    }

    private void initEvent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
        }
    }

    public void save(View view) {
        PreferencesUtils.putBoolean(getApplicationContext(), "yes", true);
        Intent intent = new Intent(UserActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
