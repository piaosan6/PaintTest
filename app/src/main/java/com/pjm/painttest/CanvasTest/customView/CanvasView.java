package com.pjm.painttest.CanvasTest.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class CanvasView extends View{

    public static final int TRANSLATE = 0;
    public static final int SCALE = 1;
    public static final int ROTATE = 2;
    public static final int CLIP = 3;

    private Rect rect;
    private Rect resultRect;
    private Paint paint;
    private Paint paint1;
    private int type;

    public CanvasView(Context context) {
        this(context, null);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        rect = new Rect(0, 0, 400, 400);
        resultRect = new Rect();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GREEN);

        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setColor(Color.GREEN);
        paint1.setStrokeWidth(18);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (type){
            case TRANSLATE:
                testTranslate(canvas);
                break;
            case SCALE:
                testScale(canvas);
                break;
            case ROTATE:
                testRotate(canvas);
                break;
            case CLIP:
                testClip(canvas);
                break;
        }

    }

    public void testRotate(Canvas canvas) {
//        canvas.drawCircle(200, 200, 150, paint1);
//        canvas.rotate(45);
//        paint1.setColor(Color.BLACK);
//        drawXY(canvas);
//        paint1.setColor(Color.BLUE);
//        canvas.drawCircle(200, 200, 150, paint1);


//        canvas.drawCircle(200, 200, 160, paint1);
//        canvas.rotate(30 , 200, 200);
//        paint1.setColor(Color.BLACK);
//        drawXY(canvas);
//        paint1.setColor(Color.BLUE);
//        canvas.drawCircle(200, 200, 150, paint1);

        canvas.save();
        canvas.rotate(30 , 200, 200);
        paint1.setColor(Color.BLACK);
        drawXY(canvas);
        paint1.setColor(Color.BLUE);
        canvas.drawCircle(200, 200, 150, paint1);
        canvas.drawLine(350, 200, 500, 200, paint1);
        canvas.restore();
        canvas.drawCircle(300 , 300 ,250, paint1);

    }



    public void testScale(Canvas canvas) {
        // Scale 只是将画图的坐标系放大/缩放，大于1扩大，小于1缩小,

//        canvas.drawCircle(200, 200, 150, paint1);
//        canvas.drawCircle(400, 400, 310, paint1);
//        canvas.scale(2, 2);
//        drawXY(canvas);
//        paint1.setColor(Color.BLUE);
//        canvas.drawCircle(200, 200, 150, paint1);
//        canvas.scale(0.5f, 0.5f);
//        paint1.setColor(Color.RED);
//        canvas.drawCircle(200, 200, 160, paint1);
//        drawXY(canvas);

        // 设置负数只是将X,Y 坐标系反转
        paint1.setColor(Color.GREEN);
        drawXY(canvas);
        canvas.drawCircle(200, 200, 150, paint1);
        canvas.drawCircle(400, 400, 280, paint1);

        canvas.scale(-2, 2);
        canvas.drawLine(-300, 0, canvas.getWidth(), 0, paint1);
        canvas.drawLine(0, 0, 0, canvas.getHeight(), paint1);
        paint1.setColor(Color.BLUE);
        canvas.drawCircle(-200, 200, 150, paint1);

        canvas.scale(0.5f, 0.5f);
        paint1.setColor(Color.RED);
        canvas.drawCircle(-200, 200, 160, paint1);
        drawXY(canvas);


//        canvas.drawCircle(200, 200, 150, paint1);
//        canvas.drawCircle(400, 400, 270, paint1);
//        paint1.setColor(Color.BLACK);
//        canvas.drawLine(100,100, 1080,100, paint1);
//        canvas.drawLine(100,100, 100, 1920, paint1);
//        canvas.scale(0f, 1f , 100, 100);
//        paint1.setColor(Color.GREEN);
//        canvas.drawLine(0, 0, 800, 800, paint);
//        canvas.drawLine(0, 0, 0, 800, paint);

//        canvas.translate(50,50);
//        drawXY(canvas);
//        paint1.setColor(Color.BLUE);
//        canvas.drawCircle(200, 200, 150, paint1);
//        canvas.scale(0.5f, 0.5f);
//        paint1.setColor(Color.RED);
//        canvas.drawCircle(200, 200, 160, paint1);
//        drawXY(canvas);


        /**
         * 这里的canvas.scale(2f, 2f , 100, 100); 做了3个操作
         * translate(100, 100);
         * scale(2, 2);
         * translate(-100, -100);
         *
         */
//        canvas.drawCircle(200, 200, 150, paint1);
//        canvas.drawCircle(400, 400, 270, paint1);
//        paint1.setColor(Color.BLACK);
//        canvas.drawLine(100,100, 1080,100, paint1);
//        canvas.drawLine(100,100, 100, 1920, paint1);
//        canvas.scale(2f, 2f , 100, 100);
//        paint1.setColor(Color.GREEN);
//        canvas.translate(50,50);
//        drawXY(canvas);
//        paint1.setColor(Color.BLUE);
//        canvas.drawCircle(200, 200, 150, paint1);
//        canvas.scale(0.5f, 0.5f);
//        paint1.setColor(Color.RED);
//        canvas.drawCircle(200, 200, 160, paint1);
//        drawXY(canvas);

    }
    public void testTranslate(Canvas canvas) {
        // translate 只是将画图的坐标系平移,而canvas 本身不移动，所以不会影响之前画的图形
        paint1.setColor(Color.GREEN);
        drawXY(canvas);
        canvas.drawCircle(200, 200, 150, paint1);
        canvas.translate(500, 500);

        paint1.setColor(Color.BLUE);
        drawXY(canvas);
        canvas.drawCircle(200, 200, 150, paint1);
        canvas.translate(-200, -200);

        paint1.setColor(Color.RED);
        canvas.drawCircle(200, 200, 150, paint1);
        drawXY(canvas);


    }

    private void drawXY(Canvas canvas) {
        canvas.drawLine(0, 0, canvas.getWidth(), 0, paint1);
        canvas.drawLine(0, 0, 0, canvas.getHeight(), paint1);
    }

    public void testClip(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
        Gravity.apply(Gravity.LEFT, 200, 400, rect, resultRect);

        canvas.save();
        canvas.clipRect(resultRect);
        canvas.drawColor(Color.GRAY);
        canvas.restore();

        canvas.save();
        canvas.clipRect(resultRect, Region.Op.DIFFERENCE);
        canvas.drawColor(Color.RED);
        canvas.restore();

        canvas.save();
        canvas.clipRect(rect,Region.Op.DIFFERENCE);
        paint.setColor(Color.GREEN);
        canvas.drawCircle(450, 450, 200, paint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(1080,1920);
    }

    public void update(int type){
        this.type = type;
        invalidate();
    }

}
