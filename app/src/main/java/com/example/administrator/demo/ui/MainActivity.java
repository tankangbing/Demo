package com.example.administrator.demo.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.acker.simplezxing.activity.CaptureActivity;
import com.example.administrator.demo.R;
import com.example.administrator.demo.SystemBarTintManager;
import com.example.administrator.demo.been.CameraBeen;
import com.example.administrator.demo.utils.PreferencesUtils;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout btn1 ,btn2,btn3, btn4,btn5, btn6,btn7, btn8;
    ImageView reSet;
    private static final int REQ_CODE_PERMISSION = 0x1111;
    private long exitTime = 0;
    private CameraBeen mCameraBeen;
    private TextView mSet;

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
        reSet = (ImageView) findViewById(R.id.iv_reset);

        reSet.setOnClickListener(this);
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
//             扫描二维码
                scan();
                break;
            case R.id.btn_curve:
                text();
                break;
            case R.id.btn_show:
                text1();
                break;
            case R.id.btn_set:
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
            case R.id.iv_reset:
                Intent intent=new Intent(MainActivity.this,UserActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }


    //================================扫描的测试==============================================================

    private void scan() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Do not have the permission of camera, request it.
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
        } else {
            // Have gotten the permission
            startCaptureActivityForResult();
        }
    }
    private void startCaptureActivityForResult() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(CaptureActivity.KEY_NEED_BEEP, CaptureActivity.VALUE_BEEP);
        bundle.putBoolean(CaptureActivity.KEY_NEED_VIBRATION, CaptureActivity.VALUE_VIBRATION);
        bundle.putBoolean(CaptureActivity.KEY_NEED_EXPOSURE, CaptureActivity.VALUE_NO_EXPOSURE);
        bundle.putByte(CaptureActivity.KEY_FLASHLIGHT_MODE, CaptureActivity.VALUE_FLASHLIGHT_OFF);
        bundle.putByte(CaptureActivity.KEY_ORIENTATION_MODE, CaptureActivity.VALUE_ORIENTATION_AUTO);
        bundle.putBoolean(CaptureActivity.KEY_SCAN_AREA_FULL_SCREEN, CaptureActivity.VALUE_SCAN_AREA_FULL_SCREEN);
        bundle.putBoolean(CaptureActivity.KEY_NEED_SCAN_HINT_TEXT, CaptureActivity.VALUE_SCAN_HINT_TEXT);
        intent.putExtra(CaptureActivity.EXTRA_SETTING_BUNDLE, bundle);
        startActivityForResult(intent, CaptureActivity.REQ_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // User agree the permission
                    startCaptureActivityForResult();
                } else {
                    // User disagree the permission
                    Toast.makeText(this, "You must agree the camera permission request before you use the code scan function", Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CaptureActivity.REQ_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        String result = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                        //扫描结果
                        //解析
                         gsonforResult(result);
                        break;
                    case RESULT_CANCELED:
                        if (data != null) {
                            String resultt = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                            //扫描结果
                            //解析
                            gsonforResult(resultt);
                        }
                        break;
                }
                break;
        }
    }

    //================================扫描的测试==============================================================


    private void text1() {
        Intent intent1=new Intent(MainActivity.this,TouchImageViewActivity.class);
        startActivity(intent1);
    }

    private void text() {
        Intent intent1=new Intent(MainActivity.this,CameraaActivity.class);
        startActivity(intent1);
    }

//    private void sao() {
//        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
//        integrator.initiateScan();
//    }
    //扫描完返回的字符串
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        if (scanResult != null) {
//            //扫描结果
//            String result = scanResult.getContents();
//            //解析
//            gsonforResult(result);
//            //回调接口
//            /**
//             *url:api+statistics/scaned_client
//             bgId:场景背景ID
//             bgTitle:场景背景标题
//             userName:用户名
//             userPhone:用户手机
//             fromMac:大屏MAC
//             fromLibrary:大屏所在图书馆
//             */
//            String url=null;
//            String bgId=null;
//            String bgTitle=null;
//            String userName=null;
//            String userPhone=null;
//            String fromMac=null;
//            String fromLibrary=null;
////            getBack(url,bgId,bgTitle,userName,userPhone,fromMac,fromLibrary);
//
//        }
//
//    }

    private void getBack(String url,String bgId, String bgTitle, String userName, String userPhone, String fromMac, String fromLibrary) {

        OkHttpUtils
                .get()
                .url(url)
                .addParams("bgId", bgId)
                .addParams("bgTitle", bgTitle)
                .addParams("userName", userName)
                .addParams("userPhone", userPhone)
                .addParams("fromMac", fromMac)
                .addParams("fromLibrary", fromLibrary)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {

                    }
                });
    }


    private void gsonforResult(String result) {
        Gson gson = new Gson();
        mCameraBeen = gson.fromJson(result, CameraBeen.class);
        PreferencesUtils.putString(getApplicationContext(),"url",mCameraBeen.getUrl());
//        String api = cameraBeen.getApi();
//        String url = cameraBeen.getUrl();
//        String library = cameraBeen.getLibrary();
        Intent intent2=new Intent(MainActivity.this,CameraaActivity.class);
        Bundle bundle=new Bundle();
        //传递name参数为tinyphp
        bundle.putString("URL",mCameraBeen.getUrl());
        intent2.putExtras(bundle);
        startActivity(intent2);
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
