package com.example.administrator.demo;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout btn1 ,btn2,btn3, btn4,btn5, btn6,btn7, btn8;
    //    LinearLayout btn1;
    private long exitTime = 0;
    //    Toast tst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }
    private void initEvent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
        }
    }
    private void initData() {
        //hehe
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        btn1 = (LinearLayout) findViewById(R.id.btn_mreasure);
        btn2 = (LinearLayout) findViewById(R.id.btn_remind);
        btn3 = (LinearLayout) findViewById(R.id.btn_curve);
        btn4 = (LinearLayout) findViewById(R.id.btn_show);
        btn5 = (LinearLayout) findViewById(R.id.btn_set);
        btn6 = (LinearLayout) findViewById(R.id.btn_ii);
        btn7 = (LinearLayout) findViewById(R.id.btn_powerkm);
        btn8 = (LinearLayout) findViewById(R.id.btn_guide);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mreasure:
//                Toast.makeText(getApplicationContext(), "Hello,I come from other thread!",Toast.LENGTH_SHORT ).show();
                //测量血压
                Intent intent1=new Intent(MainActivity.this,WebActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_remind:
                Toast.makeText(getApplicationContext(), "2",Toast.LENGTH_SHORT ).show();
                break;
            case R.id.btn_curve:
                Toast.makeText(getApplicationContext(), "3",Toast.LENGTH_SHORT ).show();
                break;
            case R.id.btn_show:
                Toast.makeText(getApplicationContext(), "4",Toast.LENGTH_SHORT ).show();
                break;
            case R.id.btn_set:
                Toast.makeText(getApplicationContext(), "5",Toast.LENGTH_SHORT ).show();
                break;
            case R.id.btn_ii:
                Toast.makeText(getApplicationContext(), "6",Toast.LENGTH_SHORT ).show();
                break;
            case R.id.btn_powerkm:
                Toast.makeText(getApplicationContext(), "7",Toast.LENGTH_SHORT ).show();
                break;
            case R.id.btn_guide:
                Toast.makeText(getApplicationContext(), "8",Toast.LENGTH_SHORT ).show();
                break;
            default:
                break;
        }
    }

    //返回键添加提醒
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
