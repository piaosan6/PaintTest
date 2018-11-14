package com.pjm.painttest.canvasTest.customView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hml on 2017/5/18.
 */

public class SearchView2 extends View {
    private Paint paint;
    private float centerX;
    private float centerY;
    private float radiusCircle;//半径
    private float radiusLine;//搜索的小尾巴长度
    private float bottomLline;//地下线的长度
    private float strokeWidth = 5;
    //private String bg = "#4CAF50";
    private float maxTranslateX;
    private float translateX = 0;
    private double percent1 = 1;
    private double percent2 = 1;
    private double percent3 = 0;
    private boolean isStart;

    public SearchView2(Context context) {
        this(context, null);

    }

    public SearchView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = getMeasuredWidth()/2;
        centerY = getMeasuredHeight()/2;
        radiusCircle = 45;
        radiusLine = 60;
        bottomLline = getMeasuredWidth() - 200;
        maxTranslateX = centerX - radiusCircle - 130;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);

        int layerID = canvas.saveLayer(0,0,getWidth(),getHeight(),paint,Canvas.ALL_SAVE_FLAG);
        canvas.translate(translateX,0);
        canvas.save();
        //画圆
        RectF rectF = new RectF(centerX-radiusCircle,centerY-radiusCircle,centerX+radiusCircle,centerY+radiusCircle);
        canvas.drawArc(rectF,(float)(45+360*(1-percent1)), (float) (360*percent1),false,paint);
        //画小尾巴
        float rotateX1 = centerX;
        float rotateY1 = centerY;
        PointF pointFStart1 = new PointF((float) (centerX+radiusCircle + radiusLine*(1-percent2)),centerY);
        PointF pointFEnd1 = new PointF(centerX+radiusCircle+radiusLine,centerY);
        canvas.rotate(45,rotateX1,rotateY1);
        canvas.save();
        canvas.drawLine(pointFStart1.x,pointFStart1.y,pointFEnd1.x,pointFEnd1.y,paint);
        //画横线
        float rotateX2 = pointFEnd1.x;
        float rotateY2 = pointFEnd1.y;
        PointF pointFStart2 = pointFEnd1;
        PointF pointFEnd2 = new PointF((float) (pointFStart2.x+bottomLline*percent3),centerY);
        canvas.rotate(135,rotateX2,rotateY2);
        canvas.save();
        canvas.drawLine(pointFStart2.x,pointFStart2.y,pointFEnd2.x,pointFEnd2.y,paint);
        canvas.restoreToCount(layerID);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN)
        {
            startAnimator();
        }
        return true;
    }

    public void startAnimator()
    {
        if (!isStart)
        {
            isStart = true;
            ValueAnimator animator = ValueAnimator.ofFloat(translateX,maxTranslateX);
            animator.setDuration(1500);
            animator.start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float dx = (float) animation.getAnimatedValue();
                    translateX = dx;
                    double percent = translateX * 1.00/ maxTranslateX;
                    if (percent < 1.00/3)
                    {
                        percent1 = 1 - (percent * 3.00);
                        percent2 = 1;
                        percent3 = 0;
                    }else if (percent<2.00/3)
                    {
                        percent1 = 0;
                        percent2 = 1 - (percent - 1.00 / 3) * 3;
                        percent3 = 0;
                    }else {
                        percent1 = 0;
                        percent2 = 0;
                        percent3 = (percent - 2.00 / 3) * 3;
                    }
                    postInvalidate();
                }
            });
        }else {
            isStart = false;
            percent1 = 1;
            percent2 = 1;
            percent3 = 0;
            postInvalidate();
        }
    }

}
