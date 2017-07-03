package com.example.administrator.demo.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
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
import com.example.administrator.demo.view.TouchImageView;

import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class OverlayActivity extends AppCompatActivity implements View.OnClickListener{


    private Button mBtn_overlay_save;
    private Button mBtnOverlay_pre;
    private ImageView mIv_overlay_result;
    private TouchImageView mIv_overlay_item;
    private Bitmap Overlay_item;
    private Canvas canvas;

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
        mIv_overlay_result = (ImageView) findViewById(R.id.iv_overlay_result);//扫描的图片
        mIv_overlay_item = (TouchImageView) findViewById(R.id.iv_overlay_item);//拍照的图片

        mBtn_overlay_save.setOnClickListener(this);
        mBtnOverlay_pre.setOnClickListener(this);
    }

    private void initData() {
        mIv_overlay_result.setDrawingCacheEnabled(true);// 启用缓存
        mIv_overlay_item.setImageResource(R.drawable.logo);
//        mIv_overlay_item.setBitmab(BitmapFactory.decodeResource(getResources(), R.drawable.logo));
//        mIv_overlay_item.setBitmab(BitmapFactory.decodeResource(getResources(), R.drawable.bg));
        mIv_overlay_item.setDrawingCacheEnabled(true);
        Intent intent = getIntent();
        if (intent != null) {
            Overlay_item = intent.getParcelableExtra("bitmap");
            mIv_overlay_item.setBitmab(Overlay_item);
        }
        String url = PreferencesUtils.getString(getApplicationContext(), "url");
        x.Ext.init(getApplication());
//        x.image().bind(mIv_overlay_result, "http://192.168.1.99/AugmentedRealitySystemFile/scenePhoto/1920/3f261939-5140-40ca-8d16-b9aa4d30b451_ar_haixinsha.jpg");
        x.image().bind(mIv_overlay_result,url);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_overlay_pre:
                //返回预览照片
                Intent intent = new Intent(OverlayActivity.this,PreviewActivity.class);
                intent.putExtra("bitmap", Overlay_item);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_overlay_save:
                //保存叠加后的照片
                save();
                //弹出对话框
                dialog();
                break;
            default:
                break;
        }
    }

    private void dialog() {

//        new AlertDialog.Builder(OverlayActivity.this).setTitle("注意")//设置对话框标题
//                .setMessage("清除数据会清除您本地的数据及个人信息，您确定要清除数据吗！")//设置显示的内容
//                .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
//                        // TODO Auto-generated method stub
//                    }
//                })
//                .setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {//响应事件
//                        // TODO Auto-generated method stub
//                    }
//                }).show();//在按键响应事件中显示此对话框
    }

    private void save() {
        // 保存叠加的图片
        Bitmap bitmap = mIv_overlay_result.getDrawingCache();
        if (canvas == null) {
            canvas = new Canvas(bitmap);
        }
        // 根据相册的位置绘制
        canvas.drawBitmap(mIv_overlay_item.getDrawingCache(), mIv_overlay_item.getLeft(),mIv_overlay_item.getTop(), null);
        // 显示在界面上
        mIv_overlay_result.setImageBitmap(bitmap);
        //===============================================
        // 保存至本地
        SimpleDateFormat formatter = new SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        String   str   =   formatter.format(curDate);
        File file = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis()+".jpg");
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        //======================================================
        mIv_overlay_item.setVisibility(View.GONE);
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
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




