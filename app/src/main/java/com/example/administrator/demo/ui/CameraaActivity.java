package com.example.administrator.demo.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.demo.R;
import com.example.administrator.demo.SystemBarTintManager;
import com.example.administrator.demo.been.CameraBeen;

import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;


public class CameraaActivity extends AppCompatActivity implements View.OnClickListener{


    private ImageView mIvresult;
    private TextView mBtnNext;
    private TextView mBtnPre;
    private String sdPath;//SD卡的路径
     private String picPath;//图片存储路径
    private static int REQUEST_ORIGINAL = 2;// 请求原图信号标识
    private static int REQUEST_THUMBNAIL = 1;// 请求缩略图信号标识
    private Bitmap mBitmap;

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
        mBtnNext = (TextView) findViewById(R.id.btn_next);
        mBtnPre = (TextView) findViewById(R.id.btn_pre);
        mBtnPre.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
    }

    private void initData() {
        //获取SD卡的路径
        sdPath = Environment.getExternalStorageDirectory().getPath();
        picPath = sdPath + "/" + "temp.png";
        //新页面接收数据
//        Bundle bundle = this.getIntent().getExtras();
//        //接收name值
//        String url = bundle.getString("URL");
        x.Ext.init(getApplication());
        //测试
        x.image().bind(mIvresult, "http://192.168.1.99/AugmentedRealitySystemFile/scenePhoto/1920/3f261939-5140-40ca-8d16-b9aa4d30b451_ar_haixinsha.jpg");
//        x.image().bind(mIvresult,url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                //调用手机拍照
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_THUMBNAIL);
                break;
            case R.id.btn_pre:
                //返回扫描界面
                Intent intent1 = new Intent(CameraaActivity.this,MainActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (resultCode == REQUEST_ORIGINAL) {//对应第二种方法
                /**
                 * 这种方法是通过内存卡的路径进行读取图片，所以的到的图片是拍摄的原图
                 */
                FileInputStream fis = null;
                try {
                    //把图片转化为字节流
                    fis = new FileInputStream(picPath);
                    //把流转化图片
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    mIvresult.setImageBitmap(bitmap);
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                }finally{
                    try {
                        fis.close();//关闭流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else
            if(requestCode == REQUEST_THUMBNAIL){
                Bundle bundle = data.getExtras();
                mBitmap = (Bitmap) bundle.get("data");
                Intent intent = new Intent(CameraaActivity.this,PreviewActivity.class);
                intent.putExtra("bitmap", mBitmap);
                startActivity(intent);
            }
        }
    }
}
