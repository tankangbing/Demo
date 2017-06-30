package com.example.administrator.demo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MyImageView extends android.support.v7.widget.AppCompatImageView{
    Matrix matrix = new Matrix();
    Matrix matrix1 = new Matrix();
    Matrix savedMatrix = new Matrix();
    /**位图对象*/
    private Bitmap bitmap = null;
    /** 屏幕的分辨率*/
    private DisplayMetrics dm;

    /** 最小缩放比例*/
    float minScaleR = 1.0f;

    /** 最大缩放比例*/
    static final float MAX_SCALE = 15f;

    /** 初始状态*/
    static final int NONE = 0;
    /** 拖动*/
    static final int DRAG = 1;
    /** 缩放*/
    static final int ZOOM = 2;

    float oldRotation = 0;
    /** 当前模式*/
    int mode = NONE;

    /** 存储float类型的x，y值，就是你点下的坐标的X和Y*/
    PointF prev = new PointF();
    PointF mid = new PointF();
    float dist = 1f;
    int widthScreen;
    int heightScreen;
    float x_down = 0;
    float y_down = 0;
    boolean matrixCheck = false;

    public MyImageView(Context context) {
        super(context);
        setupView();
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView();
    }


    public void setupView(){
        Context context = getContext();
        //获取屏幕分辨率,需要根据分辨率来使用图片居中
        dm = context.getResources().getDisplayMetrics();
        widthScreen = dm.widthPixels;
        heightScreen = dm.heightPixels;
        //根据MyImageView来获取bitmap对象
        BitmapDrawable bd = (BitmapDrawable)this.getDrawable();
        if(bd != null){
            bitmap = bd.getBitmap();
        }

        //设置ScaleType为ScaleType.MATRIX，这一步很重要
        this.setScaleType(ScaleType.MATRIX);
        this.setImageBitmap(bitmap);

        //bitmap为空就不调用center函数
//        if(bitmap != null){
//            center(true, true);
//        }
        this.setImageMatrix(matrix);
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    // 主点按下
                    case MotionEvent.ACTION_DOWN:
                        savedMatrix.set(matrix);
//                        prev.set(event.getX(), event.getY());
                        x_down = event.getX();
                        y_down = event.getY();
                        mode = DRAG;
                        break;
                    // 副点按下
                    case MotionEvent.ACTION_POINTER_DOWN:
                        dist = spacing(event);
                        oldRotation = rotation(event);
                        // 如果连续两点距离大于10，则判定为多点模式
                        if (spacing(event) > 10f) {
                            savedMatrix.set(matrix);
                            midPoint(mid, event);
                            mode = ZOOM;
                        }
                        break;
                    case MotionEvent.ACTION_UP:{
                        break;
                    }
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;
                        //savedMatrix.set(matrix);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {
                            matrix1.set(savedMatrix);
                            matrix1.postTranslate(event.getX() - x_down, event.getY()
                                    - y_down);// 平移
//                            matrixCheck = matrixCheck();
//                            matrixCheck = matrixCheck();
//                            if (matrixCheck == false) {
//                                matrix.set(matrix1);
//                                invalidate();
//                            }

//                            matrix.set(savedMatrix);
//                            matrix.postTranslate(event.getX() - prev.x, event.getY()
//                                    - prev.y);
                        } else if (mode == ZOOM) {
                            matrix1.set(savedMatrix);
                            float rotation = rotation(event) - oldRotation;
                            float newDist = spacing(event);
                            float scale = newDist / dist;
                            matrix1.postScale(scale, scale, mid.x, mid.y);// 縮放
                            matrix1.postRotate(rotation, mid.x, mid.y);// 旋轉
//                            matrixCheck = matrixCheck();
//                            if (matrixCheck == false) {
//                                matrix.set(matrix1);
//                                invalidate();
//                            }

//                            float newDist = spacing(event);
//                            if (newDist > 10f) {
//                                matrix.set(savedMatrix);
//                                float tScale = newDist / dist;
//                                matrix.postScale(tScale, tScale, mid.x, mid.y);
//                            }
                        }
                        break;
                }
                MyImageView.this.setImageMatrix(matrix);
