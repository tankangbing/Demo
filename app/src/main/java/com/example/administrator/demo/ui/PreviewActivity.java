package com.example.administrator.demo.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.SystemBarTintManager;

import org.xutils.x;


public class PreviewActivity extends AppCompatActivity implements View.OnClickListener {

    private String sdPath;//SD卡的路径
    private String picPath;//图片存储路径
    private static int REQUEST_ORIGINAL = 2;// 请求原图信号标识
    private static int REQUEST_THUMBNAIL = 1;// 请求缩略图信号标识
    private Bitmap mBitmap;
    private TextView mBtn_p_next;
    private TextView mBtn_p_again;
    private TextView mBtn_p_pre;
    private ImageView mIvpreview;

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
        setContentView(R.layout.activity_preview);
        mBtn_p_next = (TextView) findViewById(R.id.btn_preview_next);
        mBtn_p_again = (TextView) findViewById(R.id.btn_preview_again);
        mBtn_p_pre = (TextView) findViewById(R.id.btn_preview_pre);
        mIvpreview = (ImageView) findViewById(R.id.iv_result_preview);

        mBtn_p_next.setOnClickListener(this);
        mBtn_p_again.setOnClickListener(this);
        mBtn_p_pre.setOnClickListener(this);
    }

    private void initData() {
        //接收数据手机拍照的bitmap;
        Intent intent = getIntent();
        if (intent != null) {
            mBitmap = intent.getParcelableExtra("bitmap");
            mIvpreview.setImageBitmap(mBitmap);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_preview_pre:
                //返回背景预览界面
                Intent intent1 = new Intent(PreviewActivity.this, CameraaActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.btn_preview_again:
                //重拍
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent2, REQUEST_ORIGINAL);
                break;
            case R.id.btn_preview_next:
                //照片的合成
                Intent intent = new Intent(PreviewActivity.this,OverlayActivity.class);
                intent.putExtra("bitmap", mBitmap);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ORIGINAL) {
            Bundle bundle = data.getExtras();
            mBitmap = (Bitmap) bundle.get("data");
            mIvpreview.setImageBitmap(mBitmap);
        }
    }
}
