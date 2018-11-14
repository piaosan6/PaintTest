package com.pjm.painttest.xferModeTest.XferModeCase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.pjm.painttest.R;

/**
 *
 */

public class GuaGuaKaView3 extends View{

    private Paint paint;
    private Bitmap bm;
    private Path path;
    private float mLastX, mLastY;
    private Canvas mCanvas;
    private Bitmap mBackBitmap;


    public GuaGuaKaView3(Context context) {
        this(context, null);
    }

    public GuaGuaKaView3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuaGuaKaView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND); // 圆角
        paint.setStrokeCap(Paint.Cap.ROUND); // 圆角
        // 设置画笔宽度
        paint.setStrokeWidth(50);

       // bm = BitmapFactory.decodeResource(getResources(), R.mipmap.zm2);

        path = new Path();
        mBackBitmap =  BitmapFactory.decodeResource(getResources(), R.mipmap.zm2);

    }

    //    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(bm.getWidth(),bm.getHeight());
//        //mCanvas.drawColor(Color.parseColor("#c0c0c0"));
//    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = mBackBitmap.getWidth();
        int height = mBackBitmap.getHeight();
        // 初始化bitmap
        bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(bm);
        //绘制这改成
        mCanvas.drawColor(Color.parseColor("#c0c0c0"));
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBackBitmap, 0, 0, null);
        // 先绘制路径，在绘制蒙版，蒙版就是一张颜色为c0c0c0的图片
        drawPath();
        canvas.drawBitmap(bm, 0, 0, null);
    }

    private void drawPath() {
        // 只保留dest 部分，也就是先绘制的path部分
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCanvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                path.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:

                int dx = (int) Math.abs(x - mLastX);
                int dy = (int) Math.abs(y - mLastY);

                if (dx > 3 || dy > 3){
                    path.lineTo(x, y);
                }
                mLastX = x;
                mLastY = y;
                invalidate();
                break;
        }


        return true;
    }

}