//                CheckView();
                return true;
            }
        });
    }


    /**
     * 横向、纵向居中
     */
    protected void center(boolean horizontal, boolean vertical) {
        Matrix m = new Matrix();
        m.set(matrix);
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        m.mapRect(rect);

        float height = rect.height();
        float width = rect.width();

        float deltaX = 0, deltaY = 0;

        if (vertical) {
            // 图片小于屏幕大小，则居中显示。大于屏幕，上方留空则往上移，下方留空则往下移
            int screenHeight = dm.heightPixels;
            if (height < screenHeight) {
                deltaY = (screenHeight - height) / 2 - rect.top;
            } else if (rect.top > 0) {
                deltaY = -rect.top;
            } else if (rect.bottom < screenHeight) {
                deltaY = this.getHeight() - rect.bottom;
            }
        }

        if (horizontal) {
            int screenWidth = dm.widthPixels;
            if (width < screenWidth) {
                deltaX = (screenWidth - width) / 2 - rect.left;
            } else if (rect.left > 0) {
                deltaX = -rect.left;
            } else if (rect.right < screenWidth) {
                deltaX = screenWidth - rect.right;
            }
        }
        matrix.postTranslate(deltaX, deltaY);
    }


    /**
     * 限制最大最小缩放比例，自动居中
     */
    private void CheckView() {
        float p[] = new float[9];
        matrix.getValues(p);
        if (mode == ZOOM) {
            if (p[0] < minScaleR) {
                //Log.d("", "当前缩放级别:"+p[0]+",最小缩放级别:"+minScaleR);
                matrix.setScale(minScaleR, minScaleR);
            }
            if (p[0] > MAX_SCALE) {
                //Log.d("", "当前缩放级别:"+p[0]+",最大缩放级别:"+MAX_SCALE);
                matrix.set(savedMatrix);
            }
        }
        center(true, true);
    }


    /**
     * 两点的距离
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return  (float) Math.sqrt(x * x + y * y);
    }

    /**
     * 两点的中点
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
    // 取旋转角度
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }
    private boolean matrixCheck() {
        float[] f = new float[9];
        matrix.getValues(f);
        // 图片4个顶点的坐标
        float x1 = f[0] * 0 + f[1] * 0 + f[2];
        float y1 = f[3] * 0 + f[4] * 0 + f[5];
        float x2 = f[0] * bitmap.getWidth() + f[1] * 0 + f[2];
        float y2 = f[3] * bitmap.getWidth() + f[4] * 0 + f[5];
        float x3 = f[0] * 0 + f[1] * bitmap.getHeight() + f[2];
        float y3 = f[3] * 0 + f[4] * bitmap.getHeight() + f[5];
        float x4 = f[0] * bitmap.getWidth() + f[1] * bitmap.getHeight() + f[2];
        float y4 = f[3] * bitmap.getWidth() + f[4] * bitmap.getHeight() + f[5];
        // 图片现宽度
        double width = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        // 缩放比率判断
        if (width < widthScreen / 3 || width > widthScreen * 3) {
            return true;
        }
        // 出界判断
        if ((x1 < widthScreen / 3 && x2 < widthScreen / 3
                && x3 < widthScreen / 3 && x4 < widthScreen / 3)
                || (x1 > widthScreen * 2 / 3 && x2 > widthScreen * 2 / 3
                && x3 > widthScreen * 2 / 3 && x4 > widthScreen * 2 / 3)
                || (y1 < heightScreen / 3 && y2 < heightScreen / 3
                && y3 < heightScreen / 3 && y4 < heightScreen / 3)
                || (y1 > heightScreen * 2 / 3 && y2 > heightScreen * 2 / 3
                && y3 > heightScreen * 2 / 3 && y4 > heightScreen * 2 / 3)) {
            return true;
        }
        return false;
    }
}